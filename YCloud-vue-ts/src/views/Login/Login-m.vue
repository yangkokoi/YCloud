<template>
  <div>
    <var-app-bar title="返回">
      <template #left>
        <var-button
          round
          text
          color="transparent"
          text-color="#fff"
          @click="goBack"
        >
          <var-icon
            name="chevron-left"
            :size="24"
          />
        </var-button>
      </template>
    </var-app-bar>
    <div id="user-login">
      <var-form
        ref="loginForm"
        style="width: 100%;"
      >
        <var-space
          direction="column"
          :size="[14, 0]"
        >
          <var-input
            v-model="user.username"
            placeholder="请输入用户名"
            :rules="[v => !!v || '用户名不能为空']"
          />
          <var-input
            v-model="user.password"
            type="password"
            placeholder="请输入密码"
            :rules="[v => !!v || '密码不能为空']"
          />
          <var-button
            block
            type="primary"
            @click="login"
          >
            登录
          </var-button>
        </var-space>
      </var-form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { setup, toggle, login } from "./index";
import { useRouter } from "vue-router";

const {
  loginForm,
  guestLogin,
  input,
  user
} = setup;

guestLogin.value = true;
user.password = "xiaoyang";

const goBack = () => {
  useRouter().back();
};

</script>

<style scoped lang="scss">
#user-login {
  height: calc(100% - var(--app-bar-height));
  padding: 0 3vw;
  display: flex;
  align-items: center;
}
</style>
