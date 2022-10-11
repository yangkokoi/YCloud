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
  <PassInput v-else-if="showPwd" :num="6" :pass="pass"></PassInput>
</template>

<script setup lang="ts">
import PassInput from "@/components/PassInput.vue"
import { ref } from "vue"
import { useRouter } from 'vue-router';
import { main } from '@/store/main'
import { login } from "@/http/User"

const $router = useRouter();
const showPwdRaw = !main().token;
const showPwd = ref(showPwdRaw);

const pass = function(pwd: string) {
  login("guest", pwd).then(res => {
    // showPwd.value = false;
    $router.push({path: "/explorer/local"});
  });
  
}
</script>

<style scoped>
  .home-box {
    height: 100%;
    display: flex;
    align-items: center;
  }
</style>