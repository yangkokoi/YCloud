<template>
  <div style="display: flex;flex-direction: column">
    <div style="height: 60px; padding-top: 10px; flex-shrink: 0;border-bottom: 1px solid rgb(239, 239, 245);box-sizing: border-box;">
      <explorer-header />
    </div>

    <div
      ref="listTable"
      style="display: flex;height: calc(100% - 60px);"
    >
      <div style="flex: 0 0 720px">
        <explorer-tool-bar
          :name="$route.params.name as string"
          :path="explorerPath"
          :click-bread="intoPath"
        />
        <n-data-table
          ref="tableRef"
          style="height: calc(100% - 125px);"
          :bordered="false"
          :row-key="(row: Resource) => (row.uuid ? row.uuid : row.name)"
          :row-props="rowProps"
          :max-height="tableHeight - 48"
          :columns="columns"
          :data="fileList"
        />
        <n-dropdown
          placement="bottom-start"
          trigger="manual"
          :x="mouse.x"
          :y="mouse.y"
          :options="options"
          :show="showMenu"
          :on-clickoutside="onClickoutside"
          @select="handleSelect"
        />
      </div>
      <div
        v-if="previewResource"
        style="width: calc(100% - 720px - 20px);padding: 20px 0 20px 20px;"
      >
        <FileDetails :resource="previewResource" />
      </div>
    </div>

    <!-- 重命名模态框 -->
    <n-modal
      v-model:show="renameDialog.visible"
      preset="dialog"
      title="重命名"
      positive-text="确认"
      negative-text="算了"
      :show-icon="false"
      :mask-closable="false"
      @positive-click="renameHandler(clickFile)"
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

    <!-- 文件编辑模态框 -->
    <n-modal
      v-model:show="editorDialog.visible"
      preset="dialog"
      :title="editorDialog.title"
      positive-text="确认"
      negative-text="算了"
      :show-icon="false"
      :mask-closable="false"
      :style="{
        width: '80%',
      }"
      @positive-click="saveContent(clickFile)"
    >
      <TextEditor
        ref="editorRef"
        :height="editorDialog.height"
        :content="editorDialog.content"
      />
    </n-modal>

    <!-- 移动文件模态框 -->
    <n-modal
      v-model:show="moveOrCopyDialog.visible"
      preset="dialog"
      :title="moveOrCopyDialog.title"
      positive-text="确认"
      negative-text="算了"
      :show-icon="false"
      :mask-closable="false"
      :on-after-enter="moveOrCopyDialog.init"
      @positive-click="moveOrCopyHandler(clickFile)"
    >
      <n-tree-select
        v-model:value="moveOrCopyDialog.value"
        check-strategy="all"
        key-field="id"
        label-field="name"
        placeholder="留空表示根目录：/"
        clearable
        :show-path="true"
        :options="moveOrCopyDialog.options"
        :on-load="moveOrCopyDialog.loadDir"
      />
    </n-modal>
  </div>
</template>

<script setup lang="ts">
import ExplorerHeader from "@/components/ExplorerHeader.vue";
import ExplorerToolBar from "@/components/ExplorerToolBar/ExplorerToolBar.vue";
import FileDetails from "./Details.vue";
import TextEditor from "@/components/TextEditor/TextEditor.vue";
import { fileIcon } from "@/components/file-table/common.js";
import {
  readonly,
  reactive,
  ref,
  h,
  nextTick,
  onMounted,
  provide
} from "vue";
import { useRoute } from "vue-router";
import API from "@/http/Explore";
import type { Resource } from "@/type/type";
import { TreeSelectOption } from "naive-ui";

const $route = useRoute();

