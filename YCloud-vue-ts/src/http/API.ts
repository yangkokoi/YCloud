/**
 * API文件
 */
const API_PATH: string = "API_PATH";
// window.localStorage.setItem("API_PATH", "http://localhost:8999")
// window.localStorage.setItem("API_PATH", "https://cloud.xiaosm.xiaosm.cn")
const SERVER_BASE: string = window.sessionStorage.getItem(API_PATH) || import.meta.env.VITE_APP_BASE || "https://cloud.xiaosm.cn";   // 服务器地址
const SERVER_API: string = SERVER_BASE + '/api';   // 服务器API地址

type apiType = {
  [name: string]: string
}

const apis: apiType = {
  SERVER_BASE: SERVER_BASE,

  LOGIN: "/login",
  LOGOUT: "/logout",
  // USER_REGISTER: "/register",

  // 用户 API
  USER_API: "/user",
  USER_INFO: "/user/info",
  USER_OPEN: "/user/open",
  USER_TRACK: "/user/info/track",
  USER_PASS: "/user/password",

  // 仓库
  BUCKET_API: "/bucket",

  // 资源
  RESOURCE_API: "/resource",
  RESOURCE_CREATE: "/resource/create",
  RESOURCE_DELETE: "/resource/delete",
  RESOURCE_RENAME: "/resource/rename",

  ROLE_API: "/role",
  ROLE_MENU_API: "/role/menu",

  MENU_API: "/menu",
  MENU_TREE_API: "/menu/tree",

  TASK_API: "/task",

  // 系统监控API
  LOG_API: "/log",

  SYSTEM_API: "/system",

  // 配置信息API
  PROP_API: "/prop",

  MAIL_API: "/mail",
  MAIL_SEND: "/mail/send",

  // 将数据导出成 excel 表API
  EXCEL_EXPORT: "/excel",

  TOKEN_QINIU: "/token/qiniu",
  FILE_UPLOAD: "/upload",

  // 第三方登录
  OAUTH_QQ: SERVER_BASE + "/oauth/render/qq",
}

const SERVER_UPLOAD: string = SERVER_API + "/resource/upload";   // 上传地址
const SERVER_PRE_DOWNLOAD: string = SERVER_API + "/resource/pre_download";   // 预下载处理地址
const SERVER_DOWNLOAD: string = SERVER_BASE + "/resource/download";   // 下载地址
const SERVER_PREVIEW: string = SERVER_BASE + "/resource/preview";   // 下载地址
const EXCEL_EXPORT: string = SERVER_API + apis.EXCEL_EXPORT;   // 导出为表格地址

const apisBuild = function () {
  const regx = /^(http)[s]{0,1}(:\/\/)/;
  Object.keys(apis).forEach(key => {
    apis[key] = regx.test(apis[key]) ? apis[key] : SERVER_API + apis[key];
  });
  return apis;
}

// const apiList = apisBuild();
const API = function (uri: string, apiLink = true) {
  return apiLink ? SERVER_API + uri : SERVER_BASE + uri ;
}

export {
  SERVER_BASE, 
  SERVER_API,
  SERVER_UPLOAD, 
  SERVER_DOWNLOAD, 
  SERVER_PRE_DOWNLOAD, 
  SERVER_PREVIEW, 
  EXCEL_EXPORT,
}

export default API;
