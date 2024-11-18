import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUserStore = defineStore('user', () => {
  // 从 localStorage 初始化状态
  const userInfo = ref(JSON.parse(localStorage.getItem('userInfo')) || null)

  // 设置用户信息
  const setUserInfo = (info) => {
    userInfo.value = info
    // 保存到 localStorage
    localStorage.setItem('userInfo', JSON.stringify(info))
  }

  // 清除用户信息
  const clearUserInfo = () => {
    userInfo.value = null
    // 清除 localStorage
    localStorage.removeItem('userInfo')
  }

  return {
    userInfo,
    setUserInfo,
    clearUserInfo
  }
})
