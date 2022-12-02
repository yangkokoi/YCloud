import { h } from "vue";
import {
  SERVER_BASE,
  EXCEL_EXPORT
} from "../http/API";

/**
 * @param {*} source 需要重置的对象
 * @param {*} vm vue实例对象
 */
const clearObj = function (source, vm) {
  Object.keys(source).forEach(key => {
    // console.log(key)
    if (Array.isArray(source[key])) {
      // console.log(vm)
      source[key].splice(0);
    } else {
      vm.$set(source, key, null);
    }
  });
};

/**
 * 拷贝对象
 * 将 source 里的值复制到 target
 * @param {Object} source
 * @param {Object} target
 * @param {Object} vm
 */
const copyObj = function (source, target, vm, ignore) {
  // console.log(vm)
  Object.keys(source).forEach(key => {
    vm.$set(target, key, source[key]);
  });
  return target;
};

const getDeepObj = function (obj) {
  return JSON.parse(JSON.stringify(obj));
};

const buildAvatar = function (avatar = "") {
  let path = SERVER_BASE;
  if (avatar == null) avatar = "";
  if (avatar.indexOf("http") === 0) {
    return avatar;
  }
  if (avatar.charAt(0) === "/") {
    path += avatar;
  } else {
    path += "/" + avatar;
  }
  // console.log(path);
  return path;
};

/**
 * @param {Array} table 需要导出的数据
 * @param {Array} columns 渲染表格的表头排序，同时也是表头名
 * @param {Array} exclude 不需要导出的数据
 */
const exportExcel = function (table, columns, filename = "导出数据", exclude = []) {
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

const fullScreen = function (element = document) {
  console.log(element);
  let isFullSceen = element.isFullScreen || element.mozIsFullScreen || element.webkitIsFullScreen;
  let requestMethod =
    element.requestFullScreen ||
    element.webkitRequestFullScreen ||
    element.mozRequestFullScreen ||
    element.msRequestFullScreen;
  if (isFullSceen) { // 根据是否处于全屏状态 来进行切换 
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
    requestMethod.call(element); // 打开全屏
    // element.requestMethod();
  }
  // 返回操作后的全屏状态
  return !isFullSceen;
};

export const download = function(url) {
  if ((/Safari/.test(navigator.userAgent) && !/Chrome/.test(navigator.userAgent))) {
    window.$dialog.info({
      title: "下载",
      style: {
        textAlign: "center"
      },
      content: () => h("a", {
        href: url,
        target: "_blank",
        style: "font-weight: bold;font-size: 1rem"
      }, "点我下载"),
      positiveText: "确定"
    });
  } else {
    const a = document.createElement("a");
    a.style.display = "none";
    a.href = url;
    a.target = "_blank";
    document.body.appendChild(a);
    if (document.all) {
      a.click();
    } else {
      // 兼容 Firfox
      const evt = document.createEvent("MouseEvents");
      evt.initEvent("click", true, true);
      a.dispatchEvent(evt);
    }
    document.removeChild(a);
  }
};

export const copyText = async function(text) {
  if (typeof text !== "string") return;
  if (text.length === 0) return;
  if (navigator.clipboard) {
    await navigator.clipboard.writeText(text);
  }
};

const tools = {
  copyObj,
  clearObj,
  getDeepObj,
  buildAvatar,
  exportExcel,
  fullScreen,
  download,
  copyText
};

export default tools;
