<style scoped="">
  .user-info{
    float: right;
    margin: 11px 6px 0 10px;
    font-size: 15px;
  }
  .user-info:hover .drop-down{
    display: block;
  }
  .user-info img{
    width: 32px;
    height: 32px;
    border: 1px solid #ddd;
    border-radius: 50%;
  }
  .drop-down{
    display: none;
    position: absolute;
    top: 55px;
    z-index: 1;
    padding: 10px 20px 10px 20px;
    margin-left: -12px;
    min-width: 80px;
    background-color: #fff;
    border-radius: 4px;
    box-shadow: 0 2px 8px rgba(0,0,0,.1);
    filter: drop-shadow(0 0 8px rgba(0,0,0,.1));
    -webkit-filter: drop-shadow(0 0 8px rgba(0,0,0,.1));
  }
  .drop-down:after {
    position: absolute;
    top: -17px;
    left: 18%;
    content: "";
    display: inline-block;
    border: 9px solid transparent;
    border-bottom: 9px solid #fff;
    z-index: 1000;
  }
  .drop-down a{
    font-size: 15px;
    display: block;
    padding: 4px 0px 4px 0px;
    color: #969696;
  }

  .drop-down >li >a:hover{
    color: #ea6f5a;
  }
</style>
<template>
  <!-- 全局顶部导航-->
  <div class="nav">
    <div class="nav-container">
      <a class="logo" href="/"><img src="../images/log.png" alt="Logo"></a>
      <a class="btn write-btn" target="_blank" href="javascript:void(0)" @click="jumpCreateTopic()">写文章</a>
      <a class="btn log-in"  href="javascript:void(0);"  v-if="user.userName==null" @click="openBoxOne(true)">注册</a>
      <a class="btn log-in" href="javascript:void(0)" @click="openBoxOne(false)" v-if="user.userName==null">登录</a>
      <a class=" user-info" v-if="user.userName!=null">
        <div style="float: left"><img :src="user.avatarUrl"/></div>
        <div style="float: left;margin-left: 5px ;line-height: 30px">
          <span>{{user.nickName}}</span> <i class="fa fa-caret-down" aria-hidden="true"></i>
        </div>
       <!-- <ul class="drop-down">
          <li><router-link :to="{ path: 'personal'}" target="_blank" class="title"><i class="fa fa-user-o" aria-hidden="true"></i> 我的主页</router-link></li>
          <li><a href="javascript:void(0)" @click="signOut()"><i class="fa fa-sign-out" aria-hidden="true"></i> 退出</a></li>
        </ul>-->
        <div class="drop-down">
          <div><router-link :to="{ path: 'userInfo'}" target="_blank"><i class="fa fa-user-o" aria-hidden="true"></i> 我的主页</router-link></div>
          <div><a href="javascript:void(0)" @click="signOut()"style="margin-top: 10px"><i class="fa fa-sign-out" aria-hidden="true"></i> 退出</a></div>
        </div>
      </a>
      <a class="btn index" href="/"><i class="fa fa-home fa-lg" aria-hidden="true"></i>  首页</a>
      <a class="btn index" href="/"><i class="fa fa-mobile-phone fa-lg"  aria-hidden="true"></i>  下载APP</a>
      <div class="btn search">
        <input type="text" placeholder="搜索">
        <i class="fa fa-search" aria-hidden="true"></i>
      </div>
    </div>
    <v-login v-if="isOpenOne" :handler="closeBoxOne" :flag="flag"></v-login>
  </div>
</template>

<script>
  import login from './login.vue'
  import {mapGetters} from 'vuex'
 export default {
   name:'header',
   data(){
     return{
       flag:false
     }
   },
   computed: {
     ...mapGetters({user:'user'}),

     isOpenOne:function () {
       return this.$root.$data.$isOpen;
     }
   }
   ,
   components:{
     "v-login":login
   },
   methods:{
     /**
      * 打开登陆弹框
      */
     openBoxOne(flag){
        let vm=this;
        vm.$set(vm.$root.$data,'$isOpen',true)
        vm.flag=flag
     },
     /**
      * 关闭登陆弹框
      */
     closeBoxOne(){
       this.$root.$data.$isOpen=false;
     },
     /**
      * 跳转写文章
      */
     jumpCreateTopic(){
       this.$router.push({path:'createTopic'})
     },

     /**
      * 退出
      */
     signOut(){
       debugger;
       this.$store.dispatch('signOut')
     }
   }

 }
</script>


