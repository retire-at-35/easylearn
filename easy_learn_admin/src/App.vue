<script setup>
</script>

<template>
  <router-view></router-view>
</template>

<style scoped>
/* 登录页面容器 */
.login-container {
  display: flex;
  height: 100vh;
  width: 150vh;
  background-color: #f5f5f5;
}

/* 左侧图片区域 */
.login-left {
  flex: 1.2;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #e6f7ff;
}

.login-image {
  max-width: 60%;
  max-height: 60%;
  object-fit: contain;
}

/* 右侧表单区域 */
.login-right {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 40px;
  background-color: #ffffff;
  box-shadow: -2px 0 5px rgba(0, 0, 0, 0.1);
}

/* 标题样式 */
.login-title {
  font-size: 28px;
  font-weight: bold;
  margin-bottom: 30px;
  color: #333;
}

/* 表单样式 */
.login-form {
  width: 100%;
  max-width: 400px;
}

.el-input {
  margin-bottom: 25px;
}

/* 验证码图片容器 */
.captcha-container {
  display: flex;
  justify-content: center;
  margin-bottom: 25px;
}

.captcha-image {
  width: 100%;
  max-width: 250px;
  height: auto;
  cursor: pointer;
  border: 1px solid #dcdcdc;
  border-radius: 4px;
}

/* 登录按钮样式 */
.login-button {
  width: 100%;
}
</style>

