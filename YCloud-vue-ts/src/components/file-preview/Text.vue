<template>
  <n-config-provider :hljs="hljs" class="code-preview">
    <n-code :code="code" :language="language" />
  </n-config-provider>
</template>
  
<script setup>
import { onMounted, ref } from 'vue';
import http from "@/http/XMLHttpRequest"
import hljs from 'highlight.js/lib/core'
import java from 'highlight.js/lib/languages/java'
import javascript from 'highlight.js/lib/languages/javascript'

hljs.registerLanguage('java', java);
hljs.registerLanguage('javascript', javascript);

const props = defineProps({
  resource: Object
});

const code = ref("");
const language = ref("");

onMounted(() => {
  http.raw.post(resource.url).then(({ data }) => {
    // console.log(data);
    code.value = data;
    language.value = "java";
  });
});

const resource = props.resource;


</script>

<style scoped>
  .code-preview {
    width: 100%;
    height: 100%;
  }
</style>