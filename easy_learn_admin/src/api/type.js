import instance from '@/utils/http'

export function getAllTypes() {
  return instance({
    url: '/common/type/getAllType',
    method: 'get'
  })
}

export function updateOrSaveType(data) {
  return instance({
    url: '/common/type/updateOrSave',
    method: 'post',
    data
  })
}

export function deleteType(id) {
  return instance({
    url: `/super/type/deleteById`,
    method: 'delete',
    params: { id }
  })
}
