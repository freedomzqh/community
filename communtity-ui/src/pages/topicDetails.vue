<style scoped="">
  .topic-main{
    width: 650px;
    margin: 0px auto;
    background-color: #fff;
  }
  .topic-main .title{
    width: 100%;
    margin: 30px;
    text-align: center;
  }
  .topic-main .content{
    width: 900px;
    margin: 30px;
  }
  .topic-main .author{
    margin: 0 auto 0 20px;
  }
   .avatar{
    width: 48px;
    height: 48px;
    vertical-align: middle;
    display: inline-block;
  }
  .avatar img {
    width: 100%;
    height: 100%;
    border: 1px solid #ddd;
    border-radius: 50%;
  }
  .info{
    vertical-align: middle;
    display: inline-block;
    margin-left: 8px;
  }
   .info .meta{
     margin-top: 5px;
     font-size: 12px;
     color: #969696;
   }
  .reply{
    margin-bottom: 100px;
  }
  .reply .reply-title{
    padding-bottom: 20px;
    font-size: 16px;
    font-weight: 700;

  }
  .reply .reply-content{
    border-top: 1px solid #f0f0f0;
    padding-top: 20px;
  }
  .reply-text p{
    margin: 10px 0;
    line-height: 1.5;
    font-size: 16px;
    word-break: break-all;
  }
</style>
<template>
  <div>
    <v-header></v-header>
    <div class="topic-main">
      <div class="title">
        <h1>{{topic.title}}</h1>
      </div>
      <div class="author">
        <a class="avatar" href="javascript:void (0)">
          <img :src="topic.avatarUrl" v-if="topic.avatarUrl">
          <img src="../images/headportrait.jpg" v-else>
        </a>
        <div class="info">
        <span class="name"><a href="javascript:void (0)">{{topic.authorName || "铁明"}}</a></span>
        <!-- 文章数据信息 -->
        <div class="meta">
          <!-- 如果文章更新时间大于发布时间，那么使用 tooltip 显示更新时间 -->
          <span class="publish-time">{{topic.createTime}}</span>
          <span class="views-count">阅读 {{topic.visitCount}}</span>
          <span class="comments-count">评论 {{topic.replyCount}}</span>
          <span class="likes-count">喜欢 {{topic.collectCount}}</span>
        </div>
      </div>
        <!-- 如果是当前作者，加入编辑按钮 -->
      </div>
      <div class="ql-container ql-snow" style="border: none;font-size: 16px!important;">
        <div v-html="topic.content" class="ql-editor">
        </div>
      </div>
      <div style="padding-top: 20px;  margin-bottom:20px;border-top: 1px solid #f0f0f0;">
        <el-input
          type="textarea"
          :rows="2"
          placeholder="请输入你的看法"
          v-model="reply.content">
        </el-input>
        <div style="float: right;padding-top: 10px">
          <el-button type="info" @click="insertReply">发表回复</el-button>
        </div>
      </div>
      <div class="reply">
        <div class="reply-title">
          <span>{{replyCount}}条评论</span>
        </div>
        <div class="reply-content" v-for="(item,index) in replyList">
          <div class="author" style="margin-left: 0px">
            <a class="avatar" href="javascript:void (0)">
              <img :src="item.avatarUrl" v-if="item.avatarUrl">
              <img src="../images/headportrait.jpg" v-else>
            </a>
            <div class="info">
              <span class="name"><a href="javascript:void (0)">{{item.authorName || "铁明"}}</a></span>
              <div class="meta">
                <span class="publish-time">{{item.createTime}}</span>
              </div>
            </div>
          </div>
          <div class="reply-text">
            <p>{{item.content}}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import Qs from 'qs'
  import {mapGetters} from 'vuex'
  import header from '../components/header.vue'
  export default {
    data(){
      return{
        topic:'',
        replyList:[],
        reply:{
          content:'',
          authorId:'',
          topicId:'',
        }
      }
    },
    components:{
      'v-header':header
    },
    mounted:function () {
     let topicId=this.$route.query.topicId;
     if(topicId=='' ||topicId==null){
       this.$message({message: "文章不存在", center: true, type:'warning'});
       return;
     }
     this.getTopicById(topicId);
     this.getReplysByTopicId(topicId);
    },
    computed: {
      ...mapGetters({user:'user'}),
      replyCount(){
        return this.replyList.length;
      }
    },
    methods:{
      /**
       * 获取文章详情
       * @param topicId
       */
      getTopicById:function (topicId) {
        let vm=this;
          this.$axios({
            url:"http://localhost:8080/cmt/api/v1/topic/getTopicById",
            method:'get',
            params: {
              topicId: topicId
            }
          })
            .then(function (res) {
              if(res.data.code==1){
                vm.topic=res.data.data;
              }else {
                this.$message({message: res.data.msg, center: true, type:'warning'});
              }
            })
            .catch(function (err) {
              console.log(err)
            })
      },
      /**
       * 获取文章回复
       * @param topicId
       */
      getReplysByTopicId:function (topicId) {
        let vm=this;
        this.$axios({
          url:"http://localhost:8080/cmt/api/v1/reply/getReplysByTopicId",
          method:'get',
          params: {
            topicId: topicId
          }
        })
          .then(function (res) {
            if(res.data.code==1){
              vm.replyList=res.data.data.content;
            }else {
              this.$message({message: res.data.msg, center: true, type:'warning'});
            }
          })
          .catch(function (err) {
            console.log(err)
          })
      },

      /**
       * 保存评论
       */
      insertReply:function () {
        let vm=this;
        if(vm.user.userId==null ||vm.user.userId==''){
          this.$root.$data.$isOpen=true;
        }
        if(vm.reply.content==''){
            return;
        }
        vm.reply.topicId=vm.topic.topicId;
        vm.reply.authorId=vm.user.userId;
        this.$axios({
          url:"http://localhost:8080/cmt/api/v1/reply/insert",
          method:'post',
          data:Qs.stringify(vm.reply)
        })
          .then(function (res) {
            if(res.data.code==1){
              vm.replyList.unshift(res.data.data)
            }else {
              this.$message({message: res.data.msg, center: true, type:'warning'});
            }
          })
          .catch(function (err) {
            console.log(err)
          })
      }
    }
  }
</script>
