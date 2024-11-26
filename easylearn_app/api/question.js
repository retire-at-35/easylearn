import http from '@/utils/request'

// 获取题目
export const getQuestions = (type) => {
  return http.get('/app/getAllQuestion', { type })
} 