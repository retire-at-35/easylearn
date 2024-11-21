import instance from '@/utils/http'

// 获取所有章节
export const getAllChapter = () => {
  return instance({
    url: 'common/section/getAllChapter',
    method: 'get'
  })
}

// 获取节列表（带分页和搜索）
export const getSectionList = (params) => {
  return instance({
    url: 'common/section/getPage',
    method: 'get',
    params
  })
}

// 新增节
export const addSection = (data) => {
  return instance({
    url: 'common/section/addSection',
    method: 'post',
    data: {
      cid: data.chapterId,
      sectionName: data.sectionName,
      pos: data.sort
    }
  })
}

// 修改节
export const updateSection = (data) => {
  return instance({
    url: '/super/updateSection',
    method: 'put',
    params: {  // 使用 params 因为后端没有使用 @RequestBody
      sid: data.id,
      cid: data.chapterId,
      sectionName: data.sectionName,
      pos: data.sort
    }
  })
}

// 删除节
export const deleteSection = (id) => {
  return instance({
    url: '/super/deleteSection',
    method: 'delete',
    params: { id }
  })
}
