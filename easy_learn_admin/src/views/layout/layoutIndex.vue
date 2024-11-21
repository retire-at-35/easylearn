<template>
  <el-container class="layout-container">
    <!-- 侧边菜单 -->
    <el-aside :width="isCollapse ? '64px' : '200px'" class="aside-container">
      <div class="logo-container">
        <!-- <img src="@/assets/logo.png" alt="logo" class="logo-img" /> -->
        <span class="logo-text" v-show="!isCollapse">Easy Learn</span>
      </div>
      <el-menu
        :default-active="activeMenu"
        class="el-menu-vertical"
        :collapse="isCollapse"
        background-color="#304156"
        text-color="#bfcbd9"
        active-text-color="#409EFF"
        router
      >
        <el-menu-item v-if="isSuper" index="/user">
          <el-icon><user /></el-icon>
          <template #title >用户管理</template>
        </el-menu-item>

        <el-sub-menu index="/directory">
          <template #title>
            <el-icon><folder /></el-icon>
            <span>目录管理</span>
          </template>
          <el-menu-item index="/directory/chapter">章管理</el-menu-item>
          <el-menu-item index="/directory/section">节管理</el-menu-item>
        </el-sub-menu>

        <el-menu-item index="/type">
          <el-icon><List /></el-icon>
          <template #title>类型管理</template>
        </el-menu-item>

        <el-menu-item index="/question">
          <el-icon><Document /></el-icon>
          <template #title>题目管理</template>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <el-container class="main-wrapper">
      <!-- 顶部导航栏 -->
      <el-header class="header">
        <div class="header-left">
          <el-icon
            class="collapse-btn"
            @click="toggleCollapse"
          >
            <component :is="isCollapse ? 'Expand' : 'Fold'" />
          </el-icon>
          <el-breadcrumb separator="/">
            <!-- <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item> -->
            <el-breadcrumb-item>{{ route.meta.title || '活动管理' }}</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <div class="header-right">
          <el-dropdown trigger="click">
            <div class="avatar-wrapper">
              <el-avatar :size="32" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" />
              <span class="user-name">{{ userStore.userInfo?.username || '管理员' }}</span>
              <el-icon class="el-icon--right"><arrow-down /></el-icon>
            </div>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="handleLogout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>
-
      <!-- 主要内容区域 -->
      <el-main class="main-container">
        <router-view  v-slot="{ Component }">
          <transition name="fade-transform" mode="out-in">
            <component :is="Component" />
          </transition>
        </router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRoute } from 'vue-router'
import { ArrowDown, User, Document, Folder, Expand, Fold, List } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'

const isCollapse = ref(false)
const route = useRoute()
const activeMenu = computed(() => route.path)

const toggleCollapse = () => {
  isCollapse.value = !isCollapse.value
}

const router = useRouter()
const userStore = useUserStore()
const isSuper = computed(() => {
  return userStore.userInfo?.roleList.some(role => role.roleName === 'ROLE_superadmin')
})
const handleLogout = () => {
  ElMessageBox.confirm(
    '确认退出登录吗？',
    '提示',
    {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
    }
  ).then(() => {
    userStore.clearUserInfo()
    router.push('/login')
  }).catch(() => {
    // 取消退出，不做任何操作
  })
}
</script>

<style scoped>
.layout-container {
  width: 100vw;
  height: 100vh;
  display: flex;
  margin: 0;
  padding: 0;
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
}

.aside-container {
  height: 100vh;
  transition: width 0.3s;
  background-color: #304156;
  overflow: hidden;
  position: relative;
  left: 0;
  top: 0;
  bottom: 0;
  margin: 0;
  padding: 0;
}

.logo-container {
  height: 60px;
  display: flex;
  align-items: center;
  padding: 0 16px;
  background: #2b2f3a;
}

.logo-img {
  width: 40px;
  height: 40px;
  margin-right: 12px;
  object-fit: contain;
}

.logo-text {
  color: #fff;
  font-size: 22px;
  font-weight: 600;
  white-space: nowrap;
  overflow: hidden;
  letter-spacing: 1px;
}

.el-menu-vertical {
  border-right: none;
  height: calc(100% - 60px);
}

.el-menu-vertical:not(.el-menu--collapse) {
  width: 200px;
}

.main-wrapper {
  flex: 1;
  height: 100vh;
  overflow: hidden;
  margin: 0;
  padding: 0;
  display: flex;
  flex-direction: column;
}

.header {
  background-color: #fff;
  border-bottom: 1px solid #dcdfe6;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 20px;
}

.collapse-btn {
  font-size: 20px;
  cursor: pointer;
  transition: color 0.3s;
}

.collapse-btn:hover {
  color: #409EFF;
}

.header-right {
  display: flex;
  align-items: center;
}

.avatar-wrapper {
  display: flex;
  align-items: center;
  cursor: pointer;
  padding: 0 8px;
}

.user-name {
  margin: 0 8px;
  color: #606266;
}

.main-container {
  padding: 20px;
  background-color: #f0f2f5;
  height: calc(100vh - 60px);
  overflow-y: auto;
}

/* 响应式设计 */
@media screen and (max-width: 768px) {
  .aside-container {
    position: fixed;
    z-index: 1000;
    height: 100%;
  }

  .header {
    padding: 0 10px;
  }

  .logo-text,
  .user-name {
    display: none;
  }

  .main-container {
    padding: 10px;
  }
}

/* 过渡动画 */
.fade-transform-enter-active,
.fade-transform-leave-active {
  transition: all 0.5s;
}

.fade-transform-enter-from {
  opacity: 0;
  transform: translateX(-30px);
}

.fade-transform-leave-to {
  opacity: 0;
  transform: translateX(30px);
}

/* 折叠时的 logo 样式 */
.el-menu--collapse + .logo-container .logo-img {
  margin-right: 0;
}
</style>
