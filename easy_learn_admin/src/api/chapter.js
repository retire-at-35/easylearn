import instance from '@/utils/http'

// 获取章节列表
export const getChapterList = (params) => {
  return instance({
    url: '/common/chapter/getByPage',
    method: 'get',
    params
  })
}

// 新增章节
export const addChapter = (data) => {
  return instance({
    url: '/common/chapter/addChapter',
    method: 'post',
    data: {
      chapterName: data.chapterName,
      pos: data.sort
    }
  })
}

// 修改章节
export const updateChapter = (data) => {
  return instance({
    url: '/super/updateChapter',
    method: 'put',
    params: {  // 使用 params 因为后端没有使用 @RequestBody
      cid: data.id,
      chapterName: data.chapterName,
      pos: data.sort
    }
  })
}

// 删除章节
export const deleteChapter = (id) => {
  return instance({
    url: '/super/deleteChapter',
    method: 'delete',
    params: { id }
  })
}
