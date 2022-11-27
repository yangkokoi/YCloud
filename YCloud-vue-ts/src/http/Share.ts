import http, { axios, API, isMyApi, alertErrMsg } from "./XMLHttpRequest"
import { AxiosResponse, AxiosRequestConfig } from 'axios'
import $router from '@/router/index'
import { sharePinia } from '@/store/share'
import type { RespBody } from "./XMLHttpRequest"

const instance = axios.create({
  headers: {
    "Content-Type": "application/json;charset=utf-8"
  }
});


// 请求是否带上cookie
instance.defaults.withCredentials = false;
// 添加请求拦截器
instance.interceptors.request.use(
  function (config: AxiosRequestConfig) {
    if (!config.headers) config.headers = {};
    config.headers["Authorization"] = "Bearer " + sharePinia().token;
    return config;
  }
);

// 添加响应拦截器
instance.interceptors.response.use(
  function (response: AxiosResponse) {
    // 对响应数据做点什么
    if (response.data.code === 200) {
      return response.data;
    } else {
      alertErrMsg(response);
      return Promise.reject(response);
    }
  },
  function (error) {
    // 如果是登录过期或没有通过认证
    if (error.response.status == 401) {
      // 删除token
      sharePinia().clearToken();
      // $router.push({
      //   name: "Share",
      //   params: {
      //     // msg: data.msg
      //   }
      // })
      $router.go(0);
    }
    alertErrMsg(error.response);
    return Promise.reject(error.response);
  }
);


/**
 * 创建分享链接
 * @param data 
 * @returns Promise
 */
export const createShare = (data: any) => {
  return http.post(API("/share/create"), data).then((res) => {
    if (res.code == 200) {
      return res.data;
    }
  });
}

/**
 * 根据分享 id 获取分享内容
 * @param id 
 * @param password 
 * @returns Promise
 */
 export const pass = (id: string, password: string, option: any = {}) => {
  if (password == null) {
    option.show = false;
  }
  return instance.post(API("/share/pass"), {id, password}, option).then((res: any) => {
    if (res.code == 200 && res.token) {
      sharePinia().setToken(id, res.token);
    } else {
      return Promise.reject(res);
    }
    return res;
  });
}

/**
 * 根据分享 id 获取分享内容
 * @param id 
 * @returns Promise
 */
export const getShareList = (id: string, path: string) => {
  return instance.post(API("/share/list"), { id, path }).then((res: any) => {
    if (res.code == 200) {
      return res.data;
    }
  });
}

export const download = (id: string | number, path: string) => {
  return instance.post(API("/share/pre_download"), {
    resourceId: id,
    path
  }).then((res: any) => {
    if (res.code == 200) {
      window.open(`${API("/resource/download", false)}?entry=${res.data}`);
    }
  });
}

export default {
  createShare,
  pass,
  getShareList,
  download
}