<template>
  <n-breadcrumb style="height: 30px;line-height:30px;margin-top: 10px">
    <n-breadcrumb-item @click="clickBread(0)">
      <span style="background-color: #91DDFF; padding: 3px 7px">
        <n-icon>
          <svg
            xmlns="http://www.w3.org/2000/svg"
            xmlns:xlink="http://www.w3.org/1999/xlink"
            viewBox="0 0 24 24"
          >
            <path
              d="M14.59 7.41L18.17 11H6v2h12.17l-3.59 3.59L16 18l6-6l-6-6l-1.41 1.41zM2 6v12h2V6H2z"
              fill="currentColor"
            />
          </svg>
        </n-icon>
        {{ props.name }}
      </span>
      <template #separator>
        <!-- 空分隔符 -->
        <n-icon class="triangle-icon">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            xmlns:xlink="http://www.w3.org/1999/xlink"
            viewBox="0 0 12 12"
          >
            <g fill="none">
              <path
                d="M6.786 1.459a.903.903 0 0 0-1.572 0L1.122 8.628C.774 9.238 1.211 10 1.91 10H10.09c.698 0 1.135-.762.787-1.372l-4.092-7.17z"
                fill="currentColor"
              />
            </g>
          </svg>
        </n-icon>
      </template>
    </n-breadcrumb-item>
    <template
      v-for="(item, index) in props.path"
      :key="index"
    >
      <n-breadcrumb-item @click="clickBread(index + 1)">
        {{ item }}
      </n-breadcrumb-item>
    </template>
  </n-breadcrumb>
</template>

<script setup lang="ts">
const props = defineProps({
  name: {
    type: String,
    required: true
  },
  path: {
    type: Array,
    default(val: Array<String>) {
      return val;
    }
  },
  clickBread: {
    type: Function,
    default(val: Function) {
      console.info("unchange");
      return val;
    }
  }
});

const clickBread = function(index: number | null) {
  props.clickBread(index);
};

</script>

<style scoped>
.triangle-icon {
    position: relative;
    top: 3px;
    left: -17px;
    width: 25px;
    height: 25px;
    margin-right: -14px;
    transform: rotateZ(90deg);
    cursor: pointer;
}

.triangle-icon svg {
    width: 100%;
    height: 100%;
    color: #91ddff;
}

.triangle-icon svg:hover {
    color: #91ddff;
}
</style>
