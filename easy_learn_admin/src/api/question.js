import instance from '@/utils/http'

// 获取单选题列表
export const getSingleQuestionList = (params) => {
  return instance({
    url: '/common/single/getPage',
    method: 'get',
    params
  })
}

// 新增或修改单选题
export const addOrUpdateSingleQuestion = (data) => {
  return instance({
    url: '/common/single/addOrUpdateQuestion',
    method: 'post',
    data: {
      qid: data.id || undefined,
      cid: data.chapterId,
      sid: data.sectionId,
      tid: 1,
      content: data.title,
      key: data.answer,
      optionsList: [
        {
          oid: data.optionsList?.[0]?.oid,
          qid: data.id,
          op: data.optionA,
          pos: 1
        },
        {
          oid: data.optionsList?.[1]?.oid,
          qid: data.id,
          op: data.optionB,
          pos: 2
        },
        {
          oid: data.optionsList?.[2]?.oid,
          qid: data.id,
          op: data.optionC,
          pos: 3
        },
        {
          oid: data.optionsList?.[3]?.oid,
          qid: data.id,
          op: data.optionD,
          pos: 4
        }
      ]
    }
  })
}

// 删除单选题
export const deleteSingleQuestion = (id) => {
  return instance({
    url: '/common/single/deleteSingleQuestion',
    method: 'delete',
    params: { id }
  })
}

// 获取多选题列表
export const getMultipleQuestionList = (params) => {
  return instance({
    url: '/common/multiple/getPage',
    method: 'get',
    params
  })
}

// 获取判断题列表
export const getJudgeQuestionList = (params) => {
  return instance({
    url: '/common/judge/getPage',
    method: 'get',
    params
  })
}

// 新增或修改多选题
export const addOrUpdateMultipleQuestion = (data) => {
  return instance({
    url: '/common/multiple/addOrUpdateQuestion',
    method: 'post',
    data: {
      qid: data.id || undefined,
      cid: data.chapterId,
      sid: data.sectionId,
      tid: 2,  // 多选题类型为2
      content: data.title,
      key: data.answer.sort().join(''),  // 将选项数组转为字符串，如 ['A','B','C'] -> 'ABC'
      optionsList: [
        {
          oid: data.optionsList?.[0]?.oid,
          qid: data.id,
          op: data.optionA,
          pos: 1
        },
        {
          oid: data.optionsList?.[1]?.oid,
          qid: data.id,
          op: data.optionB,
          pos: 2
        },
        {
          oid: data.optionsList?.[2]?.oid,
          qid: data.id,
          op: data.optionC,
          pos: 3
        },
        {
          oid: data.optionsList?.[3]?.oid,
          qid: data.id,
          op: data.optionD,
          pos: 4
        }
      ]
    }
  })
}

// 删除多选题
export const deleteMultipleQuestion = (id) => {
  return instance({
    url: '/common/multiple/deleteMultipleQuestion',
    method: 'delete',
    params: { id }
  })
}

// 新增或修改判断题
export const addOrUpdateJudgeQuestion = (data) => {
  return instance({
    url: '/common/judge/addOrUpdateQuestion',
    method: 'post',
    data: {
      qid: data.id || undefined,
      cid: data.chapterId,
      sid: data.sectionId,
      tid: 3,  // 判断题类型为3
      content: data.title,
      key: data.answer === '1' ? 'A' : 'B',  // 1转为A(正确)，0转为B(错误)
      optionsList: [
        {
          oid: data.optionsList?.[0]?.oid,
          qid: data.id,
          op: '正确',
          pos: 1
        },
        {
          oid: data.optionsList?.[1]?.oid,
          qid: data.id,
          op: '错误',
          pos: 2
        }
      ]
    }
  })
}

// 删除判断题
export const deleteJudgeQuestion = (id) => {
  return instance({
    url: '/common/judge/deleteJudgeQuestion',
    method: 'delete',
    params: { id }
  })
}
