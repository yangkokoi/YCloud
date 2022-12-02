<template>
  <div
    id="tableOperation"
  >
    <div style="display: flex;column-gap: 15px;">
      <n-upload
        :action="SERVER_UPLOAD"
        style="width: unset"
        name="files"
        :headers="{
          'Authorization': `Bearer ${user().token}`
        }"
        :data="{
          'bucketName': props.name,
          'path': props.path.join('/')
        }"
        :custom-request="customRequest"
      >
        <n-button
          color="#FF69B4"
          size="small"
        >
          <template #icon>
            <n-icon>
              <svg
                xmlns="http://www.w3.org/2000/svg"
                xmlns:xlink="http://www.w3.org/1999/xlink"
                viewBox="0 0 24 24"
              >
                <path
                  d="M19.35 10.04A7.49 7.49 0 0 0 12 4C9.11 4 6.6 5.64 5.35 8.04A5.994 5.994 0 0 0 0 14c0 3.31 2.69 6 6 6h13c2.76 0 5-2.24 5-5c0-2.64-2.05-4.78-4.65-4.96zM14 13v4h-4v-4H7l5-5l5 5h-3z"
                  fill="currentColor"
                />
              </svg>
            </n-icon>
          </template>
          上传
        </n-button>
      </n-upload>
      <n-dropdown
        trigger="click"
        :options="popselect.options"
        @select="popselect.handleSelect"
      >
        <n-button
          color="#2684FE"
          size="small"
        >
          <template #icon>
            <n-icon>
              <svg
                xmlns="http://www.w3.org/2000/svg"
                xmlns:xlink="http://www.w3.org/1999/xlink"
                viewBox="0 0 512 512"
              >
                <path
                  d="M256 48C141.31 48 48 141.31 48 256s93.31 208 208 208s208-93.31 208-208S370.69 48 256 48zm96 224h-80v80h-32v-80h-80v-32h80v-80h32v80h80z"
                  fill="currentColor"
                />
              </svg>
            </n-icon>
          </template>
          创建
        </n-button>
      </n-dropdown>
      <n-button
        color="#FF7550"
        size="small"
      >
        <template #icon>
          <n-icon>
            <svg
              xmlns="http://www.w3.org/2000/svg"
              xmlns:xlink="http://www.w3.org/1999/xlink"
              viewBox="0 0 20 20"
            >
              <g fill="none">
                <path
                  d="M10 1.25a2.75 2.75 0 0 1 2.739 2.5H17a.75.75 0 0 1 .102 1.493L17 5.25h-.583L15.15 16.23A2 2 0 0 1 13.163 18H6.837a2 2 0 0 1-1.987-1.77L3.582 5.25H3a.75.75 0 0 1-.743-.648L2.25 4.5a.75.75 0 0 1 .648-.743L3 3.75h4.261A2.75 2.75 0 0 1 10 1.25zM8.5 7.5c-.245 0-.45.155-.492.359L8 7.938v6.125l.008.078c.042.204.247.359.492.359s.45-.155.492-.359L9 14.062V7.939l-.008-.08C8.95 7.656 8.745 7.5 8.5 7.5zm3 0c-.245 0-.45.155-.492.359L11 7.938v6.125l.008.078c.042.204.247.359.492.359s.45-.155.492-.359l.008-.079V7.939l-.008-.08c-.042-.203-.247-.358-.492-.358zM10 2.75c-.605 0-1.11.43-1.225 1h2.45c-.116-.57-.62-1-1.225-1z"
                  fill="currentColor"
                />
              </g>
            </svg>
          </n-icon>
        </template>
        删除
      </n-button>
      <n-button
        color="#18A058"
        size="small"
        @click="refresh"
      >
        <template #icon>
          <n-icon>
            <svg
              style="width: 1.2rem; height: 1.2rem"
              xmlns="http://www.w3.org/2000/svg"
              xmlns:xlink="http://www.w3.org/1999/xlink"
              viewBox="0 0 512 512"
            >
              <path
                d="M256 48C141.31 48 48 141.32 48 256c0 114.86 93.14 208 208 208c114.69 0 208-93.31 208-208c0-114.87-93.13-208-208-208zm0 313a94 94 0 0 1 0-188h4.21l-14.11-14.1a14 14 0 0 1 19.8-19.8l40 40a14 14 0 0 1 0 19.8l-40 40a14 14 0 0 1-19.8-19.8l18-18c-2.38-.1-5.1-.1-8.1-.1a66 66 0 1 0 66 66a14 14 0 0 1 28 0a94.11 94.11 0 0 1-94 94z"
                fill="currentColor"
              />
            </svg>
          </n-icon>
        </template>
        刷新
      </n-button>

      <!-- <div v-if="user && user?.username === 'guest'"> -->
      <div v-if="user().user && user().user?.username === 'guest'">
        当前用户为共享账户，您上传的文件将对所有人可见，请勿上传敏感数据
      </div>
    </div>
    <YFilePath
      :name="props.name"
      :path="props.path"
      :click-bread="props.clickBread"
    />

    <n-modal
      v-model:show="renameDialog.visible"
      preset="dialog"
      :title="renameDialog.title"
      positive-text="确认"
      negative-text="算了"
      :show-icon="false"
      :mask-closable="false"
      @positive-click="makeDirHandler(renameDialog.value)"
      @after-leave="clearDialog"
    >
      <div>
        <n-input
          v-model:value="renameDialog.value"
          :allow-input="(value: string) => !value.startsWith(' ') && !value.endsWith(' ')"
          :minlength="1"
          :maxlength="20"
          :status="renameDialog.status"
          :on-input="() => renameDialog.status = ''"
          type="text"
        />
      </div>
    </n-modal>
  </div>
