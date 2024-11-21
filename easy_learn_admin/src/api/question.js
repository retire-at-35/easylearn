import instance from '@/utils/http'

// 模拟题目列表数据
const mockQuestions = Array.from({ length: 100 }, (_, index) => ({
  id: index + 1,
  title: `这是第${index + 1}道测试题目`,
  typeName: ['单选题', '多选题', '判断题'][index % 3],
  chapterName: `第${Math.floor(index / 10) + 1}章`,
  sectionName: `第${(index % 10) + 1}节`
}))

// 模拟分页获取题目列表
export function getQuestionList(params) {
  const { pageNum, pageSize } = params
  const start = (pageNum - 1) * pageSize
  const end = start + pageSize

  // 模拟筛选
  let filteredData = [...mockQuestions]
  if (params.typeId) {
    filteredData = filteredData.filter(item =>
      item.typeName === ['单选题', '多选题', '判断题'][params.typeId - 1]
    )
  }
  if (params.keyword) {
    filteredData = filteredData.filter(item =>
      item.title.includes(params.keyword)
    )
  }

  return Promise.resolve({
    records: filteredData.slice(start, end),
    total: filteredData.length
  })
}

// 模拟章列表数据
const mockChapters = Array.from({ length: 10 }, (_, index) => ({
  id: index + 1,
  name: `第${index + 1}章`
}))

// 模拟获取章列表
export function getChapterList() {
  return Promise.resolve(mockChapters)
}

// 模拟节列表数据
export function getSectionList(chapterId) {
  const sections = Array.from({ length: 10 }, (_, index) => ({
    id: chapterId * 100 + index + 1,
    name: `第${index + 1}节`
  }))
  return Promise.resolve(sections)
}
