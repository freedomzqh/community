<style scoped>
  .container-subject{
    width: 960px;
    padding-top: 30px;
    margin: 0px auto;
  }
  .subject-main{
    width: 66%;
    float: left;
  }
  .subject-nav{
    width: 30%;
    margin-left: 4%;
    float: left;
  }
  .subject-main .main-top{
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
    padding: 10px 0 0 100px;

  }
  .main-top .title .name{
    display: inline;
    font-size: 21px;
    font-weight: 700;
    vertical-align: middle;
  }
  .main-top .info{
    margin-top: 10px;
    padding-left: 100px;
    font-size: 14px;
    color: #969696;
  }
  .subject-main .main-menu{
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
  .subject-nav .title{
    margin-bottom: 10px;
    font-size: 14px;
    color: #969696;
  }
  .subject-nav .description{
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
    <div class="container-subject">
      <div class="subject-main">
        <div class="main-top">
          <a class="avatar" href="javascript:void (0)">
            <img :src="subject.avatarUrl" alt="240">
          </a>
          <!--<a class="btn btn-success follow"><i class="iconfont ic-follow"></i><span>关注</span></a>-->
          <!--<a class="btn btn-hollow" href="/sign_in?utm_medium=not-signed-in-contribute-button&amp;utm_source=desktop">投稿</a>-->
          <div class="title">
            <a class="name" href="/c/8c92f845cd4d">{{subject.name}}</a>
          </div>
          <div class="info">
            收录了{{total}}篇文章
          </div>
        </div>
        <ul class="main-menu">
            <li :class="{active:orderFiled=='createTime'}" @click="onMenu('createTime')"><a href="javascript:void (0)"><i class="fa fa-file-text " aria-hidden="true"></i> 最新收录</a></li>
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
        <div style="color: #969696 ;text-align: center" v-show="flag">
          <i class="el-icon-tickets"></i>
          暂无数据
        </div>
        <a href="javascript:void (0);" class="load-more" style="display: block;" @click="fetchData(currentNo+1)"v-show="!loading && moreData">阅读更多</a>
      </div>
      <div class="subject-nav">
        <p class="title">专题公告</p>
        <div class="description">
          <p>
            {{subject.description}}
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import header from '../components/header.vue'
  import topic from '../components/topic.vue'
  import { mapGetters } from 'vuex'
  export default {
    name:'subject',
    data(){
      return{
        subject:'',
        topics:[],
        currentNo:'',
        size:50,
        loading:false,
        moreData:false,
        orderFiled:'createTime',
        total:'',
        flag:false
      }
    },
    created:function () {

    },

    computed: { ...mapGetters({subjects:'subjects'})},
    mounted:function () {
      let vm=this;
      let subjectId=vm.$route.query.subjectId;
      console.log(vm.subject);
      vm.subjects.forEach(function (item) {
        if(item.subjectId==subjectId){
          vm.subject=item;
          return
        }
      })
      vm.fetchData(0)
    },
    components:{
      'v-header':header,
      'v-topic':topic
    },
    methods:{
      /**
       * 请求数据
       * @param pageNo
       */
      fetchData:function (pageNo) {
        let vm=this;
        vm.loading=true;
        debugger;
        if(pageNo==0){
          vm.topics=[];
        }
        this.$axios({
          url:'http://localhost:8080/cmt/api/v1/topic/getTopicsBySubjectId',
          method:'get',
          params:{
            pageNumber:pageNo,
            subjectId:vm.subject.subjectId,
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
            if(res.data.data.totalElements==0){
              vm.flag=true;
            }
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

