<template>
  <div class="preview">
    <component
      :is="viewType"
      :resource="props.resource"
    />
  </div>
</template>

<script setup>
import { computed } from "vue";
import Image from "./Image.vue";
import Text from "./Text.vue";
import Default from "./Default.vue";
const props = defineProps({
  resource: {
    type: Object,
    required: true
  }
});

const viewType = computed({
  get: () => {
    const type = props.resource.type;
    if ("png,jpg,jpeg,gif".indexOf(type) !== -1) {
      return Image;
    } else if ("txt,md,js,java,cpp,c,py,go".indexOf(type) !== -1) {
      return Text;
    } else {
      return Default;
    }
  },
  set: () => { }
});

</script>

<style scoped>
.preview {
  height: 320px;
  background-color: #F7F7F7;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 10px;
  box-sizing: border-box;
}
</style>
