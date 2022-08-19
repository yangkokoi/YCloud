import store from '../store/index'
// import { ElMessage } from 'element-plus'
import {
  SERVER_BASE,
  EXCEL_EXPORT
} from '../http/API';

/**
 * 这是对外暴露的检查角色的方法
 * @param {Object} component  组件名称
 */
const verifyAuth = function(component) {
  if (component == "Home") return true;
  if (component == "NotFound") return true;
  if (component == "Forbidden") return true;
  // 如果用户信息还是空的
  if (!store.state.user) {
    return false;
  }
  // console.log(store.state.user);
  return prVerifyAuth(store.state.user.menus, component, 0);
}

/**
 * 路由跳转之前做的权限检查
 * @param {Object} meuns      菜单集合
 * @param {String} component  组件名称
 * @param {Number} deep       递归深度
 */
const maxDeep = 3; // 最大递归深度
const prVerifyAuth = function(menus, component, deep) {
  // console.warn(menus, component)
  if (menus == null || menus.length == 0 || deep >= maxDeep) return false;
  for (let el of menus) {
    if (el.component == component) return true;
    else if (prVerifyAuth(el.children, component, deep + 1)) return true;
  }
  return false;
}

/**
 * 
 * @param {*} source 需要重置的对象
 * @param {*} vm vue实例对象
 */
const clearObj = function(source, vm) {
  Object.keys(source).forEach(key => {
    // console.log(key)
    if (Array.isArray(source[key])) {
      // console.log(vm)
      source[key].splice(0);
    } else {
      vm.$set(source, key, null);
    }
  });
}

/**
 * 拷贝对象
 * 将 source 里的值复制到 target
 * @param {Object} source
 * @param {Object} target
 * @param {Object} vm
 */
const copyObj = function(source, target, vm, ignore) {
  // console.log(vm)
  Object.keys(source).forEach(key => {
    vm.$set(target, key, source[key]);
  });
  return target;
}

const getDeepObj = function(obj) {
  return JSON.parse(JSON.stringify(obj));
}

const buildAvatar = function(avatar = "") {
  let path = SERVER_BASE;
  if (avatar == null) avatar = "";
  if (avatar.indexOf("http") == 0) {
    return avatar;
  }
  if (avatar.charAt(0) == "/") {
    path += avatar;
  } else {
    path += "/" + avatar;
  }
  // console.log(path);
  return path;
}

/**
 * @param {Array} table 需要导出的数据
 * @param {Array} columns 渲染表格的表头排序，同时也是表头名
 * @param {Array} exclude 不需要导出的数据
 */
const exportExcel = function(table, columns, filename = "导出数据", exclude = []) {
  columns.filter(col => {
    return (!exclude.includes(col.key));
  });
  console.log(table, columns);
  let form = document.createElement("form");
  form.action = EXCEL_EXPORT;
  form.method = "POST";
  form.style.display = "none";
  let formHtml = "<input name='_token' value='" + window.localStorage.getItem("token") + "'/>";
  formHtml += "<input name='name' value='" + filename + "'/>";
  columns.forEach((el, index) => {
    formHtml += "<input name='columns[" + index + "].key' value='" + el.key + "'/>";
    formHtml += "<input name='columns[" + index + "].value' value='" + el.value + "'/>";
  });
  // table.forEach((el, index) => {
  //   Object.keys(el).forEach((key) => {
  //     // console.log("data[" + index + "]." + key)
  //     formHtml += "<input name='data[" + index + "]." + key + "' value='" + el[key] + "'/>"
  //   });
  // });
  formHtml += "<input name='data' value='" + JSON.stringify(table) + "'/>";
  
  form.innerHTML = formHtml;
  console.log(formHtml);
  document.body.appendChild(form);
  form.submit();
}

const fullScreen = function(element = document) {
  console.log(element)
  let isFullSceen = element.isFullScreen || element.mozIsFullScreen || element.webkitIsFullScreen;
  let requestMethod =
    element.requestFullScreen ||
    element.webkitRequestFullScreen ||
    element.mozRequestFullScreen ||
    element.msRequestFullScreen;
  if (isFullSceen) { //根据是否处于全屏状态 来进行切换 
    if (element.exitFullscreen) {
      element.exitFullscreen();
    } else if (element.mozCancelFullScreen) {
      element.mozCancelFullScreen();
    } else if (element.webkitCancelFullScreen) {
      element.webkitCancelFullScreen();
    } else if (element.msExitFullscreen) {
      element.msExitFullscreen();
    }
  } else {
    requestMethod.call(element); //打开全屏
    // element.requestMethod();
  }
  //返回操作后的全屏状态 
  return !isFullSceen;
}

const tools = {
  copyObj: copyObj,
  clearObj: clearObj,
  getDeepObj: getDeepObj,
  verifyAuth: verifyAuth,
  buildAvatar: buildAvatar,
  exportExcel: exportExcel,
  fullScreen: fullScreen,
}

export default tools;
