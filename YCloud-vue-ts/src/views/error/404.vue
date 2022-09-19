<template>
  <div class="error-page">
    <n-empty description="你什么也找不到">
      <template #extra>
        <n-button size="small" @click="this.$router.push('/')">
          看看别的
        </n-button>
      </template>
    </n-empty>
  </div>
</template>

<script>
import { useRouter } from 'vue-router'
export default {
  data() {
    return {
      $router: useRouter()
    }
  },
  mounted() {
    console.log(this);
  }
}
</script>

<style>
.error-page {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>