/* table中的文件列表数据 */
const fileList = ref<Resource[]>([]);
/* 重命名模态框 */
const renameDialog = reactive({
  visible: false,
  value: "",
  status: ""
});
/* 编辑器模态框 */
const editorDialog = reactive({
  visible: false,
  id: "",
  title: "",
  content: "",
  height: window.innerHeight * 0.7 + "px"
});
const moveOrCopyDialog = reactive({
  visible: false,
  title: "移动",
  value: "0", // 目录 id
  options: [],
  init() {
    if (moveOrCopyDialog.options.length > 0) return;
    return API.listResource({
      bucketName: $route.params.name,
      type: "dir"
    }).then((data) => {
      console.log(data);
      data.forEach((el: any) => {
        el.isLeaf = false;
      });
      moveOrCopyDialog.options = data;
    }).catch(() => {

    });
  },
  loadDir(option: TreeSelectOption) {
    return API.listResource({
      bucketName: $route.params.name,
      parentId: option.id,
      type: "dir"
    }).then((data) => {
      if (data.length === 0) {
        option.isLeaf = true;
      } else {
        data.forEach((el: any) => {
          el.isLeaf = false;
        });
        option.children = data;
      }
      Promise.resolve(data);
    });
  }
});

/* 面包屑 */
const explorerPath = ref<string[]>([]);
/* 面包屑 END */

/* ref */
const editorRef = ref();
const tableRef = ref(null);
const tableHeight = ref(500);
onMounted(() => {
  // console.log($route);
  explorerPath.value = $route.query.path ? ($route.query.path as string).split("/") : [];
  intoPath();
  // if (tableOperation.value) console.log(tableOperation.value);
  // 此处计算 需要减去头部的 头部的 110 和面包屑的 30
  nextTick(() => {
    tableHeight.value = tableRef.value ? tableRef.value.$el.clientHeight : 500;
  });
});

/* 刷新 */
const refresh = function() {
  intoPath(explorerPath.value.length);
};
/* 刷新 END */

const intoPath = function(path: number | string | undefined = undefined) {
  const backup = explorerPath.value;
  if (path === -1) explorerPath.value = [];
  else if (typeof path === "number") {
    explorerPath.value.splice(path + 1, explorerPath.value.length - path - 1);
  } else if (typeof path === "string") {
    explorerPath.value.push(path);
  }
  path = explorerPath.value.join("/");
  API.listResource({
    bucketName: $route.params.name,
    path
  }).then((data) => {
    fileList.value = data;
    // console.log(data);
  }).catch(() => {
    explorerPath.value = backup;
  });
};

/* 右键菜单 */
// 右键文件后 临时保存的文件对象
const clickFile = ref<Resource>();
// 鼠标位置
const mouse = reactive({ x: 300, y: 300 });
// 是否显示右键菜单
const showMenu = ref(false);
// * 右键菜单 template
const options = [
  {
    label: () => h("b", { style: { color: "#03885B" } }, "下载"),
    key: "download",
    props: {
      onClick: () => {
        // 通过保存的文件进行操作
        download(clickFile.value);
      }
    }
  },
  {
    label: "复制",
    key: "copy",
    props: {
      onClick: () => {
        // 通过保存的文件进行操作
        moveOrCopyDialog.visible = true;
      }
    }
  },
  {
    label: "复制副本",
    key: "duplicate",
    props: {
      onClick: () => {
        // 通过保存的文件进行操作
        moveOrCopyDialog.visible = true;
      }
    }
  },
  {
    label: "移动",
    key: "move",
    props: {
      onClick: () => {
        // 通过保存的文件进行操作
        moveOrCopyDialog.visible = true;
      }
    }
  },
  {
    label: () => h("span", { style: { color: "red" } }, "删除"),
    key: "delete",
    props: {
      onClick: () => {
        deleteFile(clickFile.value);
      }
    }
  },
  {
    label: () => h("span", { style: { color: "red" } }, "重命名"),
    key: "rename",
    props: {
      onClick: () => {
        // 通过保存的文件进行操作
        renameDialog.visible = true;
        renameDialog.value = clickFile.value ? clickFile.value.name : "";
      }
    }
  }
];
const handleSelect = function() {
  showMenu.value = false;
  // console.log("handleSelect");
};

