<template>
  <var-popup
    v-bind="$attrs"
    position="bottom"
  >
    <div style="margin: 20px 15px;">
      <div
        v-if="(title.length > 0)"
        class="y-pop-title"
      >
        {{ title }}
      </div>
      <div class="y-pop-body">
        <slot />
      </div>
    </div>
  </var-popup>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  inheritAttrs: false,
  props: {
    title: {
      type: String,
      default: ""
    }
  },
  mounted() {

  }
});
</script>

<style lang="scss" scoped>
.y-pop-title {
  margin-bottom: 10px;
  font-size: 1.8rem;
  font-weight: bold;
}
</style>
