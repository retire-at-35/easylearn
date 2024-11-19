import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/login/loginIndex.vue')
    },
    {
      path: '/',
      name: 'layout',
      component: () => import('../views/layout/layoutIndex.vue'),
      children: [
        {
          path: 'user',
          name: 'user',
          component: () => import('@/views/user/index.vue'),
          meta: {
            title: '用户管理',
            roles: ['ROLE_superadmin']
          }
        },
        {
          path: 'directory/chapter',
          name: 'chapter',
          component: () => import('@/views/directory/chapter.vue'),
          meta: {
            title: '章管理'
          }
        },
        {
          path: 'directory/section',
          name: 'section',
          component: () => import('@/views/directory/section.vue'),
          meta: {
            title: '节管理'
          }
        }
      ]
    }
  ]
})

export default router
