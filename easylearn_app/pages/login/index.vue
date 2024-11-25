<template>
  <view class="login-container">
    <!-- Logo -->
    <view class="logo-box">
      <image class="logo" src="/static/logo.png" mode="aspectFit"></image>
    </view>
    
    <!-- 登录表单 -->
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
      
      <!-- 登录按钮 -->
      <button 
        type="primary" 
        @tap="handleLogin"
        class="login-btn"
      >登录</button>
      
      <!-- 注册入口 -->
      <view class="register-link" @tap="goToRegister">
        <text>没有账号？去注册</text>
      </view>
    </view>
  </view>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { login } from '@/api/user.js'

// 验证码URL
const captchaUrl = ref('')

// 表单数据
const formData = reactive({
  username: '',
  password: '',
  verifyCode: ''
})

// 刷新验证码
const refreshCaptcha = () => {
  captchaUrl.value = `http://localhost:8080/pub/getCheckCode?t=${Date.now()}`
}

// 处理登录
const handleLogin = () => {
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
  if (!formData.verifyCode) {
    uni.showToast({
      title: '请输入验证码',
      icon: 'none'
    })
    return
  }
  
  // 构造登录参数
  const loginData = {
    username: formData.username,
    password: formData.password,
    checkCode: formData.verifyCode
  }
  
  login(loginData)
    .then(res => {
      // 存储token
      uni.setStorageSync('token', res.token)
      // 存储用户信息
      uni.setStorageSync('userInfo', {
        id: res.id,
        username: res.username,
        avatar: res.avatar
      })
      
      uni.showToast({
        title: '登录成功',
        icon: 'success',
        duration: 2000
      })
      
      // 登录成功后跳转到首页
      setTimeout(() => {
        uni.switchTab({
          url: '/pages/index/index',
          success: () => {
            console.log('跳转成功')
          },
          fail: (error) => {
            console.error('跳转失败:', error)
          }
        })
      }, 2000)
    })
    .catch(err => {
      // 登录失败刷新验证码
      refreshCaptcha()
      uni.showToast({
        title: err.message || '登录失败',
        icon: 'none'
      })
    })
}

// 跳转到注册页面
const goToRegister = () => {
  uni.navigateTo({
    url: '/pages/register/index'
  })
}

// 页面加载时刷新验证码
onMounted(() => {
  // 设置页面标题
  uni.setNavigationBarTitle({
    title: '登录'
  })
  // 刷新验证码
  refreshCaptcha()
})
</script>

<style lang="scss" scoped>
.login-container {
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
    
    .login-btn {
      margin-top: 40rpx;
      width: 100%;
    }
    
    .register-link {
      text-align: center;
      margin-top: 30rpx;
      color: #2979ff;
      font-size: 28rpx;
    }
  }
}
</style> 