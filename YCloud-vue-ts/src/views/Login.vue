<template>
  <div id='user-login'>
    <!-- <el-card style="width: 60%;"> -->
    <div id="login-card">
      点我登录
      <n-button type="success" @click="login">
      Success
      </n-button>
    </div>
    <!-- </el-card> -->
  </div>
</template>

<script setup lang="ts">
  import {
    onMounted,
    reactive,
    inject
  } from 'vue'
  import { useRouter, useRoute } from 'vue-router'
  import { main } from '@/store/main'
  import { login as httpLogin } from "@/http/User"

  const $router = useRouter();

  const user = reactive({
    username: "guest",
    password: "123456"
  });

  // export default defineComponent({
  const om = onMounted(() => {
    // this.$loading().close();
    if (useRoute().params.msg) {
    }
  })
  
  const loginRules = {
    username: [{
      required: true,
      message: "请输入账户名称"
    }],
    password: [{
      required: true,
      message: "请输入密码"
    }]
  };
  const event = null;

  const receiveMsg = function(e) {
    if (e.origin === $api.SERVER_BASE) {
      let res = JSON.parse(e.data);
      console.log(res)
      // 如果登录成功
      if (res.code == 200) {
        // 添加Token信息
        main().setToken(res.token);
        $router.push({
          name: "Explore",
          params: {
            // 表示是从登录页面过来的
            login: true
          }
        });
        window.$message({
          title: res.msg,
          type: "success"
        });
      } else {
        window.$message({
          title: res.msg,
          type: "warning"
        });
      }
      // 移除 message 事件
      window.removeEventListener("message", receiveMsg);
    }
  }

  const login = function(url:string) {
    if (typeof url === "string") {
      // 创建一个子窗口用于登录
      let strWindowFeatures =
        "width=600,height=500,top=100,left=100,menubar=yes,location=yes,resizable=yes,scrollbars=true,status=true";
      window.open(url, "_blank", strWindowFeatures);
      // 创建postMessage监听
      window.addEventListener("message", receiveMsg);
    } else {
      httpLogin(user.username, user.password).then(res => {
        $router.push({path: "/explorer/local"});
      });
    }
  }
</script>

<style scoped>
  #user-login {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: #F7FAFC;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  #loginBtn {
    width: 100%;
  }

  #title {
    margin-bottom: 5rem;
    text-align: center;
  }

  #login-card {
    width: 60%;
    background-color: #FFF;
    border-radius: .5rem;
    box-shadow: 0px 1px 5px 0 rgba(0, 0, 0, .1), 0px 2px 2px 0 rgba(0, 0, 0, .06);
  }
</style>
