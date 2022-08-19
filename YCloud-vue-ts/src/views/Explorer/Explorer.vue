<template>
  <n-layout
    position="absolute"
    content-style="display: flex;flex-direction: column"
  >
    <n-layout-header
      bordered
      style="height: 60px; padding-top: 10px; flex-shrink: 0;"
    >
      <explorer-header></explorer-header>
    </n-layout-header>

    <n-layout content-style>
      <explorer-tool-bar
        :name="$route.params.name"
        :path="explorerPath"
        @clickBread="intoPath"
      ></explorer-tool-bar>
      <n-data-table
        :bordered="false"
        :row-key="(row) => (row.uuid ? row.uuid : row.name)"
        :row-props="rowProps"
        :max-height="getHeight"
        :columns="columns"
        :data="fileList"
        @update:checked-row-keys="handleCheck"
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

      <n-modal v-model:show="renameDialog.visible" preset="dialog" title="重命名"
        positive-text="确认"
        negative-text="算了"
        @positive-click="renameHandler(clickFile)"
      >
        <div>内容</div>
      </n-modal>
    </n-layout>
  </n-layout>
</template>

<script setup>
import {
  readonly,
  reactive,
  ref,
  h,
  nextTick,
  computed,
  onMounted,
  inject,
  provide,
} from "vue";
import { useRouter, useRoute } from "vue-router";
import ExplorerHeader from "/src/components/ExplorerHeader.vue";
import ExplorerToolBar from "/src/components/ExplorerToolBar.vue";
import { NButton, NInput, NIcon } from "naive-ui";
import { File, Folder, FileImage, FileCode, Splotch } from "@vicons/fa";
import API from "@/http/Explore";

const $route = useRoute();
const $router = useRouter();

/* table中的文件列表数据 */
const fileList = ref([]);
/* 面包屑 */
const explorerPath = ref([]);
/* 重命名模态框 */
const renameDialog = reactive({
  visible: false,
  value: "",
});
/* 面包屑 END */
provide(
  "explorerPath",
  computed(() => explorerPath.value)
);

/* ref */
const tableOperation = ref(null);
const getHeight = computed({
  get: () => {
    // if (tableOperation.value) console.log(tableOperation.value);
    // 此处计算 需要减去头部的 头部的 110 和面包屑的 30
    const main = document.getElementById("main");
    return main && tableOperation.value
      ? main.clientHeight -
          115 -
          tableOperation.value.clientHeight +
          "px"
      : null;
  },
  set: () => {},
});

onMounted(() => {
  // console.log($route);
  explorerPath.value = $route.query.path ? $route.query.path.split("/") : [];
  intoPath();
});

/* 刷新 */
const refresh = function () {
  intoPath(explorerPath.value.length);
};
/* 刷新 END */

const intoPath = function (path) {
  const backup = explorerPath.value;
  if (path == -1) explorerPath.value = [];
  else if (typeof path === "number") {
    explorerPath.value.splice(path + 1, explorerPath.value.length - path - 1);
  } else if (typeof path === "string") {
    explorerPath.value.push(path);
  }
  path = explorerPath.value.join("/");
  API.listResource({
    bucketName: $route.params.name,
    path,
  }).then((data) => {
    fileList.value = data;
    // console.log(data);
  }).catch(() => {
    explorerPath.value = backup;
  });
};

/* 右键菜单 */
// 右键文件后 临时保存的文件对象
const clickFile = ref(null);
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
      },
    },
  },
  {
    label: "复制",
    key: "duplicate",
    props: {
      onClick: () => {
        // 通过保存的文件进行操作
        console.log(clickFile.value);
      },
    },
  },
  {
    label: "拷贝",
    key: "copy",
  },
  {
    label: "剪切",
    key: "cut",
  },
  {
    label: () => h("span", { style: { color: "red" } }, "删除"),
    key: "delete",
    props: {
      onClick: () => {
        deleteFile(clickFile.value);
      },
    },
  },
  {
    label: () => h("span", { style: { color: "red" } }, "重命名"),
    key: "rename",
    props: {
      onClick: () => {
        // 通过保存的文件进行操作
        renameDialog.visible = true;
      },
    },
  },
];
const handleSelect = function () {
  showMenu.value = false;
  // console.log("handleSelect");
};

