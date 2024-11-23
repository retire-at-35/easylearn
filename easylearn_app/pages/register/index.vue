<template>
  <view class="register-container">
    <!-- Logo -->
    <view class="logo-box">
      <image class="logo" src="/static/logo.png" mode="aspectFit"></image>
    </view>
    
    <!-- 注册表单 -->
    <view class="form-box">
      <!-- 账号输入框 -->
      <view class="input-item">
        <text class="label">账号</text>
        <input 
          type="text"
          v-model="formData.username"
          placeholder="请输入账号"
          class="input"
        />
      </view>
      
      <!-- 密码输入框 -->
      <view class="input-item">
        <text class="label">密码</text>
        <input 
          type="password"
          v-model="formData.password"
          placeholder="请输入密码"
          class="input"
        />
      </view>
      
      <!-- 确认密码 -->
      <view class="input-item">
        <text class="label">确认密码</text>
        <input 
          type="password"
          v-model="formData.confirmPassword"
          placeholder="请再次输入密码"
          class="input"
        />
      </view>
      
      <!-- 验证码输入框 -->
      <view class="input-item">
        <text class="label">验证码</text>
        <view class="verify-box">
          <input 
            type="text"
            v-model="formData.verifyCode"
            placeholder="请输入验证码"
            class="input verify-input"
          />
          <image 
            class="verify-img"
            :src="captchaUrl"
            @tap="refreshCaptcha"
            mode="aspectFit"
          />
        </view>
      </view>
      
      <!-- 注册按钮 -->
      <button 
        type="primary" 
        @tap="handleRegister"
        class="register-btn"
      >注册</button>
      
      <!-- 返回登录 -->
      <view class="login-link" @tap="goToLogin">
        <text>已有账号？去登录</text>
      </view>
    </view>
  </view>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { register } from '@/api/user.js'

// 验证码URL
const captchaUrl = ref('')

// 表单数据
const formData = reactive({
  username: '',
  password: '',
  confirmPassword: '',
  verifyCode: ''
})

// 刷新验证码
const refreshCaptcha = () => {
  captchaUrl.value = `http://localhost:8080/pub/getCheckCode?t=${Date.now()}`
}

// 处理注册
const handleRegister = () => {
  // 表单验证
  if (!formData.username) {
    uni.showToast({
      title: '请输入账号',
      icon: 'none'
    })
    return
  }
  if (!formData.password) {
    uni.showToast({
      title: '请输入密码',
      icon: 'none'
    })
    return
  }
  if (formData.password.length < 6) {
    uni.showToast({
      title: '密码长度不能少于6位',
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
  if (!formData.verifyCode) {
    uni.showToast({
      title: '请输入验证码',
      icon: 'none'
    })
    return
  }
  
  // 构造注册参数
  const registerData = {
    username: formData.username,
    password: formData.password,
    checkCode: formData.verifyCode
  }
  
  console.log('注册数据:', registerData)
  
  register(registerData)
    .then(() => {
      uni.showToast({
        title: '注册成功',
        icon: 'success',
        duration: 2000
      })
      
      // 注册成功后延迟返回登录页
      setTimeout(() => {
        uni.navigateBack()
      }, 2000)
    })
    .catch(err => {
      // 注册失败刷新验证码
      refreshCaptcha()
      uni.showToast({
        title: err.message || '注册失败',
        icon: 'none'
      })
    })
}

// 返回登录页面
const goToLogin = () => {
  uni.navigateBack()
}

// 页面加载时刷新验证码
onMounted(() => {
  // 设置页面标题
  uni.setNavigationBarTitle({
    title: '注册'
  })
  // 刷新验证码
  refreshCaptcha()
})
</script>

<style lang="scss" scoped>
.register-container {
  min-height: 100vh;
  background-color: #f5f5f5;
  padding: 40rpx;
  
  .logo-box {
    padding: 60rpx 0;
    display: flex;
    justify-content: center;
    
    .logo {
      width: 200rpx;
      height: 200rpx;
    }
  }
  
  .form-box {
    background-color: #fff;
    border-radius: 16rpx;
    padding: 30rpx;
    box-shadow: 0 2rpx 12rpx rgba(0, 0, 0, 0.1);
    
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
      
      .verify-box {
        display: flex;
        align-items: center;
        gap: 20rpx;
        
        .verify-input {
          flex: 1;
        }
        
        .verify-img {
          width: 130px;
          height: 38px;
          border: 1px solid #eee;
        }
      }
    }
    
    .register-btn {
      margin-top: 40rpx;
      width: 100%;
    }
    
    .login-link {
      text-align: center;
      margin-top: 30rpx;
      color: #2979ff;
      font-size: 28rpx;
    }
  }
}
</style> 