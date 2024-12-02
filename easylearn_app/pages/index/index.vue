<template>
  <view class="index-container">
    <!-- 题型选择区域 -->
    <view class="question-types">
      <!-- 模拟考试 -->
      <view class="type-item" @tap="goToMockExam">
        <view class="type-icon">
          <text class="iconfont icon-exam"></text>
        </view>
        <view class="type-info">
          <text class="type-name">模拟考试</text>
          <text class="type-desc">点击进入考试</text>
        </view>
      </view>
      
      <!-- 单选题 -->
      <view class="type-item" @tap="goToExam(1)">
        <view class="type-icon">
          <text class="iconfont icon-danxuan"></text>
        </view>
        <view class="type-info">
          <text class="type-name">单选题</text>
          <text class="type-desc">点击进入答题</text>
        </view>
      </view>
      
      <!-- 多选题 -->
      <view class="type-item" @tap="goToExam(2)">
        <view class="type-icon">
          <text class="iconfont icon-duoxuan"></text>
        </view>
        <view class="type-info">
          <text class="type-name">多选题</text>
          <text class="type-desc">点击进入答题</text>
        </view>
      </view>
      
      <!-- 判断题 -->
      <view class="type-item" @tap="goToExam(3)">
        <view class="type-icon">
          <text class="iconfont icon-panduan"></text>
        </view>
        <view class="type-info">
          <text class="type-name">判断题</text>
          <text class="type-desc">点击进入答题</text>
        </view>
      </view>
    </view>
  </view>
</template>

<script setup>
import { onMounted } from 'vue'

// 跳转到答题页面
const goToExam = (type) => {
  uni.navigateTo({
    url: `/pages/exam/index?type=${type}`
  })
}

// 跳转到模拟考试页面
const goToMockExam = () => {
  uni.navigateTo({
    url: '/pages/mock-exam/index'
  })
}

onMounted(() => {
  console.log('首页加载完成')
})
</script>

<style lang="scss" scoped>
.index-container {
  min-height: 100vh;
  background-color: #f5f5f5;
  padding: 20rpx;
  
  .question-types {
    .type-item {
      background-color: #fff;
      border-radius: 16rpx;
      padding: 30rpx;
      margin-bottom: 20rpx;
      display: flex;
      align-items: center;
      box-shadow: 0 2rpx 12rpx rgba(0, 0, 0, 0.1);
      
      .type-icon {
        width: 100rpx;
        height: 100rpx;
        background-color: #f0f7ff;
        border-radius: 50%;
        display: flex;
        justify-content: center;
        align-items: center;
        margin-right: 30rpx;
        
        .iconfont {
          font-size: 50rpx;
          color: #2979ff;
        }
      }
      
      .type-info {
        flex: 1;
        
        .type-name {
          font-size: 32rpx;
          color: #333;
          font-weight: bold;
          margin-bottom: 10rpx;
          display: block;
        }
        
        .type-desc {
          font-size: 26rpx;
          color: #999;
        }
      }
    }
  }
}
</style> 