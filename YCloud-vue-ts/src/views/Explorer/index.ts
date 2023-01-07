import { reactive, ref, inject } from "vue";
import API from "@/http/Explore";
import type { Resource } from "@/type/type";
import { useRoute } from "vue-router";
import shareHttp from "@/http/Share";

interface ShareDialogType {
  [key: string]: any
  form: {
    resourceIds: string
    password: string
    deadline: string | number | null
  }
}

export const shareDialog = reactive<ShareDialogType>({
  visible: false,
  loading: false,
  url: "",
  enablePwd: true,
  form: {
    resourceIds: "",
    password: "",
    deadline: null
  },
  rules: {
    password: [
      {
        required: false,
        validator(rule: any, value: string) {
          if (!value) {
            return true;
          } else if (value.length !== 4) {
            return new Error("密码长度应为4位字符");
          }
          return true;
        },
        trigger: ["blur"]
      }
    ]
  },
  closed() {
    shareDialog.url = "";
    shareDialog.enablePwd = true;
    shareDialog.form = {
      resourceIds: "",
      password: "",
      deadline: null
    };
  }
});

const SHARE_PREVIEW_URL = "/share";
export const shareHandler = function() {
  shareDialog.loading = true;
  let deadline = shareDialog.form.deadline;
  if (typeof deadline === "string") {
    deadline = new Date(deadline).getTime();
  }
  shareHttp.createShare({
    ...shareDialog.form,
    deadline,
    password: shareDialog.enablePwd ? shareDialog.form.password : null
  }).then((res: any) => {
    setTimeout(() => {
      shareDialog.url = `${location.origin}${SHARE_PREVIEW_URL}/${res.id}`;
      shareDialog.form.password = res.password;
      shareDialog.form.deadline = res.deadline;
      shareDialog.loading = false;
    }, 1000);
  }).catch(() => {
    shareDialog.loading = false;
  });
};

export default {
  setup() {
    const setUploadPath: Function | undefined = window.isMobile ? inject("setUploadPath") : undefined;
    
    const $route = useRoute();
    const bucket = reactive({
      name: $route.params.name || "local",
      nameZh: "本地存储"
    });

    /* table中的文件列表数据 */
    const fileList = ref<Resource[]>([]);

    /* 面包屑 */
    const explorerPath = ref<string[]>([]);
    /* 面包屑 END */

    /* 重命名模态框 */
    const renameDialog = reactive({
      visible: false,
      value: "",
      status: ""
    });

    /**
     * null 值表示回到根目录
     * 正值数字表示跳转到第几层目录，从左往右
     * 负值数字表示回退几层
     * 字符表示前进到指定目录
     * @param path 
     */
    const intoPath = function (config: {path: string, parentId: string} | number | null) {
      const backup = explorerPath.value;
      let path: null | string = null;
      let parentId: null | string = null;
      if (config === null) {

      } else if (typeof config === "number") {
        if (config === 0) {
          explorerPath.value = [];
        } else if (config === -1) {
          // 回退一层
          explorerPath.value.splice(-1);
        } else if (config >= 0) {
          // 这里是适配pc端的
          explorerPath.value.splice(config, explorerPath.value.length - config);
        }
      } else if (typeof config === "object") {
        if (config.path !== null) {
          explorerPath.value.push(config.path);
        }
        if (config.parentId !== null) {
          parentId = config.parentId;
        }
      }
      path = explorerPath.value.join("/");
      API.listResource({
        bucketName: bucket.name,
        path,
        parentId
      }).then((data) => {
        fileList.value = data;
        // console.log(data);
        if (setUploadPath) setUploadPath(path);
      }).catch(() => {
        explorerPath.value = backup;
      });
    };

    const calcSize = function (size: number) {
      const MB = 1048576;
      return size > MB
        ? (size / MB).toFixed(2) + "MB"
        : Math.floor(size / 1024) + "KB";
    }

    const refresh = function() {
      intoPath(explorerPath.value.length);
    };

    /**
    * 重命名文件
    */
   const renameHandler = function(row: Resource | undefined): Promise<any> {
     if (!row) return Promise.resolve();
     if (row.name === renameDialog.value) {
       return Promise.resolve();
     }
     if (renameDialog.value.trim() === "") {
       window.$message.warning("文件名不可以为空");
       renameDialog.status = "warning";
       return Promise.reject();
     }
     return API.renameFile(row.id, renameDialog.value).then(() => {
       window.$message.success("名称修改成功");
       refresh();
     }).catch(err => {
       renameDialog.status = "error";
       return Promise.reject(err);
     });
   };

    return {
      bucket, fileList, explorerPath,
      // 右键文件后 临时保存的文件对象
      clickFile: ref<Resource>(),
      renameDialog,
      intoPath, calcSize,
      refresh,
      renameHandler
    }
  },
}