<template>
  <div class="chapter-container">
    <!-- 顶部操作区 -->
    <div class="operation-area">
      <el-input
        v-model="searchQuery"
        placeholder="请输入章节名称搜索"
        class="search-input"
        clearable
        @clear="handleSearch"
        @keyup.enter="handleSearch"
      >
        <template #prefix>
          <el-icon><search /></el-icon>
        </template>
      </el-input>

      <el-button type="primary" @click="handleAdd">
        <el-icon><plus /></el-icon>
        新增章节
      </el-button>
    </div>

    <!-- 章节列表 -->
    <el-table
      :data="chapterList"
      style="width: 100%"
      v-loading="loading"
      border
    >
      <el-table-column prop="id" label="章节ID" width="100" align="center" />
      <el-table-column prop="chapterName" label="章节名称" align="center" />
      <el-table-column prop="sort" label="排序" width="100" align="center" />
      <el-table-column label="操作" width="200" align="center">
        <template #default="{ row }">
          <el-button
            type="primary"
            link
            @click="handleEdit(row)"
          >
            修改
          </el-button>
          <el-button
            type="danger"
            link
            @click="handleDelete(row)"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div class="pagination-container">
      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :page-sizes="[10, 20, 30, 50]"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      >
        <template #total>
          共 <span style="color: #409EFF;">{{ total }}</span> 条
        </template>
        <template #sizes>
          <span style="margin-right: 4px;">每页</span>
          <el-select v-model="pageSize" style="width: 100px">
            <el-option
              v-for="size in [10, 20, 30, 50]"
              :key="size"
              :value="size"
              :label="`${size}条/页`"
            />
          </el-select>
        </template>
      </el-pagination>
    </div>

    <!-- 新增/编辑对话框 -->
    <el-dialog
      :title="dialogTitle"
      v-model="dialogVisible"
      width="500px"
    >
      <el-form
        ref="chapterFormRef"
        :model="chapterForm"
        :rules="chapterRules"
        label-width="100px"
      >
        <el-form-item label="章节名称" prop="chapterName">
          <el-input v-model="chapterForm.chapterName" placeholder="请输入章节名称" />
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input-number v-model="chapterForm.sort" :min="1" :max="999" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { Search, Plus } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getChapterList, addChapter, updateChapter, deleteChapter } from '@/api/chapter'

// 搜索相关
const searchQuery = ref('')
const handleSearch = () => {
  currentPage.value = 1
  fetchChapterList()
}

// 分页相关
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const handleSizeChange = (val) => {
  pageSize.value = val
  currentPage.value = 1
  fetchChapterList()
}
const handleCurrentChange = (val) => {
  currentPage.value = val
  fetchChapterList()
}

// 表格相关
const loading = ref(false)
const chapterList = ref([])

// 获取章节列表
const fetchChapterList = async () => {
  loading.value = true
  try {
    const params = {
      page: currentPage.value,
      pageSize: pageSize.value,
      ...(searchQuery.value ? { name: searchQuery.value } : {})
    }
    const res = await getChapterList(params)
    // 由于后端返回的字段名与前端不同，需要转换一下
    chapterList.value = res.rows.map(item => ({
      id: item.cid,
      chapterName: item.chapterName,
      sort: item.pos
    }))
    total.value = res.total
  } catch (error) {
    console.error('获取章节列表失败:', error)
    ElMessage.error('获取章节列表失败')
  } finally {
    loading.value = false
  }
}

// 表单相关
const dialogVisible = ref(false)
const dialogTitle = ref('')
const chapterFormRef = ref()
const chapterForm = reactive({
  id: '',
  chapterName: '',
  sort: 1
})
const chapterRules = {
  chapterName: [
    { required: true, message: '请输入章节名称', trigger: 'blur' },
    { min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur' }
  ],
  sort: [
    { required: true, message: '请输入排序号', trigger: 'blur' }
  ]
}

// 新增章节
const handleAdd = () => {
  dialogTitle.value = '新增章节'
  chapterForm.id = ''
  chapterForm.chapterName = ''
  chapterForm.sort = chapterList.value.length + 1
  dialogVisible.value = true
}

// 编辑章节
const handleEdit = (row) => {
  dialogTitle.value = '编辑章节'
  chapterForm.id = row.id
  chapterForm.chapterName = row.chapterName
  chapterForm.sort = row.sort
  dialogVisible.value = true
}

// 删除章节
const handleDelete = (row) => {
  ElMessageBox.confirm(
    '确认删除该章节吗？',
    '警告',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }
  ).then(async () => {
    try {
      await deleteChapter(row.id)
      ElMessage.success('删除成功')
      if (chapterList.value.length === 1 && currentPage.value > 1) {
        currentPage.value--
      }
      fetchChapterList()
    } catch (error) {
      console.error('删除失败:', error)
      ElMessage.error('删除失败')
    }
  })
}

// 提交表单
const handleSubmit = () => {
  chapterFormRef.value?.validate(async (valid) => {
    if (valid) {
      try {
        if (chapterForm.id) {
          // 修改章节
          await updateChapter({
            id: chapterForm.id,
            chapterName: chapterForm.chapterName,
            sort: chapterForm.sort
          })
          ElMessage.success('修改成功')
        } else {
          // 新增章节
          await addChapter({
            chapterName: chapterForm.chapterName,
            sort: chapterForm.sort
          })
          ElMessage.success('添加成功')
        }
        dialogVisible.value = false
        fetchChapterList()
      } catch (error) {
        console.error('操作失败:', error)
        ElMessage.error('操作失败')
      }
    }
  })
}

// 初始化
fetchChapterList()
</script>

<style scoped>
.chapter-container {
  padding: 20px;
  background-color: #fff;
  border-radius: 4px;
}

.operation-area {
  margin-bottom: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.search-input {
  width: 300px;
}

.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}

:deep(.el-dialog__body) {
  padding-top: 20px;
}

:deep(.el-pagination) {
  justify-content: flex-end;
  font-size: 14px;
}

:deep(.el-select .el-input) {
  margin: 0 8px;
}

:deep(.el-pagination .el-select .el-input .el-input__inner) {
  padding-right: 25px;
}
</style>
