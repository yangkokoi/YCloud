<template>
  <div>
    <router-view style="margin-bottom: calc(var(--bottom-navigation-height) + 10px);" />
    <var-bottom-navigation
      v-model:active="active"
      fixed
      safe-area
      class="navigation-m"
      @change="changeNav"
    >
      <template #fab>
        <var-uploader
          v-model="files"
          hide-list
          @after-read="upload"
        >
          <var-icon name="upload" />
        </var-uploader>
      </template>
      <var-bottom-navigation-item
        name="Home"
        label="首页"
        icon="home"
      />
      <var-bottom-navigation-item
        name="Explorer"
        label="文件"
      >
        <template #icon>
          <y-icon name="fa-folder" />
        </template>
      </var-bottom-navigation-item>
      <var-bottom-navigation-item
        name="MineShare"
        label="分享"
      >
        <template #icon>
          <y-icon name="fa-share-alt-square" />
        </template>
      </var-bottom-navigation-item>
      <var-bottom-navigation-item
        name="Mine"
        label="我的"
        icon="account-circle"
      />
    </var-bottom-navigation>
  </div>
</template>

<script setup lang="ts">
import { provide, ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import axios from "@/http/XMLHttpRequest";
import type { VarFile } from "@varlet/ui";

const $route = useRoute();
const $router = useRouter();

const bucketName = ref($route.params.name as string);
const path = ref("");

const active = ref<number | string>($route.name as string);
const files = ref<[]>([]);

const refresh = ref<Function>(() => {
  console.log("refresh");
});

const upload = function(file: VarFile) {
  const formData = new FormData();
  formData.append("bucketName", bucketName.value);
  formData.append("path", path.value);
  formData.append("files", file.file as File);
  axios.upload("/resource/upload", formData)
    .then(() => {
      window.$message.success("上传成功");
      refresh.value();
    }).catch(() => {
      files.value = [];
    });
};

const changeNav = function(active: number | string) {
  if (typeof active === "string") {
    $router.push({ name: active });
  }
};

provide("setRefresh", (fn: Function) => {
  refresh.value = fn;
});
provide("setUploadPath", (val: string) => {
  path.value = val;
});
</script>

<style lang="scss">
</style>
