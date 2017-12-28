import Vue from 'vue'
import Vuex from 'vuex'
import user from './modules/user'
import conf from './modules/conf'
import subject from './modules/subject'
Vue.use(Vuex)
export default new Vuex.Store({
  modules: {
    user,conf,subject
  },
});
