import router from '@/router'
import { useUserStore } from '@/stores/user'

// 白名单路由
const whiteList = ['/login']

router.beforeEach(async (to, from, next) => {
  const userStore = useUserStore()
  const userInfo = userStore.userInfo

  if (userInfo) {
    // 已登录
    if (to.path === '/login') {
      next('/')
    } else {
      // 检查路由权限
      if (to.meta.roles) {
        const hasRole = userInfo.roleList.some(role =>
          to.meta.roles.includes(role.roleName)
        )
        if (hasRole) {
          next()
        } else {
          next('/directory/chapter')
        }
      } else {
        if (to.path === '/') {
          next('/directory/chapter')
        } else {
          // 确保路由存在
          if (to.matched.length === 0) {
            next('/directory/chapter')
          } else {
            next()
          }
        }
      }
    }
  } else {
    if (whiteList.includes(to.path)) {
      next()
    } else {
      next('/login')
    }
  }
})

export default router
