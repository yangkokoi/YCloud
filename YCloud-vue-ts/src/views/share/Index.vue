<template>
  <div
    v-if="shareData !== null"
    class="share-page"
  >
    <div style="width: 960px;margin: auto;">
      <div class="share-header">
        <span>过期时间：{{ shareData.deadline ? new Date(shareData.deadline) : "永久" }}</span>
      </div>
      <div class="resource-list">
        <FilePath
          name="Share"
          :path="explorerPath"
          :click-bread="intoPath"
        />
        <n-data-table
          :bordered="false"
          :row-key="(row) => (row.id ? row.id : row.name)"
          :columns="columns"
          :data="shareData.resourceList"
          @update:checked-row-keys="handleCheck"
        />
      </div>
    </div>
  </div>
  <PassInput
    v-else-if="showPwd"
    :id="id"
    :pass-ok="() => list()"
  />
</template>

<script setup>
import PassInput from "@/components/PassInput.vue";
import FilePath from "@/components/ExplorerToolBar/FilePath.vue";
import { readonly, onMounted, ref, h } from "vue";
import { useRoute } from "vue-router";
import { sharePinia } from "@/store/share";
import API from "@/http/Share";
import { NButton } from "naive-ui";
import { fileIcon } from "@/components/file-table/common.js";

const $route = useRoute();
const id = ref($route.params.id);
const shareData = ref(null);
/* 面包屑 */
const explorerPath = ref([]);
const showPwd = ref(false);
/* 面包屑 END */

onMounted(() => {
  window.$loadingBar.start();
  // list().then(() => window.$loadingBar.finish()).catch(() => window.$loadingBar.error());
  API.pass(id.value, null).then(() => {
    list();
  }).catch(() => {
    showPwd.value = true;
  });
  setTimeout(() => {
    window.$loadingBar.finish();
  }, 10000);
});

const list = function(path) {
  if (sharePinia().token === null) {
    showPwd.value = true;
    return Promise.resolve(1);
  }
  window.$loadingBar.start();
  return API.getShareList(id.value, path).then(res => {
    shareData.value = res;
    console.log(shareData.value);
    window.$loadingBar.finish();
  });
};

const intoPath = function(path) {
  const backup = explorerPath.value;
  if (path === -1) explorerPath.value = [];
  else if (typeof path === "number") {
    explorerPath.value.splice(path + 1, explorerPath.value.length - path - 1);
  } else if (typeof path === "string") {
    explorerPath.value.push(path);
  }
  path = explorerPath.value.join("/");
  list(path).catch(() => {
    explorerPath.value = backup;
  });
};

/* Table */
// 选择的文件
const checkedRowKeysRef = ref([]);
const handleCheck = function(rowKeys) {
  checkedRowKeysRef.value = rowKeys;
};

const columns = readonly([
  {
    type: "selection",
    disabled(row) {
      return row.id === "Edward King 3";
    }
  },
  {
    title: "文件名",
    key: "name",
    render: (row) => {
      return h(
        "a",
        {
          class: "fileLink",
          style: {
            color: row.type === "dir" ? "#E67E22" : "#03885B"
          },
          onClick: (e) => {
            e.stopPropagation();
            if (row.type === "dir") {
              intoPath(row.name);
            } else {
              /*  */
            }
          }
        },
        [fileIcon(row.type), row.name]
      );
    }
  },
  {
    title: "修改日期",
    key: "updateTime",
    width: "170",
    render: (row) => {
      return row.type === "dir"
        ? "-"
        : new Date(row.updateTime).format("yyyy/MM/dd HH:mm");
    }
  },
  {
    title: "类型",
    key: "type",
    width: "100",
    render: (row) => {
      return row.dir === true ? "-" : row.type;
    }
  },
  {
    title: "大小",
    key: "size",
    width: "100",
    render: (row) => {
      const MB = 1048576; // 2 << 20
      return row.type === "dir"
        ? "-"
        : row.size > MB
          ? (row.size / MB).toFixed(2) + "MB"
          : parseInt(row.size / 1024) + "KB";
    }
  },
  {
    title: "操作",
    key: null,
    width: "100",
    render: (row) => {
      // const MB = 1048576; // 2 << 20
      return row.type !== "dir"
        ? h(
          NButton,
          {
            size: "tiny",
            type: "info",
            tertiary: true,
            onClick: () => API.download(row.id, explorerPath.value.join("/"))
          },
          { default: () => "下载" }
        )
        : null;
    }
  }
]);
/* Table END */

/**
 * 文件下载
 */
function download(row) {

}
</script>

<style lang="scss" scoped>
.share-page {
  width: 100%;
  height: 100%;

  .share-header {
    height: 100px;
    display: flex;
    flex-direction: column-reverse;
  }

  .resource-list {
    height: calc(100% - 100px);
  }
}

:deep(.fileLink) {
  cursor: pointer;
}

:deep(.fileLink:hover) {
  text-decoration: underline;
}

:deep(.fileIcon) {
  margin-right: 5px;
}
</style>
