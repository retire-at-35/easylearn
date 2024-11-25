<template>
  <view class="my-container">
    <!-- 用户信息区域 -->
    <view class="user-info">
      <view class="avatar-wrapper" @tap="handleChangeAvatar">
        <image 
          class="avatar" 
          :src="userInfo.avatar || '/static/default-avatar.png'"
          mode="aspectFill"
        ></image>
      </view>
      <text class="username">{{ userInfo.username || '未登录' }}</text>
    </view>
    
    <!-- 功能列表区域 -->
    <view class="function-list">
      <!-- 账号管理 -->
      <view class="function-group">
        <view class="group-title">账号管理</view>
        <view class="group-content">
          <!-- 修改密码 -->
          <view class="function-item" @tap="handleChangePassword">
            <text class="item-text">修改密码</text>
            <text class="item-arrow">></text>
          </view>
          <!-- 退出登录 -->
          <view class="function-item" @tap="handleLogout">
            <text class="item-text">退出登录</text>
            <text class="item-arrow">></text>
          </view>
        </view>
      </view>
    </view>
  </view>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { uploadAvatar } from '@/api/common.js'

// 用户信息
const userInfo = ref({
  avatar: '',
  username: ''
})

// 获取用户信息
const getUserInfo = () => {
  const storedUserInfo = uni.getStorageSync('userInfo')
  if (storedUserInfo) {
    userInfo.value = storedUserInfo
  }
}

// 修改密码
const handleChangePassword = () => {
  uni.navigateTo({
    url: '/pages/change-password/index'
  })
}

// 退出登录
const handleLogout = () => {
  uni.showModal({
    title: '提示',
    content: '确定要退出登录吗？',
    success: (res) => {
      if (res.confirm) {
        // 清除存储的用户信息和token
        uni.removeStorageSync('userInfo')
        uni.removeStorageSync('token')
        
        // 跳转到登录页
        uni.reLaunch({
          url: '/pages/login/index',
          success: () => {
            uni.showToast({
              title: '已退出登录',
              icon: 'success'
            })
          }
        })
      }
    }
  })
}

// 修改头像
const handleChangeAvatar = () => {
  uni.chooseImage({
    count: 1,
    sizeType: ['compressed'],
    sourceType: ['album', 'camera'],
    success: async (res) => {
      try {
        // 显示加载提示
        uni.showLoading({
          title: '上传中...'
        })
        
        // 上传图片，传入临时文件路径
        const url = await uploadAvatar(res.tempFilePaths[0])
        
        // 更新本地存储的用户信息
        const storedUserInfo = uni.getStorageSync('userInfo')
        storedUserInfo.avatar = url
        uni.setStorageSync('userInfo', storedUserInfo)
        
        // 更新页面显示
        userInfo.value.avatar = url
        
        // 显示成功提示
        uni.showToast({
          title: '头像修改成功',
          icon: 'success'
        })
      } catch (error) {
        uni.showToast({
          title: error.message || '头像上传失败',
          icon: 'none'
        })
      } finally {
        uni.hideLoading()
      }
    }
  })
}

onMounted(() => {
  getUserInfo() // 页面加载时获取用户信息
})
</script>

<style lang="scss" scoped>
.my-container {
  min-height: 100vh;
  background-color: #f5f5f5;
  
  .user-info {
    background-color: #fff;
    padding: 40rpx;
    display: flex;
    flex-direction: column;
    align-items: center;
    
    .avatar-wrapper {
      position: relative;
      margin-bottom: 20rpx;
      
      .avatar {
        width: 150rpx;
        height: 150rpx;
        border-radius: 75rpx;
      }
      
      .change-text {
        position: absolute;
        bottom: -30rpx;
        left: 50%;
        transform: translateX(-50%);
        font-size: 24rpx;
        color: #666;
      }
    }
    
    .username {
      font-size: 32rpx;
      color: #333;
    }
  }
  
  .function-list {
    margin-top: 20rpx;
    
    .function-group {
      background-color: #fff;
      
      .group-title {
        padding: 20rpx 30rpx;
        font-size: 28rpx;
        color: #666;
        border-bottom: 1rpx solid #eee;
      }
      
      .group-content {
        .function-item {
          display: flex;
          justify-content: space-between;
          align-items: center;
          padding: 30rpx;
          border-bottom: 1rpx solid #eee;
          
          &:last-child {
            border-bottom: none;
          }
          
          .item-text {
            font-size: 30rpx;
            color: #333;
          }
          
          .item-arrow {
            color: #999;
            font-size: 30rpx;
          }
        }
      }
    }
  }
}
</style> 