<template>
  <div class="file-details">
    <!-- 文件预览 -->
    <Preview :resource="props.resource" />
    <div style="margin: 10px;text-align: center;">
      <n-tag
        :bordered="false"
        type="success"
      >
        {{ resource.name }}
      </n-tag>
    </div>

    <n-descriptions
      label-placement="left"
      :column="1"
      label-style="font-weight: 700"
    >
      <n-descriptions-item label="MD5">
        {{ resource.hash }}
      </n-descriptions-item>
      <n-descriptions-item label="文件大小">
        {{ resource.size > MB
          ? (resource.size / MB).toFixed(2) + "MB"
          : resource.size > 1024 ? parseInt(resource.size / 1024) + "KB" : parseInt(resource.size) + "B" }}
      </n-descriptions-item>
      <n-descriptions-item label="文件类型">
        {{ resource.type }}
      </n-descriptions-item>
      <n-descriptions-item label="更新日期">
        {{ new Date(resource.updateTime).format() }}
      </n-descriptions-item>
      <n-descriptions-item label="创建日期">
        {{ new Date(resource.createTime).format() }}
      </n-descriptions-item>
    </n-descriptions>

    <div class="operator">
      <n-button
        v-if="resource.edit === true"
        strong
        round
        color="#ff69b4"
        @click="editFile"
      >
        编辑
      </n-button>
      <n-button
        v-if="user().user && user().user?.username !== 'guest'"
        strong
        secondary
        round
        type="success"
        @click="openShare"
      >
        分享
      </n-button>
      <!-- <n-button strong secondary round type="error">
        删除资源
      </n-button> -->
    </div>

    <!-- 分享模态框 -->
    <n-modal
      v-model:show="shareDialog.visible"
      preset="dialog"
      title="资源分享"
      :on-after-leave="shareDialog.closed"
      :show-icon="false"
      :mask-closable="false"
    >
      <n-spin
        v-if="!shareDialog.url"
        class="share-content"
        :show="shareDialog.loading"
      >
        <n-form
          ref="formRef"
          :label-width="80"
          :model="shareDialog.form"
          :rules="shareDialog.rules"
          label-placement="left"
        >
          <n-form-item label="截止日期">
            <n-date-picker
              v-model:value="shareDialog.form.deadline"
              type="date"
              clearable
              :is-date-disabled="(val) => new Date() > val"
              :shortcuts="shortcuts"
              placeholder="留空则表示永久"
            />
          </n-form-item>
          <n-form-item
            label="加密分享"
            path="password"
          >
            <n-switch
              v-model:value="shareDialog.enablePwd"
              style="margin-right: 10px;"
            />
            <n-input
              v-show="shareDialog.enablePwd"
              v-model:value="shareDialog.form.password"
              :maxlength="4"
              show-count
              clearable
              :allow-input="(val) => !val || /^[a-zA-Z0-9]+$/.test(val)"
              placeholder="无需自定义密码可留空"
            />
          </n-form-item>
        </n-form>
      </n-spin>
      <div
        v-else
        class="share-content"
      >
        <n-input-group>
          <n-input
            ref="shareUrlRef"
            v-model:value="shareDialog.url"
            readonly
          >
            <template #prefix>
              <!-- <n-icon :component="FlashOutline" /> -->
            </template>
          </n-input>
          <n-button
            type="primary"
            ghost
            @click="copyUrl"
          >
            复制
          </n-button>
        </n-input-group>
        <vue-qrcode
          class="qrcode"
          :value="shareDialog.url"
          :options="{ width: 200 }"
        />
        <div>
          密码：<span>{{ shareDialog.form.password }}</span><br>
          到期时间：<span>{{ shareDialog.form.deadline == null ? "永久" : new Date(shareDialog.form.deadline).format("YYYY-MM-DD HH:mm:ss") }}</span>
        </div>
      </div>
      <template #action>
        <n-button
          v-if="!shareDialog.url"
          size="small"
        >
          算了
        </n-button>
        <n-button
          v-if="!shareDialog.url"
          size="small"
          color="#ff69b4"
          @click="shareHandler"
        >
          分享
        </n-button>
        <n-button
          v-else
          size="small"
          color="#ff69b4"
          @click="shareDialog.visible = false"
        >
          好的
        </n-button>
      </template>
    </n-modal>
  </div>
</template>

<script setup>
import { ref, reactive, inject } from "vue";
import Preview from "@/components/file-preview/FilePreview.vue";
import http from "@/http/XMLHttpRequest";
import { preview as HttpPreview } from "@/http/Explore";
import { user } from "@/store/user";
import VueQrcode from "@chenfengyuan/vue-qrcode";
import { shareDialog, shareHandler } from "./index";
import { copyText } from "@/utils/Tools";

const showEditor = inject("showEditor");
const props = defineProps({
  resource: {
    type: Object,
    required: true
  }
});
const MB = 1048576;

const oneDay = 24 * 60 * 60 * 1000;
const shortcuts = reactive({
  一天: () => new Date().getTime() + oneDay,
  七天: new Date().getTime() + oneDay * 7,
  一个月: () => new Date().getTime() + oneDay * 31
});

const editFile = function() {
  HttpPreview(props.resource.id).then(url => {
    http.post(props.resource.url).then(({ data }) => {
      showEditor(props.resource.id, props.resource.name, data);
    }).catch(() => {
      window.$message.error("资源已被删除");
    });
  });
};

const openShare = function() {
  shareDialog.visible = true;
  shareDialog.form.resourceIds = props.resource.id + "";
};

const shareUrlRef = ref(null);
const copyUrl = async function() {
  shareUrlRef.value.select();
  await copyText(shareDialog.url);
  window.$message.success("复制成功");
};
</script>

<style scoped lang="scss">
.file-details {
  width: 100%;

  .operator {
    margin-top: 30px;
    text-align: center;

    button {
      margin: 0 10px;
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

</style>
