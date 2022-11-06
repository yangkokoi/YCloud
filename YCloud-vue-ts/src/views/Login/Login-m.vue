<template>
  <div id="user-login">
    <div class="back">
      <n-button
        type="primary"
        size="tiny"
        dashed
        @click="$router.push('/')"
      >
        <template #icon>
          <n-icon>
            <AngleLeft />
          </n-icon>
        </template>
        首页
      </n-button>
    </div>
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
            name="flip"
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
            @click="toggle"
          >
            <template #icon>
              <n-icon>
                <Seedling />
              </n-icon>
            </template>
            {{ guestLogin ? "用户登录" : "游客登录" }}
          </n-button>
        </div>
      </div>
    </div>
    <!-- </el-card> -->
  </div>
</template>

<script setup lang="ts">
import { setup, toggle, login } from "./index";
import { AngleLeft, Seedling } from "@vicons/fa";

const {
  loginForm,
  guestLogin,
  input,
  user
} = setup;

</script>

<style scoped lang="scss">
#user-login {
  height: 100%;
  display: flex;
  align-items: center;
  background-color: #FFF;
}

.back {
  position: fixed;
  left: 10px;
  top: 10px;
}

#loginBtn {
  width: 100%;
}

#title {
  margin-bottom: 3rem;
  text-align: center;
}

#login-card {
  width: 100%;
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

.flip-enter-active {
  animation: flip 0.5s ease-out reverse;
}

// .flip-leave-active {
//   //transition: opacity 0.3s;
//   //animation: flip 0.5s ease-out;
// }

.flip-enter-from,
.flip-leave-to {
  position: absolute;
}

@keyframes flip {
  0% {
    transform: rotateY(0deg);
  }

  50% {
    transform: rotateY(45deg);
  }

  100% {
    transform: rotateY(90deg);
  }
}
</style>