</template>

<script setup lang="ts">
import YFilePath from "./FilePath.vue";
import { h, inject, reactive } from "vue";
import { user } from "@/store/user";
import { SERVER_UPLOAD } from "@/http/API";
import { axios } from "@/http/XMLHttpRequest";
import { createFile } from "@/http/Explore";
// import YButton from "@/components/YButton.vue"
import type { AxiosRequestConfig } from "axios";
import type { UploadCustomRequestOptions } from "naive-ui";
import YIcon from "../YIcon.vue";

const showEditor = inject<Function>("showEditor");
const props = defineProps({
  name: {
    type: String,
    required: true
  },
  path: {
    type: [Array],
    default(val: Array<String>) {
      return val;
    }
  },
  clickBread: {
    type: Function,
    default: () => {
      console.log(null);
    }
  }
});

const popselect = reactive({
  value: "",
  options: [
    {
      key: "file",
      label: "创建文件",
      icon: () => h(YIcon, { name: "fa-file-circle-plus" })
    }, {
      key: "dir",
      label: "创建目录",
      icon: () => h(YIcon, { name: "fa-folder-plus" })
    }
  ],
  handleSelect(key: string) {
    popselect.value = key;
    renameDialog.visible = true;
  }
});

/* 新建文件模态框 */
const renameDialog = reactive({
  visible: false,
  value: "",
  status: "",
  title: "文件名"
});

const clearDialog = function() {
  renameDialog.value = "";
  renameDialog.status = "";
};

// const upload = function() {

// };

/**
 * 创建文件或目录
 * @param fileName 文件名
 */
const makeDirHandler = function(fileName: string) {
  if (!fileName || fileName.trim() === "") {
    window.$message.warning("文件名不可以为空");
    renameDialog.status = "warning";
    return false;
  }
  return createFile({
    bucketName: props.name,
    path: props.path.join("/"),
    name: fileName,
    type: popselect.value
  }).then((res) => {
    console.log(res);
    refresh();
    // 如果是文件类型，将在文件创建成功后打开编辑器
    if (popselect.value === "file") {
      showEditor(res, fileName, "");
    }
  }).catch(() => {
    renameDialog.status = "error";
    return Promise.reject(new Error());
  });
};

const refresh = function() {
  // emits("clickBread", props.path.length);
  props.clickBread(props.path.length);
};

const customRequest = ({
  file,
  data,
  headers,
  withCredentials,
  action,
  onFinish,
  onError,
  onProgress
}: UploadCustomRequestOptions) => {
  const formData = new FormData();
  if (data) {
    Object.keys(data).forEach((key) => {
      formData.append(
        key,
        data[key as keyof UploadCustomRequestOptions["data"]]
      );
    });
  }
  formData.append("files", file.file as File);
  console.log(data);
  axios.post(action as string, formData, {
    method: "POST",
    withCredentials,
    headers,
    onUploadProgress: ({ loaded, total }) => {
      onProgress({ percent: Math.ceil((loaded / total) * 100) });
    }
  } as AxiosRequestConfig)
    .then((e) => {
      if (e.data.code === 200) {
        window.$message.success(e.data.msg);
        onFinish();
        refresh();
      } else {
        window.$message.warning(e.data.msg);
        onError();
      }
    }).catch((error) => {
      window.$message.warning(error.data.msg);
      onError();
    });
};

</script>

<style scoped>
#tableOperation {
  height: 125px;
  box-sizing: border-box;
  text-align: left;
  padding: 10px 0;
  padding-left: 5px;
}
</style>
