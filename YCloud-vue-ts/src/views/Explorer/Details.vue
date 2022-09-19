<template>
  <div class="file-details">
    <!-- 文件预览 -->
    <Preview :resource="props.resource"></Preview>
    <div style="margin: 10px;text-align: center;">
      <n-tag :bordered="false" type="success">
        {{resource.name}}
      </n-tag>
    </div>

    <n-descriptions label-placement="left" :column="1" label-style="font-weight: 700">
      <n-descriptions-item label="文件ID">
        {{resource.uuid}}
      </n-descriptions-item>
      <n-descriptions-item label="文件大小">
        {{resource.size > MB
          ? (resource.size / MB).toFixed(2) + "MB"
          : parseInt(resource.size / 1024) + "KB"}}
      </n-descriptions-item>
      <n-descriptions-item label="午餐">
        苹果
      </n-descriptions-item>
      <n-descriptions-item label="更新日期">
        {{new Date(resource.updateTime).format()}}
      </n-descriptions-item>
      <n-descriptions-item label="创建日期">
        {{new Date(resource.createTime).format()}}
      </n-descriptions-item>
    </n-descriptions>

    <div class="operator">
      <n-button strong secondary round type="success" @click="openShare">
        分享
      </n-button>
      <!-- <n-button strong secondary round type="error">
        删除资源
      </n-button> -->
    </div>

    <!-- 分享模态框 -->
    <n-modal v-model:show="shareDialog.visible" 
      preset="dialog" title="资源分享"
      :onAfterLeave="shareDialog.closed"
      :showIcon="false" :maskClosable="false" >
      <n-spin class="share-content" v-if="!shareDialog.url" :show="shareDialog.loading">
        <n-form
          ref="formRef"
          :label-width="80"
          :model="shareDialog"
          :rules="shareDialog.rules"
          label-placement="left"
        >
          <n-form-item label="截止日期">
            <n-date-picker 
              v-model:value="shareDialog.deadline" 
              type="date" clearable 
              :is-date-disabled="(val) => new Date() > val"
              :shortcuts="shortcuts"
              placeholder="留空则表示永久" />
          </n-form-item>
          <n-form-item label="加密分享" path="password">
            <n-switch v-model:value="shareDialog.enablePwd" style="margin-right: 10px;" />
            <n-input :maxlength="4" show-count clearable 
              v-show="shareDialog.enablePwd" 
              v-model:value="shareDialog.password" 
              :allow-input="(val) => !val || /^[a-zA-Z0-9]+$/.test(val)"
              placeholder="无需自定义密码可留空" />
            <!-- <div class="pwd-input" v-show="shareDialog.enablePwd">
              <n-input :maxlength="1" v-model:value="shareDialog.password[0]" />
              <n-input :maxlength="1" v-model:value="shareDialog.password[1]" />
              <n-input :maxlength="1" v-model:value="shareDialog.password[2]" />
              <n-input :maxlength="1" v-model:value="shareDialog.password[3]" />
            </div> -->
          </n-form-item>
        </n-form>
      </n-spin>
      <div class="share-content" v-else>
        <n-input-group>
          <n-input ref="shareUrlRef" v-model:value="shareDialog.url" readonly >
            <template #prefix>
              <!-- <n-icon :component="FlashOutline" /> -->
            </template>
          </n-input>
          <n-button type="primary" ghost @click="copyUrl">复制</n-button>
        </n-input-group>
        <vue-qrcode class="qrcode" :value="shareDialog.url" :options="{ width: 200 }"></vue-qrcode>
      </div>
      <template #action>
        <n-button v-if="!shareDialog.url" size="small">算了</n-button>
        <n-button v-if="!shareDialog.url" size="small" color="#ff69b4" @click="shareHandler">分享</n-button>
        <n-button v-else size="small" color="#ff69b4" @click="shareDialog.visible = false">好的</n-button>
      </template>
    </n-modal>
  </div>
</template>

<script setup>
import { ref, reactive } from "vue";
import Preview from "@/components/file-preview/Index.vue"
import VueQrcode from '@chenfengyuan/vue-qrcode';
const props = defineProps({
  resource: Object
});
const MB = 1048576;

const oneDay = 24 * 60 * 60 * 1000;
const shortcuts = reactive({
  一天: () => new Date().getTime() + oneDay,
  七天: new Date().getTime() + oneDay * 7,
  一个月: () => new Date().getTime() + oneDay * 31
})

const shareUrlRef = ref(null);
const shareDialog = reactive({
  visible: true,
  loading: false,
  deadline: null,
  url: null,
  enablePwd: true,
  password: "",
  rules: {
    password: [
      {
        required: false,
        validator(rule, value) {
          if (!value) {
            return true;
          } else if (value.length != 4) {
            return new Error("密码长度应为4位字符");
          }
          return true;
        },
        trigger: ["blur"]
      }
    ]
  },
  closed() {
    shareDialog.url = null;
  }
});

const openShare = function() {
  shareDialog.visible = true;
}

const shareHandler = function() {
  shareDialog.loading = true;
  setTimeout(() => {
    shareDialog.url = "11111";
    shareDialog.loading = false;
  }, 1000);
}

const copyUrl = function() {
  shareUrlRef.value.select();
}
</script>

<style scoped lang="scss">
.file-details {
  width: 100%;

  .operator {
    margin-top: 30px;

    button {
      display: block;
      margin: 5px auto;
    }
  }
}

.share-content {
  padding-top: 20px;

  :deep(.qrcode) {
    display: block;
    margin: 0 auto;
  }
}

.pwd-input {
  display: flex;

  :deep(.n-input) {
    text-align: center;
    width: 40px;
    margin: 0 3px;
  }
}

</style>