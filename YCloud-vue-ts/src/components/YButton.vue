<!-- <script lang="ts">
import { defineComponent } from 'vue'
import { NButton } from "naive-ui"

export default defineComponent({
  extends: NButton,
  setup: NButton.setup,
  props: {
    permission: [String]
  },
  mounted() {
    if (this.permission !== "resource:add") {
      this.$el.style.display = "none";
    }
  }
});
</script> -->

<template>
  <div v-if="$props.permission === 'resource:add'">
    <n-button v-bind="$attrs">
      <slot v-for="(s, name) in $slots" :name="name"></slot>
    </n-button>
    我是新增的元素
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
  inheritAttrs: false,
  props: {
    permission: String
  },
  mounted() {
    
  }
});
</script>