const onClickoutside = function () {
  showMenu.value = false;
};
/* 右键菜单 END */

/* Table */
// 选择的文件
const checkedRowKeysRef = ref([]);
const handleCheck = function (rowKeys) {
  checkedRowKeysRef.value = rowKeys;
};
// 表格配置
const rowProps = (row) => {
  return {
    id: "tr" + row.hash,
    // 右键菜单
    onContextmenu: (e) => {
      // window.$message.info(JSON.stringify(row, null, 2))
      e.preventDefault();
      showMenu.value = false;
      nextTick().then(() => {
        clickFile.value = row;
        showMenu.value = true;
        mouse.x = e.clientX;
        mouse.y = e.clientY;
      });
    },
  };
};

const columns = readonly([
  {
    type: "selection",
    disabled(row) {
      return row.hash === "Edward King 3";
    },
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
            color: row.type === "dir" ? "#E67E22" : "#03885B",
          },
          onClick: (e) => {
            e.stopPropagation();
            if (row.type === "dir") {
              console.log(row.name);
              intoPath(row.name);
            } else {
            }
          },
        },
        [fileIcon(row.type), row.name]
      );
    },
  },
  {
    title: "修改日期",
    key: "updateTime",
    width: "170",
    render: (row) => {
      return row.type == "dir"
        ? "-"
        : new Date(row.updateTime).format("yyyy/MM/dd HH:mm");
    },
  },
  {
    title: "类型",
    key: "type",
    width: "100",
    render: (row) => {
      return row.dir === true ? "-" : row.type;
    },
  },
  {
    title: "大小",
    key: "size",
    width: "150",
    render: (row) => {
      const MB = 1048576; // 2 << 20
      return row.type === "dir"
        ? "-"
        : row.size > MB
        ? (row.size / MB).toFixed(2) + "MB"
        : parseInt(row.size / 1024) + "KB";
    },
  },
  {
    title: "操作",
    key: "operation",
    width: "150",
    render: (row) => {
      if (row.type == "dir") {
        
      } else {
        // 渲染一个删除按钮
        return h(
          NButton,
          {
            quaternary: true,
            type: "error",
            size: "tiny",
            onClick: () => {
              // deleteFile(row);
            },
          },
          () => {
            return "删除";
          }
        );
      }
    },
  },
]);

const fileIcon = function (type = "FILE") {
  let icon = File;
  type = type.toUpperCase();
  if (type === "DIR") icon = Folder;
  else if (["JPG", "PNG", "JPEG"].includes(type)) icon = FileImage;
  else if (["txt", "js", "ts", "java", "md"].includes(type)) icon = FileCode;
  else if (["exe"].includes(type)) icon = Splotch;
  return h(
    NIcon,
    { size: 13, color: type === "DIR" ? "#E66E05" : "#333", class: "fileIcon" },
    () => h(icon)
  );
};
/* Table END */

/// 以下事件处理器

/**
 * 创建目录
 * @param {*} name 
 */
const createDir = function (name) {
  $axios
    .post(`${$api.RESOURCE_CREATE}`, {
      bucketName: $route.params.name,
      path: explorerPath.value.join("/"),
      name: name,
      fileType: "DIR",
    })
    .then((res) => {
      if (res.code == 200) {
        $meesage.success("新建目录成功");
        refresh();
      } else {
        $message.warning(res.msg);
      }
    });
};

/**
 * 删除文件操作
 */
const deleteFile = function (row) {
  API.deleteFile(row.id).then(() => {
    window.$message.success("删除资源成功");
    refresh();
  });
};

/**
 * 重命名文件
 */
const renameHandler = function(row) {
  if (renameDialog.value.trim() == "") {
    window.$message.warning("文件名不可以为空");
    return;
  }
  API.renameFile(row.id, row.name).then(() => {
    window.$message.success("名称修改成功");
    refresh();
  });
}

/**
 * 文件下载
 */
const download = function (row) {
  API.download(row.id);
};
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