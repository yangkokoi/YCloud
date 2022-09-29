import http, { axios, API, isMyApi, alertErrMsg } from "./XMLHttpRequest"
import { AxiosResponse, AxiosRequestConfig } from 'axios'
import $router from '@/router/index'
import { sharePinia } from '@/store/share'

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
      $router.push({
        name: "SharePreview",
        params: {
          // msg: data.msg
        }
      })
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
  return http.post(API("/share/create"), data).then(res => {
    if (res.code == 200) {
      return res.data;
    }
  });
}

/**
 * 根据分享 id 获取分享内容
 * @param uuid 
 * @param password 
 * @returns Promise
 */
 export const pass = (uuid: string, password: string, option: {}) => {
  return instance.post(API("/share/pass"), {uuid, password}, option).then(res => {
    if (res.code == 200 && res.token) {
      sharePinia().setToken(uuid, res.token);
    } else {
      return Promise.reject(res);
    }
    return res;
  });
}

/**
 * 根据分享 id 获取分享内容
 * @param uuid 
 * @param password 
 * @returns Promise
 */
export const getShareList = (uuid: string) => {
  return instance.post(API("/share/info"), { uuid }).then(res => {
    if (res.code == 200) {
      return res.data;
    }
  });
}

export default {
  createShare,
  pass,
  getShareList
}