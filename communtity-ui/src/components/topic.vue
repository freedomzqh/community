<style scoped>

</style>
<template>
  <div>
    <a class="wrap-img" href="javascript:void (0)" target="_blank">
      <img class="img-blur-done" :src="topic.imgUrl"alt="120">
    </a>
    <div class="content" style="padding-right: 160px">
      <div class="author">
        <a class="avatar">
          <img :src="topic.avatarUrl" v-if="topic.avatarUrl">
          <img src="../images/headportrait.jpg" v-else>
        </a>
        <div class="info">
          <a class="nickname" target="_blank" href="javascript:void (0)">{{topic.authorName || "铁明"}}</a>
          <span style="color: #969696">{{topic.createTime}}</span>
        </div>
      </div>
      <router-link :to="{ path: 'topicDetails', query: { topicId: topic.topicId }}" target="_blank" class="title">{{topic.title}}</router-link>
      <p class="abstract">{{topic.summary}}...</p>
      <div class="meta">
        <a class="collection-tag" target="_blank" href="javascript:void(0);">{{getTagName}}</a>
        <a target="_blank" href="javascript:void(0);"><i class="fa fa-eye" aria-hidden="true"></i> {{topic.visitCount}}</a>
        <a target="_blank" href="javascript:void(0);"><i class="fa fa-commenting" aria-hidden="true"></i>  {{topic.replyCount}}</a>
        <span style="color: #b4b4b4; "><i class="fa fa-heart-o" aria-hidden="true"></i> {{topic.collectCount}}</span>
      </div>
    </div>
  </div>
</template>

<script>
  import {mapState} from 'vuex'
  import { mapGetters } from 'vuex'
  export default {
    name:'topic',
    data(){
      return{
      }
    },
    computed: {
      ...mapState({user:state=>state.user}),
      ...mapGetters({subjects:'subjects'}),
      getTagName:function () {
        let name='其他';
        let vm =this;
        if(vm.subjects!=null){
          console.log(vm.subject)
          vm.subjects.forEach(function (item) {
            if(item.subjectId==vm.topic.subjectId){
              name=item.name;
              return;
            }
          })
          return name;
        }
      }
    },
    props:{
      topic:{
        type:Object,
        require:true
      }
    },
    methods:{

    }

  }
</script>

