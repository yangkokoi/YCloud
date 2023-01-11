<template>
  <div class="preview">
    <component
      :is="viewType"
      :resource="props.resource"
      :url="url"
    />
  </div>
</template>

<script setup>
import { computed, watch, ref } from "vue";
import YImage from "./YImage.vue";
import YText from "./YText.vue";
import Default from "./Default.vue";
import YImageM from "./YImage-m.vue";
import { preview } from "@/http/Explore";
const props = defineProps({
  api: {
    type: Function,
    required: false,
    default: preview
  },
  resource: {
    type: Object,
    required: true
  }
});

const isMobile = window.sessionStorage.getItem("isMobile");

const url = ref("");

watch(() => props.resource, () => {
  getUrl(props.resource.id).then(u => {
    url.value = u;
  });
});

const getUrl = async (id) => {
  let u = "";
  await props.api(id).then(url => {
    u = url;
  });
  return u;
};

getUrl(props.resource.id).then(u => {
  url.value = u;
});

const viewType = computed({
  get: () => {
    const type = props.resource.type;
    if ("png,jpg,jpeg,gif".indexOf(type) !== -1) {
      return isMobile ? YImageM : YImage;
    } else if ("txt,md,js,java,cpp,c,py,go".indexOf(type) !== -1) {
      return YText;
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
