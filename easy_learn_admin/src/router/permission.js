import router from '@/router'
import { useUserStore } from '@/stores/user'

// 白名单路由
const whiteList = ['/login']

router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  const userInfo = userStore.userInfo

  if (userInfo) {
    // 已登录
    if (to.path === '/login') {
      // 如果已登录，访问登录页则重定向到首页
      next('/')
    } else {
      // 检查路由权限
      if (to.meta.roles) {
        // 检查用户是否有权限访问该路由
        const hasRole = userInfo.roleList.some(role =>
          to.meta.roles.includes(role.roleName)
        )
        if (hasRole) {
          next()
        } else {
          // 无权限时重定向到章管理页面
          next('/directory/chapter')
        }
      } else {
        // 如果是访问根路径，根据角色重定向
        if (to.path === '/') {
          const isSuperAdmin = userInfo.roleList.some(role => role.roleName === 'ROLE_superadmin')
          // 超级管理员跳转到用户管理，其他角色跳转到章管理
          next(isSuperAdmin ? '/user' : '/directory/chapter')
        } else {
          next()
        }
      }
    }
  } else {
    // 未登录
    if (whiteList.includes(to.path)) {
      next()
    } else {
      next('/login')
    }
  }
})

export default router
