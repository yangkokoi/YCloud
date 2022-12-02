<template>
  <div>
    <n-empty description="当前文件类型暂不支持预览">
      <template #icon>
        <y-icon name="user" />
      </template>
    </n-empty>
  </div>
</template>

<script setup>
const props = defineProps({
  resource: Object
});
</script>
