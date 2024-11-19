<template>
  <div class="section-container">
    <!-- 顶部操作区 -->
    <div class="operation-area">
      <div class="left-area">
        <el-select
          v-model="selectedChapter"
          placeholder="请选择章"
          class="chapter-select"
          @change="handleChapterChange"
        >
          <el-option
            v-for="item in chapterList"
            :key="item.id"
            :label="item.chapterName"
            :value="item.id"
          />
        </el-select>

        <el-input
          v-model="searchQuery"
          placeholder="请输入节名称搜索"
          class="search-input"
          clearable
          @clear="handleSearch"
          @keyup.enter="handleSearch"
        >
          <template #prefix>
            <el-icon><search /></el-icon>
          </template>
        </el-input>
      </div>

      <el-button type="primary" @click="handleAdd">
        <el-icon><plus /></el-icon>
        新增节
      </el-button>
    </div>

    <!-- 节列表 -->
    <el-table
      :data="sectionList"
      style="width: 100%"
      v-loading="loading"
      border
    >
      <el-table-column prop="id" label="节ID" width="100" align="center" />
      <el-table-column prop="sectionName" label="节名称" align="center" />
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
        ref="sectionFormRef"
        :model="sectionForm"
        :rules="sectionRules"
        label-width="100px"
      >
        <el-form-item label="所属章" prop="chapterId">
          <el-select
            v-model="sectionForm.chapterId"
            placeholder="请选择所属章"
            class="form-select"
          >
            <el-option
              v-for="item in chapterList"
              :key="item.id"
              :label="item.chapterName"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="节名称" prop="sectionName">
          <el-input v-model="sectionForm.sectionName" placeholder="请输入节名称" />
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input-number v-model="sectionForm.sort" :min="1" :max="999" />
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
import { ref, reactive, onMounted } from 'vue'
import { Search, Plus } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getAllChapter, getSectionList, addSection } from '@/api/section'

// 章节选择相关
const chapterList = ref([])
const selectedChapter = ref('')

// 获取章列表
const fetchChapterList = async () => {
  try {
    const res = await getAllChapter()
    chapterList.value = res.map(item => ({
      id: item.cid,
      chapterName: item.name,
      sort: item.pos
    }))
    if (chapterList.value.length > 0 && !selectedChapter.value) {
      selectedChapter.value = chapterList.value[0].id
      fetchSectionList()
    }
  } catch (error) {
    console.error('获取章列表失败:', error)
    ElMessage.error('获取章列表失败')
  }
}

// 搜索相关
const searchQuery = ref('')
const handleSearch = () => {
  currentPage.value = 1
  fetchSectionList()
}

// 分页相关
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const handleSizeChange = (val) => {
  pageSize.value = val
  currentPage.value = 1
  fetchSectionList()
}
const handleCurrentChange = (val) => {
  currentPage.value = val
  fetchSectionList()
}

// 表格相关
const loading = ref(false)
const sectionList = ref([])

// 获取节列表
const fetchSectionList = async () => {
  if (!selectedChapter.value) return
  loading.value = true
  try {
    const params = {
      page: currentPage.value,
      pageSize: pageSize.value,
      cid: selectedChapter.value,
      ...(searchQuery.value ? { name: searchQuery.value } : {})
    }
    const res = await getSectionList(params)
    sectionList.value = res.rows.map(item => ({
      id: item.sid,
      sectionName: item.name,
      sort: item.pos
    }))
    total.value = res.total
  } catch (error) {
    console.error('获取节列表失败:', error)
    ElMessage.error('获取节列表失败')
  } finally {
    loading.value = false
  }
}

// 处理章节切换
const handleChapterChange = () => {
  currentPage.value = 1
  fetchSectionList()
}

// 表单相关
const dialogVisible = ref(false)
const dialogTitle = ref('')
const sectionFormRef = ref()
const sectionForm = reactive({
  id: '',
  chapterId: '',
  sectionName: '',
  sort: 1
})
const sectionRules = {
  chapterId: [
    { required: true, message: '请选择所属章', trigger: 'change' }
  ],
  sectionName: [
    { required: true, message: '请输入节名称', trigger: 'blur' },
    { min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur' }
  ],
  sort: [
    { required: true, message: '请输入排序号', trigger: 'blur' }
  ]
}

// 新增节
const handleAdd = () => {
  dialogTitle.value = '新增节'
  sectionForm.id = ''
  sectionForm.chapterId = selectedChapter.value
  sectionForm.sectionName = ''
  sectionForm.sort = sectionList.value.length + 1
  dialogVisible.value = true
}

// 编辑节
const handleEdit = (row) => {
  dialogTitle.value = '编辑节'
  sectionForm.id = row.id
  sectionForm.chapterId = selectedChapter.value
  sectionForm.sectionName = row.sectionName
  sectionForm.sort = row.sort
  dialogVisible.value = true
}

// 删除节
const handleDelete = (row) => {
  ElMessageBox.confirm(
    '确认删除该节吗？',
    '警告',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }
  ).then(async () => {
    try {
      // TODO: 调用删除接口
      ElMessage.success('删除成功')
      if (sectionList.value.length === 1 && currentPage.value > 1) {
        currentPage.value--
      }
      fetchSectionList()
    } catch (error) {
      console.error('删除失败:', error)
      ElMessage.error('删除失败')
    }
  })
}

// 提交表单
const handleSubmit = () => {
  sectionFormRef.value?.validate(async (valid) => {
    if (valid) {
      try {
        if (sectionForm.id) {
          // TODO: 实现编辑功能
        } else {
          // 新增节
          await addSection({
            chapterId: sectionForm.chapterId,
            sectionName: sectionForm.sectionName,
            sort: sectionForm.sort
          })
          ElMessage.success('添加成功')
        }
        dialogVisible.value = false
        fetchSectionList()
      } catch (error) {
        console.error('操作失败:', error)
        ElMessage.error('操作失败')
      }
    }
  })
}

// 初始化
onMounted(() => {
  fetchChapterList()
})
</script>

<style scoped>
.section-container {
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

.left-area {
  display: flex;
  gap: 16px;
  align-items: center;
}

.chapter-select {
  width: 200px;
}

.search-input {
  width: 300px;
}

.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}

.form-select {
  width: 100%;
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
