import Vue from 'vue'
import Router from 'vue-router'
import home from '@/pages/home.vue'
import createTopic from '@/pages/createTopic.vue'
import topicDetails from '@/pages/topicDetails.vue'
import userInfo from '@/pages/userInfo.vue'
import subject from '@/pages/subject.vue'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: home
    },
    {
      path: '/createTopic',
      name: 'createTopic',
      meta: { auth: true },
      component: createTopic
    },
    {
      path: '/topicDetails',
      name: 'topicDetails',
      component: topicDetails
    },
    {
      path: '/userInfo',
      name: 'userInfo',
      component: userInfo
    },
    {
      path: '/subject',
      name: 'subject',
      component: subject
    }
  ]
})
