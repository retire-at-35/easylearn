import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUserStore = defineStore('user', () => {
  const userInfo = ref(null)

  // 设置用户信息
  const setUserInfo = (info) => {
    userInfo.value = info
  }

  // 清除用户信息
  const clearUserInfo = () => {
    userInfo.value = null
  }

  return {
    userInfo,
    setUserInfo,
    clearUserInfo
  }
})
