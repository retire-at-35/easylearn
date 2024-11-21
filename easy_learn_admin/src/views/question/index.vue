<template>
  <div class="question-container">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <div class="filter-container">
            <!-- 筛选区域 -->
            <el-select v-model="queryParams.typeId" placeholder="题目类型" clearable>
              <el-option
                v-for="item in typeList"
                :key="item.tid"
                :label="item.tmtypeName"
                :value="item.tid"
              />
            </el-select>

            <el-select
              v-model="queryParams.chapterId"
              placeholder="选择章"
              clearable
              @change="handleChapterChange"
            >
              <el-option
                v-for="item in chapterList"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              />
            </el-select>

            <el-select
              v-model="queryParams.sectionId"
              placeholder="选择节"
              clearable
              :disabled="!queryParams.chapterId"
            >
              <el-option
                v-for="item in sectionList"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              />
            </el-select>

            <el-input
              v-model="queryParams.keyword"
              placeholder="请输入关键词"
              style="width: 200px"
              clearable
            >
              <template #append>
                <el-button :icon="Search" @click="handleSearch"/>
              </template>
            </el-input>
          </div>

          <el-button type="primary" @click="handleAdd">新增题目</el-button>
        </div>
      </template>

      <el-table
        :data="questionList"
        style="width: 100%"
        v-loading="loading"
      >
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="title" label="题目" show-overflow-tooltip />
        <el-table-column prop="typeName" label="类型" width="120">
          <template #default="scope">
            <el-tag>{{ scope.row.typeName }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="chapterName" label="章" width="120" />
        <el-table-column prop="sectionName" label="节" width="120" />
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="scope">
            <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button
              size="small"
              type="danger"
              @click="handleDelete(scope.row)"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 添加分页组件 -->
      <div class="pagination-container">
        <el-pagination
          v-model:current-page="queryParams.pageNum"
          v-model:page-size="queryParams.pageSize"
          :page-sizes="[10, 20, 50, 100]"
          :total="total"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>

    <!-- 新增题目的对话框后续添加 -->
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { Search } from '@element-plus/icons-vue'
import { getQuestionList, getChapterList, getSectionList } from '@/api/question'
import { getAllTypes } from '@/api/type'

// 查询参数
const queryParams = ref({
  typeId: '',
  chapterId: '',
  sectionId: '',
  keyword: '',
  pageNum: 1,
  pageSize: 10
})

// 数据列表
const questionList = ref([])
const typeList = ref([])
const chapterList = ref([])
const sectionList = ref([])
const loading = ref(false)

// 添加总数记录
const total = ref(0)

// 获取所有类型
const getTypes = async () => {
  try {
    const res = await getAllTypes()
    typeList.value = res
  } catch (error) {
    console.error('获取类型列表失败：', error)
  }
}

// 获取章列表
const getChapters = async () => {
  try {
    const res = await getChapterList()
    chapterList.value = res
  } catch (error) {
    console.error('获取章列表失败：', error)
  }
}

// 获取节列表
const getSections = async (chapterId) => {
  if (!chapterId) {
    sectionList.value = []
    return
  }
  try {
    const res = await getSectionList(chapterId)
    sectionList.value = res
  } catch (error) {
    console.error('获取节列表失败：', error)
  }
}

// 章选择改变时触发
const handleChapterChange = (value) => {
  queryParams.value.sectionId = ''
  if (value) {
    getSections(value)
  }
}

// 获取题目列表
const getQuestions = async () => {
  loading.value = true
  try {
    const res = await getQuestionList(queryParams.value)
    // 假设后端返回的数据格式为 { records: [], total: number }
    questionList.value = res.records
    total.value = res.total
  } catch (error) {
    console.error('获取题目列表失败：', error)
  } finally {
    loading.value = false
  }
}

// 处理每页显示数量变化
const handleSizeChange = (val) => {
  queryParams.value.pageSize = val
  queryParams.value.pageNum = 1 // 重置到第一页
  getQuestions()
}

// 处理页码变化
const handleCurrentChange = (val) => {
  queryParams.value.pageNum = val
  getQuestions()
}

// 搜索
const handleSearch = () => {
  queryParams.value.pageNum = 1
  getQuestions()
}

// 新增题目
const handleAdd = () => {
  // 待实现
}

// 编辑题目
const handleEdit = (row) => {
  // 待实现
}

// 删除题目
const handleDelete = (row) => {
  // 待实现
}

onMounted(() => {
  getTypes()
  getChapters()
  getQuestions()
})
</script>

<style scoped>
.question-container {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.filter-container {
  display: flex;
  gap: 15px;
  align-items: center;
}

.pagination-container {
  display: flex;
  justify-content: flex-end;
  margin-top: 20px;
}
</style>
