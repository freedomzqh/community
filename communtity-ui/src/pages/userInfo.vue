<style scoped>
  .container-userInfo{
    width: 960px;
    padding-top: 30px;
    margin: 0px auto;
  }
  .userInfo-main{
    width: 66%;
    float: left;
  }
  .userInfo-nav{
    width: 30%;
    margin-left: 4%;
    float: left;
  }
  .userInfo-main .main-top{
    margin-bottom: 35px;
  }
  .main-top .avatar{
    float: left;
    width: 80px;
    height: 80px;
  }
  .main-top .avatar img{
    width: 100%;
    height: 100%;
    border: 1px solid #ddd;
    border-radius: 10%;
  }
  .main-top .title{
    padding: 10px 0 0 180px;

  }
  .main-top .title .name{
    display: inline;
    font-size: 21px;
    font-weight: 700;
    vertical-align: middle;
  }
  .main-top .info{
    margin-top: 10px;
    padding-left: 180px;
    font-size: 14px;
    color: #969696;
  }
  .userInfo-main .main-menu{
    margin-bottom: 20px;
    border-bottom: 1px solid #f0f0f0;
    list-style: none;
  }
  .main-menu li{
    position: relative;
    display: inline-block;
    padding: 8px 0;
    margin-bottom: -1px;
  }
  .main-menu li a{
    padding: 13px 20px;
    font-size: 15px;
    font-weight: 700;
    color: #969696;
    line-height: 32px;
  }
  .main-menu .active , .main-menu a:hover {
    color: #646464;
    border-bottom: 2px solid #646464;
  }
  .userInfo-nav .title{
    margin-bottom: 10px;
    font-size: 14px;
    color: #969696;
  }
  .userInfo-nav .description{
    position: relative;
    width: 100%;
    margin-bottom: 20px;
    padding: 0 0 16px;
    text-align: left;
    font-size: 14px;
    border-bottom: 1px solid #f0f0f0;
    clear: both;
    word-break: break-all;
  }
</style>
<template>
  <div>
    <v-header></v-header>
    <div class="container-userInfo">
      <div class="userInfo-main">
        <div class="main-top">
          <div class="avatar">
            <VueImgInputer v-model="file" theme="light" size="small" :imgSrc="user.avatarUrl" placeholder="上传头像" :onChange="uploadImg"></VueImgInputer>
          </div>
          <!--<a class="btn btn-success follow"><i class="iconfont ic-follow"></i><span>关注</span></a>-->
          <!--<a class="btn btn-hollow" href="/sign_in?utm_medium=not-signed-in-contribute-button&amp;utm_source=desktop">投稿</a>-->
          <div class="title">
            <a class="name" href="javascript:void (0)">{{user.nickName}}</a>
          </div>
          <div class="info">
            收录了{{total}}篇文章
          </div>
        </div>
        <ul class="main-menu">
          <li :class="{active:orderFiled=='createTime'}" @click="onMenu('createTime')"><a href="javascript:void (0)"><i class="fa fa-file-text " aria-hidden="true"></i> 我的文章</a></li>
          <li :class="{active:orderFiled=='updateTime'}" @click="onMenu('updateTime')"><a href="javascript:void (0)"><i class="fa fa-commenting" aria-hidden="true"></i> 最新评论</a></li>
          <li :class="{active:orderFiled=='visitCount'}" @click="onMenu('visitCount')"><a href="javascript:void (0)"><i class="fa fa-fire" aria-hidden="true"></i> 热门</a></li>
        </ul>
        <div class="list-container">
          <ul class="note-list">
            <li v-for="(item,index) in topics">
              <v-topic :topic="item"></v-topic>
            </li>
          </ul>
        </div>
        <div style="margin: 0 auto;width: 50px" v-show="loading">
          <v-loading :size="size"></v-loading>
        </div>
        <a href="javascript:void (0);" class="load-more" style="display: block;" @click="fetchData(currentNo+1)"v-show="!loading && moreData">阅读更多</a>
      </div>
      <div class="userInfo-nav">
        <p class="title">个人简介</p>
        <div class="description">
          <p>
            {{user.description}}
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import { mapGetters } from 'vuex'
  import VueImgInputer from 'vue-img-inputer'
  export default {
    name:'userInfo',
    data(){
      return{
        userInfo:'',
        topics:[],
        currentNo:'',
        size:50,
        loading:false,
        moreData:false,
        orderFiled:'createTime',
        total:'',
        file:''
      }
    },
    components:{
      VueImgInputer
    },
    computed: { ...mapGetters({user:'user'})},
    mounted:function () {
      this.fetchData(0)
    },
    methods:{
      /**
       * 请求数据
       * @param pageNo
       */
      fetchData:function (pageNo) {
        let vm=this;
        vm.loading=true;
        if(pageNo==0){
          vm.topics=[];
        }
        this.$axios({
          url:'http://localhost:8080/cmt/api/v1/topic/getTopicsByUserId',
          method:'get',
          params:{
            pageNumber:pageNo,
            userId:vm.user.userId,
            orderFiled:vm.orderFiled
          }
        }).then(function (res) {
          vm.loading=false;
          if(res.data.code==1){
            if(res.data.data.numberOfElements==10){
              vm.moreData=true;
            }else {
              vm.moreData=false;
            }
            vm.total=res.data.data.totalElements;
            vm.topics=vm.topics.concat(res.data.data.content);
            vm.currentNo=pageNo;
          }else {
            vm.$message({message: res.data.msg, center: true, type:'warning'});
          }
        }).catch(function (err) {
          vm.loading=false;
          console.log(err)
        })

      },

      /**
       * 更换图片
       * @param
       */
      uploadImg:function () {
        let formData = new FormData();
        formData.append('img', this.file);
        formData.append('userId',this.user.userId)
        this.$axios({
          url: 'http://localhost:8080/cmt/api/v1/user/updateImg',
          method: 'POST',
          data: formData
        })
          .then((res) => {
            if(res.data.code==1){
              this.$store.dispatch('login',res.data.data);
              this.$message({message: '上传成功', center: true, type:'success'});
            }else {
              this.$message({message: res.data.msg, center: true, type:'warning'});
            }
          })
          .catch((err) => {
            console.log(err);
          })
      },
      /**
       * 切换标签
       */
      onMenu:function (orderFiled) {
        this.orderFiled=orderFiled;
        debugger;
        this.fetchData(0)
      }
    }
  }
</script>