const onClickoutside = function() {
  showMenu.value = false;
};
/* 右键菜单 END */

const previewResource = ref<Resource | null>(null);

/* Table */
// 选择的文件
// const checkedRowKeysRef = ref([]);
// const handleCheck = function(rowKeys) {
//   checkedRowKeysRef.value = rowKeys;
// };
// 表格配置
const rowProps = (row: Resource) => {
  return {
    id: "tr" + row.uuid,
    // 右键菜单
    onContextmenu: (e: MouseEvent) => {
      // window.$message.info(JSON.stringify(row, null, 2))
      e.preventDefault();
      showMenu.value = false;
      nextTick().then(() => {
        clickFile.value = row;
        showMenu.value = true;
        mouse.x = e.clientX;
        mouse.y = e.clientY;
      });
    }
  };
};

const columns = readonly([
  {
    type: "selection",
    disabled(row: Resource) {
      return row.uuid === "Edward King 3";
    }
  },
  {
    title: "文件名",
    key: "name",
    render: (row: Resource) => {
      return h(
        "a",
        {
          class: "fileLink",
          style: {
            color: row.type === "dir" ? "#E67E22" : "#03885B"
          },
          onClick: (e: MouseEvent) => {
            e.stopPropagation();
            if (row.type === "dir") {
              console.log(row.name);
              intoPath(row.name);
            } else {
              API.preview(row.id).then(url => {
                previewResource.value = {
                  ...row,
                  url,
                  name: row.name
                };
              });
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
    render: (row: Resource) => {
      return row.type === "dir"
        ? "-"
        : new Date(row.updateTime).format("yyyy/MM/dd HH:mm");
    }
  },
  {
    title: "类型",
    key: "type",
    width: "100",
    render: (row: Resource) => {
      return row.dir === true ? "-" : row.type;
    }
  },
  {
    title: "大小",
    key: "size",
    width: "100",
    render: (row: Resource) => {
      const MB = 1048576; // 2 << 20
      return row.type === "dir"
        ? "-"
        : row.size > MB
          ? (row.size / MB).toFixed(2) + "MB"
          : Math.floor(row.size / 1024) + "KB";
    }
  }
]);

/* Table END */

/// 以下事件处理器

/**
 * 删除文件操作
 */
const deleteFile = function(row: Resource | undefined) {
  if (!row) return;
  API.deleteFile(row.id).then(() => {
    window.$message.success("删除资源成功");
    refresh();
  });
};

/**
 * 重命名文件
 */
const renameHandler = function(row: Resource | undefined) {
  if (!row) return;
  if (renameDialog.value.trim() === "") {
    window.$message.warning("文件名不可以为空");
    renameDialog.status = "warning";
    return false;
  }
  return API.renameFile(row.id, renameDialog.value).then(() => {
    window.$message.success("名称修改成功");
    refresh();
  }).catch(err => {
    renameDialog.status = "error";
    return Promise.reject(err);
  });
};

/**
 * 文件移动、复制
 * @param row 文件对象
 */
const moveOrCopyHandler = function(row: Resource | undefined) {
  if (!row) return;
  return API.moveOrCopyFile(row.id, moveOrCopyDialog.value, "copy");
};

/**
 * 保存文件内容
 * @param resource
 */
const saveContent = function(resource: Resource | undefined) {
  if (!resource) return;
  const val = editorRef.value.getValue();
  return API.saveContent(editorDialog.id, val).then(() => {
    window.$message.success("保存成功");
  }).catch((err) => {
    return Promise.reject(err);
  });
};

/**
 * 文件下载
 */
const download = function(row: Resource | undefined) {
  if (!row) return;
  API.download(row.id);
};

const showEditor = function(id: string, filename: string, content: string) {
  editorDialog.id = id;
  editorDialog.title = filename;
  editorDialog.content = content;
  editorDialog.visible = true;
};

provide("showEditor", showEditor);
</script>

<style scoped>
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
