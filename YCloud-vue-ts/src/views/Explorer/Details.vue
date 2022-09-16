<template>
  <div class="file-details">
    <!-- 文件预览 -->
    <Preview :resource="props.resource"></Preview>
    <div style="margin: 10px;text-align: center;">
      <n-tag :bordered="false" type="success">
        {{resource.name}}
      </n-tag>
    </div>

    <n-descriptions label-placement="left" :column="1" label-style="font-weight: 700">
      <n-descriptions-item label="文件ID">
        {{resource.uuid}}
      </n-descriptions-item>
      <n-descriptions-item label="文件大小">
        {{resource.size}}
      </n-descriptions-item>
      <n-descriptions-item label="午餐">
        苹果
      </n-descriptions-item>
      <n-descriptions-item label="更新日期">
        {{new Date(resource.updateTime).format()}}
      </n-descriptions-item>
      <n-descriptions-item label="创建日期">
        {{new Date(resource.createTime).format()}}
      </n-descriptions-item>
    </n-descriptions>
  </div>
</template>

<script setup>
import Preview from "@/components/file-preview/Index.vue"
const props = defineProps({
  resource: Object
});
</script>

<style scoped>
.file-details {
  width: 100%;
}

</style>