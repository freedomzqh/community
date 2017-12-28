<style scoped>
  .main-topic{
    padding-top: 30px;
    width: 66%;
    float: left;
  }
  .main-nav{
    width: 30%;
    padding-top: 30px;
    margin-left: 4%;
    float: left;
  }
  .main-nav .board{
    padding-bottom: 4px;
    min-height: 228px;
  }
  .main-nav .board img{
    width: 100%;
    min-height: 50px;
    margin-bottom: 6px;
    border-radius: 4px;
  }
  .main-nav .recommended-authors {
    margin-bottom: 20px;
    padding-top: 20px;
    font-size: 13px;
  }
  .recommended-authors .title{
    font-size: 14px;
    color: #969696;
  }
  .recommended-authors .list{
    margin: 0 0 20px;
    text-align: left;
    list-style: none;
  }
  .list li{
    margin-top: 20px;
  }
  .list .avatar{
    float: left;
    width: 48px;
    height: 48px;
    margin-right: 10px;
  }
  .list .avatar img{
    width: 100%;
    height: 100%;
    border: 1px solid #ddd;
    border-radius: 50%;
  }
  .list .follow{
    float: right;
    margin-top: 5px;
    padding: 0;
    font-size: 13px;
    color: #42c02e;
  }
  .no-data{
    background-image: url("../images/data.jpg");
    width: 100%;
    height: 650px;
    background-color: #fff;
  }
</style>
<template>
  <div>
    <v-header></v-header>
    <div class="main">
      <div class="main-topic">
        <v-carousel></v-carousel>
        <v-tag></v-tag>
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
      <div class="main-nav">
        <div class="board">
          <a target="_blank" href="javascript:void (0)">
            <img src="../images/nav1.png" alt="Banner s 1">
          </a>
          <a target="_blank" href="javascript:void (0)">
            <img src="../images/nav2.png" alt="Banner s 3"></a>
          <a target="_blank" href="javascript:void (0)">
            <img src="../images/nav3.png" alt="Banner s 4"></a>
          <a target="_blank" href="javascript:void (0)">
            <img src="../images/nav4.png" alt="Banner s 5">
          </a>
          <a target="_blank" href="javascript:void (0)">
            <img src="../images/nav5.png" alt="Banner s 6">
          </a>
        </div>
        <div class="recommended-authors">
            <div class="title">
              <span style="text-align: left">推荐作者</span>
            </div>
          <ul class="list">
            <li v-for="n in 5">
              <a href="javascript:void (0)" target="_blank" class="avatar">
                <img src="../images/nav.jpg"></a>
              <a class="follow" href="javascript:void (0)"><i class="iconfont ic-follow"></i>关注</a>
              <a href="/u/82854a3500fc?utm_source=desktop&amp;utm_medium=index-users" target="_blank" class="name">小黄人</a>
              <p style="color: #969696;">写了194.7k字 · 2k喜欢</p>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import header from '../components/header.vue'
  import tag from '../components/tag.vue'
  import carousel from '../components/carousel.vue'
  import topic from '../components/topic.vue'
  import {mapState} from 'vuex'
  export default {
    name:'home',
    data(){
      return{
        topics:[],
        currentNo:'',
        size:50,
        loading:false,
        moreData:false
      }
    },
    mounted:function () {
      this.fetchData(0)
    },
    components:{
      'v-header':header,
      'v-tag':tag,
      'v-carousel':carousel,
      'v-topic':topic
    },
    methods:{
      fetchData:function (pageNo) {
        let vm=this;
        vm.loading=true;
        this.$axios({
          url:'http://localhost:8080/cmt/api/v1/topic/getTopics',
          method:'get',
          params:{
            pageNumber:pageNo
          }
        }).then(function (res) {
          if(res.data.code==1){
            if(res.data.data.numberOfElements==10){
              vm.moreData=true;
            }else {
              vm.moreData=false;
            }
            vm.topics=vm.topics.concat(res.data.data.content);
            vm.currentNo=pageNo;
          }else {
            vm.$message({message: res.data.msg, center: true, type:'warning'});
          }
          }).catch(function (err) {
          console.log(err)
          })
        vm.loading=false;
      }
    }
  }
</script>

