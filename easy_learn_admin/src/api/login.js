import instance from '@/utils/http'
export const loginAPI = ({ username,password,checkCode }) => {
  return instance({
    url: '/pub/login',
    method: 'POST',
    data: {
     username,
     password,
     checkCode
    }
  })
}
