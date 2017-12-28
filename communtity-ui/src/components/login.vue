<style scoped="">
  .mask-layer{
    position: fixed;
    left: 0;
    top: 0;
    width: 100%;
    height:100%;
    zoom: 1;
    background: #333;
    z-index: 999;
    filter:alpha(opacity=50); /*IE滤镜，透明度50%*/
    -moz-opacity:0.8; /*Firefox私有，透明度50%*/
    opacity:0.8;/*其他，透明度50%*/
  }
  .register{
    position: fixed;
    margin: auto;
    top:0;
    bottom: 0;
    left: 0;
    right: 0;
    background: #fff;
    border-radius: 4px;
    width: 400px;
    height: 400px;
    z-index: 9999999
  }
  .login{
    position: fixed;
    margin: auto;
    top:0;
    bottom: 0;
    left: 0;
    right: 0;
    background: #fff;
    border-radius: 4px;
    width: 400px;
    height: 275px;
    z-index: 9999999;
  }
  .register-item{
    margin: 20px auto;
    width: 300px;
  }
  .close{
    float: right;
    color: #969696;
  }
  .close :hover{
   background-color: #ea6f5a;
  }
  .login-register {
    color: #969696;
  }
  .login-register:hover {
    color: #646464;
    border-bottom: 2px solid #646464;
  }


</style>
<template>
  <div>
    <div class="mask-layer"></div>
    <div class="register-login">
      <div class="register" v-if="flag1">
        <a class="close"  href="javascript:void(0)" @click="close"><i  style="font-size: 25px" class="el-icon-close"></i></a>
        <div style="padding: 10px;text-align: center; margin: 20px">
          <a class="login-register" href="javascript:void(0)" @click="goLogin()"><span style="font-weight: 700;">登录</span></a>
          <span style="font-weight: 700; color: #969696; margin-right: 20px; margin-left: 20px">|</span>
          <a href="javascript:void(0)" @click="goRegister()"><span style="font-weight:700; color: #646464;border-bottom: 2px solid #646464;">注册</span></a>
        </div>
        <div class="register-item">
          <el-input v-model="user.nickName" placeholder="设置昵称">
            <template slot="prepend"><i class="fa fa-user-circle-o" aria-hidden="true"></i></template>
          </el-input>
        </div>
        <div class="register-item">
          <el-input v-model="user.userName" placeholder="输入邮箱">
            <template slot="prepend"><i class="fa fa-envelope" aria-hidden="true"></i></template>
          </el-input>
        </div>
        <div class="register-item">
          <el-input v-model="user.password" placeholder="设置密码">
            <template slot="prepend"><i class="fa fa-key" aria-hidden="true"></i></template>
          </el-input>
        </div >
        <div class="register-item">
          <el-input
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 4}"
            placeholder="一句话介绍一下自己"
            v-model="user.description">
          </el-input>
        </div>
        <div style=" margin: 10px auto; text-align: center">
          <el-button type="success" round size="medium" @click="register()">&nbsp;&nbsp;&nbsp; 注&nbsp;&nbsp;册 &nbsp;&nbsp;&nbsp; </el-button>
        </div>
      </div>
      <div class="login" v-if="!flag1">
        <a class="close"  href="javascript:void(0)" @click="close"><i  style="font-size: 25px" class="el-icon-close"></i></a>
        <div style="padding: 10px;text-align: center; margin: 20px">
          <a href="javascript:void(0)" @click="goLogin()"><span style="font-weight: 700; color: #646464;border-bottom: 2px solid #646464;" >登录</span></a>
          <span style="font-weight: 700; color: #969696; margin-right: 20px; margin-left: 20px">|</span>
          <a class="login-register" href="javascript:void(0)" @click="goRegister()"><span style="font-weight: 700; ">注册</span></a>
        </div>
        <div class="register-item">
          <el-input v-model="user.userName" placeholder="输入邮箱">
            <template slot="prepend"><i class="fa fa-envelope" aria-hidden="true"></i></template>
          </el-input>
        </div>
        <div class="register-item">
          <el-input v-model="user.password" placeholder="输入密码">
            <template slot="prepend"><i class="fa fa-key" aria-hidden="true"></i></template>
          </el-input>
        </div >
        <div style=" margin: 10px auto; text-align: center">
          <el-button type="success" round size="medium" @click="login()">&nbsp;&nbsp;&nbsp; 登&nbsp;&nbsp;录 &nbsp;&nbsp;&nbsp; </el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import Qs from 'qs'
  import regex from '../utils/regex'
  export default {
    data () {
      return{
        user:{
          nickName:'',
          userName:'',
          password:'',
          description:'',
        },
        flag1:''
      }
    },

    props:{
      flag:{
        type:Boolean,
        require:false,
        default:false
      },
      handler:{
        type:Function,
        require:true
      }
    },
    mounted:function () {
      this.flag1=this.flag;
    },
    methods:{
      /**
       *切换到登录面板
       */

      goLogin:function(){
        if(this.flag1){
          this.flag1=false;
          this.user.userName='';
          this.user.password='';
          this.user.description='';
        }
      },
      /**
       *切换到注册面板
       */
      goRegister:function(){
        debugger
        if(!this.flag1){
          this.flag1=true;
          this.user.userName='';
          this.user.password='';
          this.user.description='';
        }
      },
      /**
       * 关闭弹框
       */
      close:function () {
        this.handler();
      },

      /**
       * 登陆
       */
      login(){
        let vm=this;
        if(vm.user.userName==""||vm.user.password==""){
          vm.$message({message: "用户名密码不能为空", center: true, type:'warning'});
          return
        }
        this.$axios({
          url:'http://localhost:8080/cmt/api/v1/user/login',
          method:'post',
          data:Qs.stringify(vm.user)
        }).then(function (res) {
          if(res.data.code==1){
            vm.$store.dispatch('login',res.data.data);
            vm.close();
            vm.$message({message: '登陆成功', center: true, type:'success'});
          }else {
            vm.$message({message: `登录失败,${res.data.msg}`, center: true, type:'warning'});
          }
        }).catch(function (err) {
          console.log(err)
          vm.$message({message: '登录失败', center: true, type:'error'});
        })
      },

      /**
       * 注册用户
       */
      register(){
        let vm=this;
        debugger;
        if(!regex.nickName.test(vm.user.nickName)){
          vm.$message({message: '昵称4-8汉字', center: true, type:'warning'});
          return;
        }
        if(!regex.userName.test(vm.user.userName)){
          vm.$message({message: '邮箱格式不对', center: true, type:'warning'});
          return;
        }
        if(!regex.password.test(vm.user.password)){
          vm.$message({message: '密码6-20位数字或字母', center: true, type:'warning'});
          return;
        }
        if(vm.user.description==''){
          vm.$message({message: '简介不能为空', center: true, type:'warning'});
          return;
        }
        this.$axios({
          url:'http://localhost:8080/cmt/api/v1/user/register',
          method:'post',
          data:Qs.stringify(vm.user)
        }).then(function (res) {
          if(res.data.code==1){
            vm.$store.dispatch('login',res.data.data);
            vm.$message({message: '注册成功', center: true, type:'success'});
            vm.close();
          }else {
            vm.$message({message: `注册失败,${res.data.msg}`, center: true, type:'warning'});
          }
        }).catch(function (err) {
          vm.$message({message: '注册失败', center: true, type:'error'});
        })
      }
    }
  }
</script>
