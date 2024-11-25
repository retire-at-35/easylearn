<template>
  <view class="index-container">
    <text>主页</text>
  </view>
</template>

<script setup>
import { onMounted } from 'vue'

onMounted(() => {
  const pages = getCurrentPages()
  console.log('当前页面栈:', pages)
  console.log('当前页面路径:', pages[pages.length - 1].route)
})
</script>

<style lang="scss" scoped>
.index-container {
  padding: 20rpx;
}
</style> 