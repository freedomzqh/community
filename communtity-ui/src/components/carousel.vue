<style scoped>
  .carousel-wrap {
    position: relative;
    display: inline-block;
    width: 100%;
    overflow: hidden;
    padding: 0;
  }
  img {
    width: 100%;
    height: 100%;
    border-radius: 4px;
    background-color: hsla(0,0%,71%,.2)
  }
</style>
<template>
  <div class="carousel-wrap">
    <el-carousel :interval="4000">
      <el-carousel-item v-for="item in slideList" :key="item">
        <img :src="item.image">
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<script>
  export default {
    name:'carousel',
    data(){
      return{
        slideList: [
          {
            "image": "http://localhost:8080/cmt/images/aed5a6e5-923a-4f4d-93f2-541bd95dac40.jpg"
          },
          {
            "image": "http://localhost:8080/cmt/images/5729ca48-ceee-4373-86a1-4f5a5702178a.jpg"
          },
          {
            "clickUrl": "#",
            "desc": "rsdh",
            "image": "http://localhost:8080/cmt/images/efa29cf2-1532-4207-90d5-bffdd4da85a9.jpg"
          }
        ],
      }
    },
    created() {

    },
    methods: {}
  }
</script>

