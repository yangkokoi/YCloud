<template>
  <div id="user-login">
    <!-- <el-card style="width: 60%;"> -->
    <div id="login-card">
      <div style="min-width: 300px; padding: 20px 15px 0px 35px;position: relative;">
        <div id="title">
          <h1>网盘登录</h1>
        </div>

        <n-form
          ref="loginForm"
          :model="user"
          :show-label="false"
          style="position: relative;"
        >
          <Transition
            name="slide"
            class="input-box"
          >
            <div v-if="!guestLogin">
              <n-form-item path="username">
                <n-input
                  ref="input"
                  v-model:value="user.username"
                  :autofocus="true"
                >
                  <template #prefix>
                    账号：
                  </template>
                </n-input>
              </n-form-item>
              <n-form-item path="password">
                <n-input
                  v-model:value="user.password"
                  type="password"
                  @keyup.enter="login"
                >
                  <template #prefix>
                    密码：
                  </template>
                </n-input>
              </n-form-item>
            </div>
            <div v-else>
              <!-- <span style="font-size: 12px;margin: 20px 0;display: block;" @click="guestLogin = false">&lt; 用户登录</span> -->
              <n-form-item path="password">
                <n-input
                  ref="input"
                  v-model:value="user.password"
                  type="password"
                  placeholder="请输入访问密码"
                  @keyup.enter="login"
                />
              </n-form-item>
            </div>
          </Transition>
          <n-form-item>
            <n-button
              id="loginBtn"
              type="primary"
              @click="login"
            >
              登录
            </n-button>
          </n-form-item>
          <!-- <el-form-item>
                  <el-button type="primary" id='loginBtn' @click="login($api.OAUTH_QQ)" >QQ登录</el-button>
                </el-form-item> -->
        </n-form>

        <div class="btn-group">
          <n-button
            type="primary"
            size="tiny"
            dashed
            @click="$router.push('/')"
          >
            <template #icon>
              <n-icon>
                <i class="fa-solid fa-angles-left" />
              </n-icon>
            </template>
            首页
          </n-button>
          <n-button
            type="primary"
            size="tiny"
            dashed
            @click="toggle"
          >
            <template #icon>
              <n-icon>
                <i class="fa-solid fa-seedling" />
              </n-icon>
            </template>
            {{ guestLogin ? "用户登录" : "游客登录" }}
          </n-button>
        </div>
      </div>
      <div style="flex: 1;height: 100%;box-sizing: border-box;padding: 10px;">
        <img
          style="width: 100%; height: 100%;background-color: #EBF4FF;"
          :src="bg"
          fit="contain"
        >
      </div>
    </div>
    <!-- </el-card> -->
  </div>
</template>

<script setup lang="ts">
import { setup, toggle, login } from "./index";
import bg from "@/assets/login-bg.svg";

const {
  loginForm,
  guestLogin,
  input,
  user
} = setup;

// import { nextTick, onMounted, reactive, ref } from "vue";
// import { useRouter } from "vue-router";
// // import { main } from "@/store/main";
// import { login as httpLogin } from "@/http/User";
// import { AngleLeft, Seedling } from "@vicons/fa";
// import bg from "@/assets/login-bg.svg";
// import { FormInst, useMessage } from "naive-ui";

// const $router = useRouter();
// const $message = useMessage();

// const loginForm = ref<FormInst | null>(null);
// const guestLogin = ref(false);
// const input = ref(null);

// const user = reactive({
//   username: "",
//   password: ""
// });

// // export default defineComponent({
// onMounted(() => {
//   // this.$loading().close();

//   // if (useRoute().params.msg) {
//   // }
// });

// const toggle = function() {
//   guestLogin.value = !guestLogin.value;
//   nextTick(() => {
//     user.username = "";
//     user.password = "";
//     if (input.value == null) return;
//     // @ts-ignore
//     input.value.focus();
//   });
// };

// // const receiveMsg = function (e) {
// //   if (e.origin === $api.SERVER_BASE) {
// //     let res = JSON.parse(e.data);
// //     console.log(res)
// //     // 如果登录成功
// //     if (res.code == 200) {
// //       // 添加Token信息
// //       main().setToken(res.token);
// //       $router.push({
// //         name: "Explore",
// //         params: {
// //           // 表示是从登录页面过来的
// //           login: true
// //         }
// //       });
// //       window.$message({
// //         title: res.msg,
// //         type: "success"
// //       });
// //     } else {
// //       window.$message({
// //         title: res.msg,
// //         type: "warning"
// //       });
// //     }
// //     // 移除 message 事件
// //     window.removeEventListener("message", receiveMsg);
// //   }
// // }

// function checkPwd() {
//   if (!guestLogin.value) {
//     if (user.username.trim() === "") {
//       $message.warning("用户名不可以为空");
//       user.username = "";
//       return false;
//     } else if (user.username.length > 10) {
//       $message.warning("用户名长度在10位数以内");
//       user.username = "";
//       return false;
//     }
//   }
//   if (user.password.trim() === "") {
//     $message.warning("密码不可以为空");
//     user.password = "";
//     return false;
//   } else if (user.password.length > 18) {
//     $message.warning("密码长度在18位数以内");
//     user.password = "";
//     return false;
//   }
//   return true;
// }

// const login = function(url: string) {
//   if (typeof url === "string") {
//     // 创建一个子窗口用于登录
//     let strWindowFeatures =
//       "width=600,height=500,top=100,left=100,menubar=yes,location=yes,resizable=yes,scrollbars=true,status=true";
//     window.open(url, "_blank", strWindowFeatures);
//     // 创建postMessage监听
//     // window.addEventListener("message", receiveMsg);
//   } else if (guestLogin.value === true) {
//     if (!checkPwd()) return;
//     httpLogin("guest", user.password).then(res => {
//       // showPwd.value = false;
//       $router.push({ path: "/explorer/local" });
//     });
//   } else {
//     if (!checkPwd()) return;
//     httpLogin(user.username, user.password).then(res => {
//       $router.push({ path: "/explorer/local" });
//     });
//   }
// };
</script>

<style scoped lang="scss">
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
  margin-bottom: 3rem;
  text-align: center;
}

#login-card {
  height: 400px;
  background-color: #FFF;
  border-radius: .5rem;
  box-shadow: 0px 1px 5px 0 rgba(0, 0, 0, .1), 0px 2px 2px 0 rgba(0, 0, 0, .06);
  display: flex;
}

.btn-group {
  position: absolute;
  left: 10px;
  bottom: 10px;

  button {
    margin-right: 20px;
  }
}

.input-box {
  height: 116px;
  display: grid;
  align-items: center;
}

.slide-enter-active {
  animation: slide 1s;
}

// .slide-leave-active {
//   // transition: opacity 0.7s ease;
// }

.slide-enter-from,
.slide-leave-to {
  position: absolute;
  opacity: 0;
}

@keyframes slide {
  0% {
    transform: translate(-100px, 0);
  }

  50% {
    transform: translate(25px, 0);
  }

  100% {
    transform: translate(0px, 0);
  }
}
</style>
