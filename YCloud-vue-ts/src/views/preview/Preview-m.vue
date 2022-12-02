<template>
  <div>
    <var-app-bar>
      <template #left>
        <var-button
          round
          text
          color="transparent"
          text-color="#fff"
          style="margin-right: 10px;"
          @click="$router.back()"
        >
          <var-icon
            name="chevron-left"
            :size="24"
          />
        </var-button>
      </template>
      <template #right>
        <var-button
          round
          text
          color="transparent"
          text-color="#ffffff"
        >
          <y-icon name="fa-ellipsis" />
        </var-button>
      </template>
    </var-app-bar>
    <div>
      <Preview :resource="(resource as any)" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import Preview from "@/components/file-preview/FilePreview.vue";
import store from "@/store/temp";

// const $route = useRoute();
const $router = useRouter();

const resource = store().$state.resource;
console.log(resource);

onMounted(() => {

});

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
