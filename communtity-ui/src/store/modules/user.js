import Vue from 'vue'
const LOGIN='login';
const SIGN_OUT='signOut'
export  default {
  state: {
    user:JSON.parse(sessionStorage.getItem('user'))||{}
  },
  mutations:{
    login(state,user){
      sessionStorage.setItem('user',JSON.stringify(user));
      Vue.set(state,'user',user);
    },

    signOut(state){
      sessionStorage.removeItem('user');
      Object.keys(state).forEach(k => Vue.delete(state, k))
    },

  },

  getters:{
    user:state =>{
      return state.user;
    }
  },
  actions:{
    login({ commit },user){
     commit('login',user)
    },

    signOut({commit}){
      commit('signOut')
    },
  }
}
