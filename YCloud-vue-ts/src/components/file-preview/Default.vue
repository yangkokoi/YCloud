<template>
  <div>
    <span>{{resource.name}}</span>
    默认预览模式
  </div>
</template>

<script setup>
const props = defineProps({
  resource: Object
});

const resource = props.resource;
</script>