<template>
  <div class="login-container">
    <div class="login-box">
      <!-- 左侧装饰区域 -->
      <div class="login-left">
        <div class="welcome-text">
          <h2>Welcome Back!</h2>
          <p>欢迎使用 Easy Learn 后台管理系统</p>
        </div>
        <div class="decoration-image"></div>
      </div>

      <!-- 右侧登录表单 -->
      <div class="login-form">
        <div class="form-header">
          <img src="@/assets/logo.png" alt="logo" class="logo">
          <h3>账号登录</h3>
        </div>

        <el-form
          ref="loginFormRef"
          :model="loginForm"
          :rules="loginRules"
          size="large"
        >
          <el-form-item prop="username">
            <el-input
              v-model="loginForm.username"
              placeholder="请输入用户名"
              :prefix-icon="User"
            />
          </el-form-item>

          <el-form-item prop="password">
            <el-input
              v-model="loginForm.password"
              type="password"
              placeholder="请输入密码"
              :prefix-icon="Lock"
              @keyup.enter="handleLogin"
            />
          </el-form-item>

          <el-form-item prop="checkCode">
            <div class="captcha-container">
              <el-input
                v-model="loginForm.checkCode"
                placeholder="请输入验证码"
                :prefix-icon="Key"
              />
              <img
                :src="captchaUrl"
                class="captcha-img"
                @click="refreshCaptcha"
                alt="验证码"
              />
            </div>
          </el-form-item>

          <el-form-item>
            <el-button
              type="primary"
              class="login-button"
              :loading="loading"
              @click="handleLogin"
            >
              登 录
            </el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { User, Lock, Key } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'
import { loginAPI } from '@/api/login'
import { useUserStore } from '@/stores/user'
const router = useRouter()
const loading = ref(false)
const loginFormRef = ref()
const userStore = useUserStore()

const captchaUrl = ref('http://localhost:8080/pub/checkCode')

const loginForm = reactive({
  username: 'cccc',
  password: '123456',
  checkCode: ''
})

const loginRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '用户名长度应在 3 到 20 个字符之间', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 20, message: '密码长度应在 6 到 20 个字符之间', trigger: 'blur' }
  ],
  checkCode: [
    { required: true, message: '请输入验证码', trigger: 'blur' },
    { min: 5, max: 5, message: '验证码长度应为 5 位', trigger: 'blur' }
  ]
}

const login =async () => {
  const res = await loginAPI(loginForm)
  userStore.setUserInfo(res)
}

const handleLogin = () => {
  loginFormRef.value?.validate(async (valid) => {
    if (valid) {
      loading.value = true;
      try {
        login(loginForm);
        ElMessage.success('登录成功');
        router.push('/');
      } catch (error) {
        console.log('登录失败', error);
      } finally {
        loading.value = false;
      }
    }
  });
};



const refreshCaptcha = () => {
  captchaUrl.value = `http://localhost:8080/pub/checkCode?t=${new Date().getTime()}`
}
</script>

<style scoped>
.login-container {
  height: 100vh;
  width: 100vw;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #e0eafc 0%, #cfdef3 100%);
  overflow: hidden;
}

.login-box {
  width: 900px;
  height: 560px;
  background: #fff;
  border-radius: 20px;
  box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.1);
  display: flex;
  overflow: hidden;
  animation: fadeIn 0.8s ease-out;
}

.login-left {
  flex: 1;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 40px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  color: white;
  position: relative;
  overflow: hidden;
}

.welcome-text {
  position: relative;
  z-index: 1;
}

.welcome-text h2 {
  font-size: 32px;
  margin-bottom: 10px;
  font-weight: 600;
}

.welcome-text p {
  font-size: 16px;
  opacity: 0.9;
}

.decoration-image {
  position: absolute;
  bottom: -10%;
  right: -10%;
  width: 300px;
  height: 300px;
  background: url('@/assets/decoration.svg') no-repeat center;
  background-size: contain;
  opacity: 0.1;
  animation: float 6s ease-in-out infinite;
}

.login-form {
  flex: 1;
  padding: 40px;
  display: flex;
  flex-direction: column;
}

.form-header {
  text-align: center;
  margin-bottom: 40px;
}

.logo {
  width: 64px;
  height: 64px;
  margin-bottom: 16px;
}

.form-header h3 {
  font-size: 24px;
  color: #333;
  font-weight: 600;
}

.el-input {
  height: 50px;
}

:deep(.el-input__wrapper) {
  background-color: #f5f7fa;
  box-shadow: none !important;
  border: 2px solid transparent;
  transition: all 0.3s;
}

:deep(.el-input__wrapper:hover) {
  border-color: #dcdfe6;
}

:deep(.el-input__wrapper.is-focus) {
  border-color: #409eff;
  background-color: #fff;
}

.login-button {
  width: 100%;
  height: 50px;
  font-size: 16px;
  font-weight: 600;
  margin-top: 20px;
  border-radius: 8px;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes float {
  0% {
    transform: translateY(0px);
  }
  50% {
    transform: translateY(-20px);
  }
  100% {
    transform: translateY(0px);
  }
}

/* 响应式设计 */
@media screen and (max-width: 768px) {
  .login-box {
    width: 90%;
    height: auto;
    flex-direction: column;
  }

  .login-left {
    padding: 30px;
    min-height: 200px;
  }

  .welcome-text h2 {
    font-size: 24px;
  }

  .welcome-text p {
    font-size: 14px;
  }

  .login-form {
    padding: 30px;
  }

  .decoration-image {
    width: 200px;
    height: 200px;
  }
}

.captcha-container {
  display: flex;
  align-items: center;
  gap: 12px;
}

.captcha-img {
  height: 50px;
  border-radius: 4px;
  cursor: pointer;
  transition: opacity 0.3s;
}

.captcha-img:hover {
  opacity: 0.8;
}

:deep(.captcha-container .el-input) {
  flex: 1;
}

/* 确保验证码输入框和图片在移动端也能正确显示 */
@media screen and (max-width: 768px) {
  .captcha-container {
    gap: 8px;
  }

  .captcha-img {
    height: 40px;
  }
}
</style>

