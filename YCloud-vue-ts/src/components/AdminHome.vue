<template>
  <!-- 所有组件和页面的根 -->
  <el-container>
    <!-- <i  v-loading.fullscreen.lock="this.$store.state.user == null"></i> -->
    <el-container id='root-container' >
      <!-- 侧边栏 -->
      <el-aside>
         <left-nav/>
      </el-aside>
      
      <!-- 右边内容 -->
      <el-container>
        <!-- 头部 -->
        <el-header>
          <top-bar/>
        </el-header>
        <!-- 主体 -->
        <el-main>
          <right-tabs/>
        </el-main>
        
      </el-container>
    </el-container>
  </el-container>
</template>

<script>
export default {
  data() {
    return {
      isLoading: this.$store.state.user == null,
    }
  },
  methods: {

  },
  components: {
    
  }
};
</script>

<style scoped>
.el-container {
  height: 100%;
  width: 100%;
}
.el-header {
  /* background-color: #2b3749; */
}
.el-aside {
  /* height: 100%; */
  width: auto !important;
  /* min-width: 200px; */
}
.el-main {
  /* height: 100%; */
  padding: 0;
  width: 100%;
  background: #F3F5F8;
}
</style>
