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
      name: data.sectionName,
      pos: data.sort
    }
  })
}
