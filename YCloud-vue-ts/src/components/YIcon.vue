<template>
  <span class="y-icon">
    <slot>
      <i
        :class="getIconClass"
        :style="{color: color, fontSize: sizePx}"
      />
    </slot>
  </span>
</template>

<script lang="ts">
export default {
  name: "YIcon",
  props: {
    name: {
      type: String,
      required: true
    },
    size: {
      type: Number,
      default: 16
    },
    color: {
      type: String,
      default: undefined
    }
  },
  data() {
    return {
      sizePx: this.size + "px"
    };
  },
  computed: {
    getIconClass() {
      return this.name.startsWith("fa") ? `fa ${this.name}` : this.name;
    }
  }
};
</script>

<style>
.y-icon {
  display: inline-flex;
  align-items: center;
  text-align: center;
  padding: 0 3px;
}
</style>
