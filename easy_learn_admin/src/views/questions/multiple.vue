<template>
  <div class="question-container">
    <!-- 顶部操作区 -->
    <div class="operation-area">
      <div class="left-area">
        <!-- 章选择 -->
        <el-select
          v-model="selectedChapter"
          placeholder="请选择章"
          class="chapter-select"
          @change="handleChapterChange"
          clearable
        >
          <el-option
            v-for="item in chapterList"
            :key="item.id"
            :label="item.chapterName"
            :value="item.id"
          />
        </el-select>

        <!-- 节选择 -->
        <el-select
          v-model="selectedSection"
          placeholder="请选择节"
          class="section-select"
          :disabled="!selectedChapter"
          clearable
        >
          <el-option
            v-for="item in sectionList"
            :key="item.id"
            :label="item.sectionName"
            :value="item.id"
          />
        </el-select>

        <!-- 题目搜索 -->
        <el-input
          v-model="searchQuery"
          placeholder="请输入题目名称搜索"
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
        新增题目
      </el-button>
    </div>

    <!-- 题目列表 -->
    <el-table
      :data="questionList"
      style="width: 100%"
      v-loading="loading"
      border
    >
      <el-table-column prop="id" label="题目ID" width="80" align="center" />
      <el-table-column prop="title" label="题目名称" align="center" />
      <el-table-column prop="chapterName" label="所属章" align="center" />
      <el-table-column prop="sectionName" label="所属节" align="center" />
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
      width="700px"
    >
      <el-form
        ref="questionFormRef"
        :model="questionForm"
        :rules="questionRules"
        label-width="100px"
      >
        <el-form-item label="所属章" prop="chapterId">
          <el-select
            v-model="questionForm.chapterId"
            placeholder="请选择所属章"
            class="form-select"
            @change="handleFormChapterChange"
          >
            <el-option
              v-for="item in chapterList"
              :key="item.id"
              :label="item.chapterName"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="所属节" prop="sectionId">
          <el-select
            v-model="questionForm.sectionId"
            placeholder="请选择所属节"
            class="form-select"
            :disabled="!questionForm.chapterId"
          >
            <el-option
              v-for="item in formSectionList"
              :key="item.id"
              :label="item.sectionName"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="题目名称" prop="title">
          <el-input
            v-model="questionForm.title"
            type="textarea"
            :rows="2"
            placeholder="请输入题目名称"
          />
        </el-form-item>
        <el-form-item label="选项A" prop="optionA">
          <el-input v-model="questionForm.optionA" placeholder="请输入选项A" />
        </el-form-item>
        <el-form-item label="选项B" prop="optionB">
          <el-input v-model="questionForm.optionB" placeholder="请输入选项B" />
        </el-form-item>
        <el-form-item label="选项C" prop="optionC">
          <el-input v-model="questionForm.optionC" placeholder="请输入选项C" />
        </el-form-item>
        <el-form-item label="选项D" prop="optionD">
          <el-input v-model="questionForm.optionD" placeholder="请输入选项D" />
        </el-form-item>
        <el-form-item label="正确答案" prop="answer">
          <el-checkbox-group v-model="questionForm.answer">
            <el-checkbox label="A">A</el-checkbox>
            <el-checkbox label="B">B</el-checkbox>
            <el-checkbox label="C">C</el-checkbox>
            <el-checkbox label="D">D</el-checkbox>
          </el-checkbox-group>
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
import { ref, reactive, watch, onMounted } from 'vue'
import { Search, Plus } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getAllChapter, getSectionList } from '@/api/section'
import { getMultipleQuestionList, addOrUpdateMultipleQuestion, deleteMultipleQuestion } from '@/api/question'

// 章节选择相关
const chapterList = ref([])
const sectionList = ref([])
const selectedChapter = ref('')
const selectedSection = ref('')
const formSectionList = ref([])

// 获取章列表
const fetchChapterList = async () => {
  try {
    const res = await getAllChapter()
    chapterList.value = res.map(item => ({
      id: item.cid,
      chapterName: item.chapterName,
      sort: item.pos
    }))
  } catch (error) {
    console.error('获取章列表失败:', error)
    ElMessage.error('获取章列表失败')
  }
}

// 获取节列表
const fetchSectionList = async (chapterId) => {
  try {
    const res = await getSectionList({ cid: chapterId })
    const sections = res.rows.map(item => ({
      id: item.sid,
      sectionName: item.sectionName,
      sort: item.pos
    }))
    if (chapterId === selectedChapter.value) {
      sectionList.value = sections
    }
    if (chapterId === questionForm.chapterId) {
      formSectionList.value = sections
    }
  } catch (error) {
    console.error('获取节列表失败:', error)
    ElMessage.error('获取节列表失败')
  }
}

// 处理章选择变化
const handleChapterChange = (val) => {
  selectedSection.value = ''
  if (val) {
    fetchSectionList(val)
  } else {
    sectionList.value = []
  }
  currentPage.value = 1
  fetchQuestionList()
}

