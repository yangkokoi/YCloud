<template>
  <div class="header">
    <var-app-bar :title="props.title">
      <template #left>
        <slot name="left">
          <var-image
            width="36px"
            height="36px"
            fit="cover"
            radius="50%"
            style="margin-right: 10px;"
            src="http://q2.qlogo.cn/headimg_dl?dst_uin=1282381264&spec=3"
            @click="popup.visible = true"
          />
        </slot>
      </template>
      <template #right>
        <var-button
          round
          text
          color="transparent"
          text-color="#ffffff"
          @click="offsetY = true"
        >
          <var-icon
            name="plus"
            :size="24"
          />
        </var-button>
      </template>
    </var-app-bar>
    <form action="">
      <var-input
        v-model="search.value"
        v-search
        clearable
        :hint="false"
        :line="false"
        text-color="#FFF"
        focus-color="#FFF"
        blur-color="#AECBD9"
        placeholder="文件，文件夹"
        class="search-input"
        :rules="[v => v.length < 10 || '搜索关键词应小于10']"
      >
        <template #prepend-icon>
          <var-icon
            name="magnify"
            style="margin-right: 5px;"
          />
        </template>
      </var-input>
    </form>

    <var-popup
      v-model:show="popup.visible"
      position="left"
    >
      <div class="popup-block">
        <var-button
          block
          type="primary"
          @click="logout"
        >
          退出登录
        </var-button>
      </div>
    </var-popup>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from "vue";
import { useRouter } from "vue-router";
import { user } from "@/store/user";

const props = defineProps({
  title: {
    type: String,
    required: false,
    default: ""
  }
});

const offsetY = ref(false);
const search = reactive({
  value: ""
});

const popup = reactive({
  visible: false
});

const $router = useRouter();

const logout = function() {
  user().logout();
  $router.push({
    name: "Login",
    params: {
      // msg: data.msg
    }
  });
};

const mineHandler = function() {
  console.log(1);
};

</script>

<style>
.header {
  background-color: #3A7AFE;
  padding: 0 10px 5px 10px;
}

.search-input {
  padding: 0 10px;
  background-color: #275DA3;
  border-radius: 7px;
}

.var-elevation--3 {
  box-shadow: none;
}

.popup-block {
  padding: 20px 24px;
  width: 70vw;
}
</style>
