<template>
  <div class="home-box" v-if="!showPwd">
    <n-upload action="https://www.mocky.io/v2/5e4bafc63100007100d8b70f">
      <n-upload-dragger>
        <div style="margin-bottom: 12px">
          <n-icon size="70" :depth="3">
            <!-- <archive-icon /> -->
          </n-icon>
        </div>
        <n-text style="font-size: 16px">
          点击或者拖动文件到该区域来上传
        </n-text>
        <n-p depth="3" style="margin: 8px 0 0 0">
          请不要上传敏感数据，比如你的银行卡号和密码，信用卡号有效期和安全码
        </n-p>
      </n-upload-dragger>
    </n-upload>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router';

const $router = useRouter();

</script>

<style scoped>
  .home-box {
    height: 100%;
    display: flex;
    align-items: center;
  }
</style>