// 处理表单中章选择变化
const handleFormChapterChange = (val) => {
  questionForm.sectionId = ''
  if (val) {
    fetchSectionList(val)
  } else {
    formSectionList.value = []
  }
}

// 搜索相关
const searchQuery = ref('')
const handleSearch = () => {
  currentPage.value = 1
  fetchQuestionList()
}

// 分页相关
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const handleSizeChange = (val) => {
  pageSize.value = val
  currentPage.value = 1
  fetchQuestionList()
}
const handleCurrentChange = (val) => {
  currentPage.value = val
  fetchQuestionList()
}

// 表格相关
const loading = ref(false)
const questionList = ref([])

// 添加对节选择变化的监听
watch(selectedSection, () => {
  currentPage.value = 1
  fetchQuestionList()
})

// 获取题目列表
const fetchQuestionList = async () => {
  loading.value = true
  try {
    const params = {
      page: currentPage.value,
      pageSize: pageSize.value,
      cid: selectedChapter.value || undefined,
      sid: selectedSection.value || undefined,
      content: searchQuery.value || undefined
    }
    const res = await getMultipleQuestionList(params)
    questionList.value = res.rows.map(item => ({
      id: item.qid,
      title: item.content,
      chapterId: item.cid,
      sectionId: item.sid,
      answer: item.key,
      chapterName: item.chapterName,
      sectionName: item.sectionName,
      optionsList: item.optionsList
    }))
    total.value = res.total
  } catch (error) {
    console.error('获取题目列表失败:', error)
    ElMessage.error('获取题目列表失败')
  } finally {
    loading.value = false
  }
}

// 表单相关
const dialogVisible = ref(false)
const dialogTitle = ref('')
const questionFormRef = ref()
const questionForm = reactive({
  id: '',
  chapterId: '',
  sectionId: '',
  title: '',
  optionA: '',
  optionB: '',
  optionC: '',
  optionD: '',
  answer: []  // 修改为数组类型
})

const questionRules = {
  chapterId: [
    { required: true, message: '请选择所属章', trigger: 'change' }
  ],
  sectionId: [
    { required: true, message: '请选择所属节', trigger: 'change' }
  ],
  title: [
    { required: true, message: '请输入题目名称', trigger: 'blur' }
  ],
  optionA: [
    { required: true, message: '请输入选项A', trigger: 'blur' }
  ],
  optionB: [
    { required: true, message: '请输入选项B', trigger: 'blur' }
  ],
  optionC: [
    { required: true, message: '请输入选项C', trigger: 'blur' }
  ],
  optionD: [
    { required: true, message: '请输入选项D', trigger: 'blur' }
  ],
  answer: [
    {
      required: true,
      type: 'array',
      message: '请至少选择一个正确答案',
      trigger: 'change'
    }
  ]
}

// 新增题目
const handleAdd = () => {
  dialogTitle.value = '新增题目'
  Object.keys(questionForm).forEach(key => {
    questionForm[key] = key === 'answer' ? [] : ''
  })
  dialogVisible.value = true
}

// 编辑题目
const handleEdit = (row) => {
  dialogTitle.value = '编辑题目'
  Object.assign(questionForm, {
    id: row.id,
    chapterId: row.chapterId,
    sectionId: row.sectionId,
    title: row.title,
    optionA: row.optionsList[0]?.op || '',
    optionB: row.optionsList[1]?.op || '',
    optionC: row.optionsList[2]?.op || '',
    optionD: row.optionsList[3]?.op || '',
    answer: row.answer.split(''),  // 字符串转为数组
    optionsList: row.optionsList
  })
  // 获取编辑项的节列表
  if (questionForm.chapterId) {
    fetchSectionList(questionForm.chapterId)
  }
  dialogVisible.value = true
}

// 删除题目
const handleDelete = (row) => {
  ElMessageBox.confirm(
    '确认删除该题目吗？',
    '警告',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }
  ).then(async () => {
    try {
      await deleteMultipleQuestion(row.id)
      ElMessage.success('删除成功')
      if (questionList.value.length === 1 && currentPage.value > 1) {
        currentPage.value--
      }
      fetchQuestionList()
    } catch (error) {
      console.error('删除失败:', error)
      ElMessage.error('删除失败')
    }
  })
}

// 提交表单
const handleSubmit = () => {
  questionFormRef.value?.validate(async (valid) => {
    if (valid) {
      try {
        await addOrUpdateMultipleQuestion({
          id: questionForm.id,
          chapterId: questionForm.chapterId,
          sectionId: questionForm.sectionId,
          title: questionForm.title,
          optionA: questionForm.optionA,
          optionB: questionForm.optionB,
          optionC: questionForm.optionC,
          optionD: questionForm.optionD,
          answer: questionForm.answer,
          optionsList: questionForm.optionsList
        })
        ElMessage.success(questionForm.id ? '修改成功' : '添加成功')
        dialogVisible.value = false
        fetchQuestionList()
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
  fetchQuestionList()
})
</script>

<style scoped>
.question-container {
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

.chapter-select,
.section-select {
  width: 180px;
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
