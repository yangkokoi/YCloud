<template>
  <n-image
    class="preview-img"
    :alt="props.resource.name"
    :src="props.url"
    object-fit="contain"
  />
</template>

<script setup>
const props = defineProps({
  resource: Object,
  url: String
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
