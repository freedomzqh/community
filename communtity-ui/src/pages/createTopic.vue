<style scoped="">
  .write-main{
    width: 792px;
    margin: 0px auto;
    padding: 0 40px 0 40px;
    background-color: #fff;
  }
  .write-main .content{
   margin: 30px;
  }
  .write-main .item{
    margin-top: 20px;
    margin-bottom: 20px;
    text-align: center;
  }
</style>
<template>
  <div>
    <v-header></v-header>
    <div class="write-main">
      <div class="content">
        <div class="item">
          <h2>开始你的创作吧！</h2>
        </div>
        <div class="item">
          <el-input placeholder="请输入内容" v-model="topic.title">
            <template slot="prepend">文章标题</template>
          </el-input>
        </div>
        <div style="margin-top: 20px;margin-bottom: 20px;">
          <el-select v-model="topic.subjectId" placeholder="请选择文章标签">
            <el-option
              v-for="item in subjects"
              :key="item.subjectId"
              :label="item.name"
              :value="item.subjectId">
            </el-option>
          </el-select>
          <div style="float: right">
            <el-button @click="submit()">发表</el-button>
          </div>
        </div>
        <div>
          <vue-editor v-model="topic.content" useCustomImageHandler @imageAdded="handleImageAdded" placeholder="开始吧"></vue-editor>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
  import Qs from 'qs'
  import {mapGetters} from 'vuex'
  import vueEditor from '../components/vueEditor.vue'
  export default {
    data(){
      return{
        topic:{
          authorId:"",
          title:'',
          content:'',
          subjectId:'',
        },
      }
    },
    components:{
      vueEditor
    },
    computed: {  ...mapGetters({user:'user',subjects:'subjects'})},
    methods:{
      /**
       * 图片上传
       */
      handleImageAdded: function(file, Editor, cursorLocation) {
        let formData = new FormData();
        formData.append('file', file)
        this.$axios({
          url: 'http://localhost:8080/cmt/api/v1/img/upload',
          method: 'POST',
          data: formData
        })
          .then((res) => {
            if(res.data.code==1){
              let url = res.data.data;
              Editor.insertEmbed(cursorLocation, 'image', url);
            }else {
              this.$message({message: res.data.msg, center: true, type:'warning'});
            }
          })
          .catch((err) => {
            console.log(err);
          })
      },

      /**
       * 保存文章
       */
      submit:function () {
        let vm=this;
        if(vm.topic.title==""){
          vm.$message({message: "请填写文章标题", center: true, type:'warning'});
          return;
        }
        if(vm.topic.tabId==""){
          vm.$message({message: "请选择文章标签", center: true, type:'warning'});
          return;
        }
        if(vm.topic.content==""){
          vm.$message({message: "请输入文章内容", center: true, type:'warning'});
          return;
        }
        vm.topic.authorId=vm.user.userId;
        this.$axios({
          url: 'http://localhost:8080/cmt/api/v1/topic/saveTopic',
          method: 'POST',
          data: Qs.stringify(vm.topic)
        }).then((res) => {
            if(res.data.code==1){
              vm.$message({message: "发表", center: true, type:'success'})
              vm.$router.push({path:'topicDetails',query:{topicId:res.data.data}})
            }else {
              this.$message({message: res.data.msg, center: true, type:'warning'});
            }
          })
          .catch((err) => {
            console.log(err);
          })

      }
    }
  }
</script>
