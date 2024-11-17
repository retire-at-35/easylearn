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
      component: () => import('../views/layout/layoutIndex.vue')
    },
  ],
})

export default router
