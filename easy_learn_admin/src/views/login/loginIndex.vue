<template>
  <div class="login-container">
    <!-- 左侧图像区域 -->
    <div class="login-left">
      <img class="login-image" src="..\assert\bg.jpeg" alt="login-logo" />
    </div>

    <!-- 右侧表单区域 -->
    <div class="login-right">
      <h2 class="login-title">用户登录</h2>
      <el-form :model="loginForm" :rules="rules" ref="loginFormRef" class="login-form">
        <!-- 用户名输入框 -->
        <el-form-item label="用户名" prop="username">
          <el-input v-model="loginForm.username" placeholder="请输入用户名" />
        </el-form-item>

        <!-- 密码输入框 -->
        <el-form-item label="密码" prop="password">
          <el-input
            v-model="loginForm.password"
            placeholder="请输入密码"
            show-password
          />
        </el-form-item>

        <!-- 验证码输入框 -->
        <el-form-item label="验证码" prop="captcha">
          <el-input v-model="loginForm.captcha" placeholder="请输入验证码" />
        </el-form-item>

        <!-- 验证码图片 -->
        <div class="captcha-container">
          <img
            class="captcha-image"
            src=""
            alt="captcha"
            @click="refreshCaptcha"
          />
        </div>

        <!-- 登录按钮 -->
        <el-form-item>
          <el-button type="primary" class="login-button" @click="handleLogin">
            登录
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from "vue";

// 登录表单数据
const loginForm = reactive({
  username: "",
  password: "",
  captcha: "",
});

// 表单校验规则
const rules = {
  username: [
    { required: true, message: "用户名不能为空", trigger: "blur" },
    { min: 6, max: 11, message: "用户名长度必须为6到11位", trigger: "blur" },
  ],
  password: [
    { required: true, message: "密码不能为空", trigger: "blur" },
    { min: 6, max: 11, message: "密码长度必须为6到11位", trigger: "blur" },
  ],
  captcha: [{ required: true, message: "验证码不能为空", trigger: "blur" }],
};

// 引用表单实例
const loginFormRef = ref(null);

// 登录逻辑
const handleLogin = () => {
  loginFormRef.value.validate((valid) => {
    if (valid) {
      console.log("登录信息：", loginForm);
    } else {
      console.log("表单验证失败");
    }
  });
};

// 刷新验证码
const refreshCaptcha = () => {
  console.log("刷新验证码");
  // 这里可以更新验证码图片的 URL
};
</script>

<style scoped>
/* 登录页面容器 */
.login-container {
  display: flex;
  height: 100vh;
  background-color: #f5f5f5;
}

/* 左侧图片区域 */
.login-left {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #e6f7ff;
}

.login-image {
  max-width: 80%;
  max-height: 80%;
  object-fit: contain;
}

/* 右侧表单区域 */
.login-right {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 20px;
  background-color: #ffffff;
  box-shadow: -2px 0 5px rgba(0, 0, 0, 0.1);
}

/* 标题样式 */
.login-title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 20px;
}

/* 表单样式 */
.login-form {
  width: 100%;
  max-width: 300px;
}

.el-input {
  margin-bottom: 20px;
}

/* 验证码图片容器 */
.captcha-container {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}

.captcha-image {
  width: 100%;
  max-width: 200px;
  height: auto;
  cursor: pointer;
}

/* 登录按钮样式 */
.login-button {
  width: 100%;
}
</style>
