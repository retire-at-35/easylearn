import http from '@/utils/request'

// 用户注册
export const register = (data) => {
  return http.post('/pub/register', data)
}

// 用户登录
export const login = (data) => {
  return http.post('/pub/applogin', data)
} 