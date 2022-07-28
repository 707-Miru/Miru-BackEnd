import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '@/views/LoginView.vue'
import SignupView from '@/views/SingupView.vue'
import HomeView from '@/views/HomeView.vue'
import FindPassWordView from '@/views/FindPassWordView.vue'
import ResetPassWordView from '@/views/ResetPassWordView.vue'
import store from '../store'


const routes = [
  {
    path : "/",
    name : "HomeView",
    component : HomeView,
  },
  {
    path : "/login",
    name : "LoginView",
    component : LoginView,
  },
  {
    path : "/signup",
    name : "SignupView",
    component : SignupView,
  },
  {
    path : "/findpw",
    name : "FindPassWordView",
    component : FindPassWordView,
  },
  {
    path : "/resetpw",
    name : "ResetPassWordView",
    component : ResetPassWordView,
  }


]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

router.beforeEach((to, from, next) => {
  // 이전 페이지에서 발생한 에러메시지 삭제
  store.commit('SET_AUTH_ERROR', null)

  const { isLoggedIn } = store.getters

  const noAuthPages = ['LoginView', 'SignupView', 'FindPassWordView']

  const isAuthRequired = !noAuthPages.includes(to.name)

  if (isAuthRequired && !isLoggedIn) {
    alert('Require Login. Redirecting..')
    next({ name: 'LoginView' })
  } else if (!isAuthRequired && isLoggedIn) {
    next({ name: 'HomeView' })
  } else {
    next()
  }



})

export default router
