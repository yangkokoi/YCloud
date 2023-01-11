<template>
  <n-config-provider
    :hljs="hljs"
    class="code-preview"
  >
    <n-scrollbar
      x-scrollable
      style="max-height: calc(100% - 2px)"
    >
      <n-code
        :code="code"
        :language="language"
        :show-line-numbers="true"
      />
    </n-scrollbar>
  </n-config-provider>
</template>

<script setup>
import { onMounted, ref, watch } from "vue";
import http from "@/http/XMLHttpRequest";
import hljs from "highlight.js/lib/core";
import java from "highlight.js/lib/languages/java";
import javascript from "highlight.js/lib/languages/javascript";

hljs.registerLanguage("java", java);
hljs.registerLanguage("javascript", javascript);

const props = defineProps({
  resource: Object,
  url: String
});

const resource = ref(props.resource);
const code = ref("");
const language = ref("");
const lMap = {
  java: "java",
  js: "javascript"
};

watch(() => props.resource, (val) => {
  resource.value = props.resource;
  refreshCode();
});

onMounted(() => {
  refreshCode();
});

const refreshCode = function() {
  http.post(props.url).then(({ data }) => {
    code.value = data;
    language.value = lMap[resource.value.type];
  }).catch(err => {
    code.value = "源资源已被删除";
    console.log(err);
  });
};

</script>

<style scoped>
.code-preview {
  width: 100%;
  height: 100%;
}
</style>
