<template>
  <div
    id="editor"
    :style="{
      height: props.height
    }"
  />
</template>

<script setup lang="ts">
import { nextTick, onMounted, defineProps } from "vue";

const props = defineProps({
  height: {
    type: String,
    required: true
  },
  content: {
    type: String,
    required: false,
    default: ""
  }
});

let editor = null;
onMounted(() => {
  nextTick(() => {
    init();
  });
});

function init() {
  editor = window.ace.edit("editor");
  editor.setValue(props.content);
}

function getValue(): string {
  return editor.getValue();
}

defineExpose({
  getValue
});
</script>

<style lang="scss" scoped>
#editor {
  width: 100%;
  margin-top: 30px;
}
</style>
