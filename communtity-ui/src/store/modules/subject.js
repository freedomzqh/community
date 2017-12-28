import Vue from 'vue'
const SUBJECT='subject';
const SET_SUBJECT='set_subject'
export  default {
  state: {
    subjects:JSON.parse(sessionStorage.getItem(SUBJECT))||[]
  },
  mutations:{
    [SET_SUBJECT](state,subject){
      sessionStorage.setItem(SUBJECT,JSON.stringify(subject));
      Vue.set(state,'subjects',subject)
    }
  },
  getters:{
    subjects:state => {
      return state.subjects;
    }
  },
  actions:{
    [SET_SUBJECT]({ commit },subject){
      commit(SET_SUBJECT,subject)
    }
  }
}
