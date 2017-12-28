// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
import axios from 'axios'
import './font-awesome-4.7.0/css/font-awesome.min.css'
import './css/common.css'
import ElementUI from 'element-ui'
import components from './components/'
import 'element-ui/lib/theme-chalk/index.css'
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
Vue.use(ElementUI);
Vue.config.productionTip = false
Vue.prototype.$message=ElementUI.Message;
Vue.prototype.$axios=axios;
Object.keys(components).forEach((key) => {
  var name = key.replace(/(\w)/, (v) => v.toUpperCase()) //首字母大写
  Vue.component(`v${name}`, components[key])
})

router.beforeEach(({meta, path}, from, next) => {
  var { auth = false } = meta
  var isLogin = Boolean(store.state.user.user.userId) //true用户已登录， false用户未登录
  if (auth && !isLogin && path !== '/login') {
    vm.$data.$isOpen=true;
    return;
  }
  next()
})
/* eslint-disable no-new */
var vm=new Vue({
  el: '#app',
  router,
  store,
  data(){
    return{
      $isOpen:false
    }
  },
  created:function () {
      axios({
        url:'http://localhost:8080/cmt/api/v1/subject/getSubjects',
        method:'get'
      })
        .then(function (res) {
          if(res.data.code==1){
            store.dispatch('set_subject',res.data.data);
          }else {
            console.log(res)
          }
        })
        .catch(function (err) {
          console.log(err)
        })
  },
  template: '<App/>',
  components: { App }
})
