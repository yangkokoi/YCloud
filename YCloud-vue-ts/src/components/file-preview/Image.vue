<template>
  <n-image
    class="preview-img"
    :alt="props.resource.name"
    :src="props.resource.url"
    object-fit="contain"
  />
</template>

<script setup>

const props = defineProps({
  resource: Object
});
</script>

<style lang="scss" scoped>
.preview-img {
  max-width: 100%;
  max-height: 100%;

  :deep(img) {
    max-width: 100%;
    max-height: 100%;
  }
}
</style>