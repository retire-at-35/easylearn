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
      name: data.chapterName,
      pos: data.sort
    }
  })
}
