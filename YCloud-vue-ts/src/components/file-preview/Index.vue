<template>
  <div class="preview">
    <component :is="viewType" :resource="props.resource"></component>
  </div>
</template>

<script setup>
import { ref, computed } from "vue"
import Image from "./Image.vue"
import Text from "./Text.vue"
import Default from "./Default.vue"
const props = defineProps({
  resource: Object
});

const viewType = computed({
  get: () => {
    const type = props.resource.type;
    if ("png,jpg,jpeg,gif".indexOf(type) != -1) {
      return Image;
    } else if ("txt,md,js,java") {
      return Text;
    } else {
      return Default;
    }
  },
  set: () => { },
});

</script>

<style scoped>
.preview {
  height: 300px;
  background-color: #F7F7F7;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 10px;
  overflow: auto;
}
</style>