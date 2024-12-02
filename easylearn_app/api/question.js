import http from '@/utils/request'

// 获取题目
export const getQuestions = (type) => {
  return http.get('/app/getAllQuestion', { type })
}

// 获取模拟考试题目
export const getExamQuestions = () => {
  return http.get('/app/generateExam')
} 