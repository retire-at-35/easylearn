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
      redirect: '/directory/chapter',
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
        },
        {
          path: 'questions/single',
          name: 'singleQuestion',
          component: () => import('@/views/questions/single.vue'),
          meta: {
            title: '单选题管理'
          }
        },
        {
          path: 'questions/multiple',
          name: 'multipleQuestion',
          component: () => import('@/views/questions/multiple.vue'),
          meta: {
            title: '多选题管理'
          }
        },
        {
          path: 'questions/judge',
          name: 'judgeQuestion',
          component: () => import('@/views/questions/judge.vue'),
          meta: {
            title: '判断题管理'
          }
        }
      ]
    }
  ]
})

export default router
