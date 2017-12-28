<style scoped>
  .recommend-collection{
    margin-top: 30px;
  }
  .recommend-collection .collection {
    display: inline-block;
    margin: 0 18px 18px 0;
    min-height: 32px;
    background-color: #f7f7f7;
    border: 1px solid #dcdcdc;
    border-radius: 4px;
    vertical-align: top;
    overflow: hidden;
  }
  .collection .name{
    display: inline-block;
    padding: 0 11px 0 6px;
    font-size: 14px;
  }
  .recommend-collection .collection img {
    width: 32px;
    height: 32px;
    vertical-align: middle;
  }
</style>
<template>
  <div class="recommend-collection">
      <router-link :to="{ path: 'subject', query: {subjectId:item.subjectId }}" target="_blank" class="collection" v-for="item in subjects" :key="item.subjectId">
        <img :src="item.avatarUrl"><div class="name">{{item.name}}</div>
      </router-link>
  </div>
</template>

<script>
  import { mapGetters } from 'vuex'
  export default {
    name:'tag',
    data(){
      return{
          demo:''
      }
    },
    computed: { ...mapGetters({subjects:'subjects'})},
    mounted:function () {
    }
  }
</script>

