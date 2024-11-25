<template>
  <view class="change-password-container">
    <view class="form-box">
      <!-- 原密码输入框 -->
      <view class="input-item">
        <text class="label">原密码</text>
        <input 
          type="password"
          v-model="formData.oldPassword"
          placeholder="请输入原密码"
          class="input"
        />
      </view>
      
      <!-- 新密码输入框 -->
      <view class="input-item">
        <text class="label">新密码</text>
        <input 
          type="password"
          v-model="formData.password"
          placeholder="请输入新密码"
          class="input"
        />
      </view>
      
      <!-- 确认新密码输入框 -->
      <view class="input-item">
        <text class="label">确认新密码</text>
        <input 
          type="password"
          v-model="formData.confirmPassword"
          placeholder="请再次输入新密码"
          class="input"
        />
      </view>
      
      <!-- 提交按钮 -->
      <button 
        type="primary" 
        @tap="handleSubmit"
        class="submit-btn"
      >确认修改</button>
    </view>
  </view>
</template>

<script setup>
import { reactive } from 'vue'
import { changePassword } from '@/api/user.js'

// 表单数据
const formData = reactive({
  oldPassword: '',
  password: '',
  confirmPassword: ''
})

// 提交修改
const handleSubmit = async () => {
  // 表单验证
  if (!formData.oldPassword) {
    uni.showToast({
      title: '请输入原密码',
      icon: 'none'
    })
    return
  }
  if (!formData.password) {
    uni.showToast({
      title: '请输入新密码',
      icon: 'none'
    })
    return
  }
  if (!formData.confirmPassword) {
    uni.showToast({
      title: '请确认新密码',
      icon: 'none'
    })
    return
  }
  if (formData.password !== formData.confirmPassword) {
    uni.showToast({
      title: '两次输入的密码不一致',
      icon: 'none'
    })
    return
  }

  try {
    await changePassword({
      password: formData.password,
      oldPassword: formData.oldPassword
    })
    
    uni.showToast({
      title: '密码修改成功',
      icon: 'success'
    })
    
    // 修改成功后返回上一页
    setTimeout(() => {
      uni.navigateBack()
    }, 1500)
    
  } catch (error) {
    uni.showToast({
      title: error.message || '修改失败',
      icon: 'none'
    })
  }
}
</script>

<style lang="scss" scoped>
.change-password-container {
  min-height: 100vh;
  background-color: #f5f5f5;
  padding: 40rpx;
  
  .form-box {
    background-color: #fff;
    border-radius: 16rpx;
    padding: 30rpx;
    
    .input-item {
      margin-bottom: 30rpx;
      
      .label {
        display: block;
        margin-bottom: 10rpx;
        font-size: 28rpx;
        color: #333;
      }
      
      .input {
        width: 100%;
        height: 80rpx;
        border: 1px solid #ddd;
        border-radius: 8rpx;
        padding: 0 20rpx;
        font-size: 28rpx;
        box-sizing: border-box;
      }
    }
    
    .submit-btn {
      margin-top: 40rpx;
      width: 100%;
    }
  }
}
</style> 