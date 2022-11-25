<template>
  <div>
    <n-empty description="当前文件类型暂不支持预览">
      <template #icon>
        <n-icon>
          <Wind />
        </n-icon>
      </template>
    </n-empty>
  </div>
</template>

<script setup>
import { Wind } from "@vicons/fa";
const props = defineProps({
  resource: Object
});
</script>
