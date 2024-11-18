import instance from '@/utils/http'

// 获取用户列表
export const getUserList = (params) => {
  return instance({
    url: '/super/getUser',
    method: 'get',
    params
  })
}

// 新增用户
export const addUser = (data) => {
  return instance({
    url: '/super/addUser',
    method: 'post',
    data
  })
}

// 修改用户
export const updateUser = (data) => {
  return instance({
    url: '/super/updateUser',
    method: 'put',
    data
  })
}

// 删除用户
export const deleteUser = (id) => {
  return instance({
    url: '/super/deleteById',
    method: 'delete',
    params: { id }
  })
}
