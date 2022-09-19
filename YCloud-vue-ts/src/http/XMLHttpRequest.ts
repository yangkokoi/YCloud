import axios, { AxiosResponse, AxiosRequestConfig } from 'axios'
import $router from '@/router/index'
import { main } from '@/store/main'
import { createDiscreteApi } from 'naive-ui'

import {
  SERVER_BASE
} from "./API"

import type { Method } from 'axios'

type YRes = {
  code: Number,
  msg: String,
  data: Object
}

type YAxiosResponse = AxiosResponse<any, YRes>;
type YAxiosRequestConfig = AxiosRequestConfig<YRes>;

const { message } = createDiscreteApi(
  ["message"],
);


// 请求是否带上cookie
axios.defaults.withCredentials = false;
// 添加请求拦截器
axios.interceptors.request.use(
  function (config: YAxiosRequestConfig) {
    // 在发送请求之前做些什么
    // 添加此之前添加Token
    // console.log(store.state.token)
    if (isMyApi(config.url)) {
      // if (!config.headers) config.headers = {}
      config.headers["Authorization"] = "Bearer " + main().token;
    }
    return config;
  },
  function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
  }
);

type RES_CODE_TYPE = {
  [code: string]: string
}
const RES_CODE: RES_CODE_TYPE =  {
  "401": "授权过期",
  "403": "权限不足，本次请求被拒绝",
}

// 添加响应拦截器
axios.interceptors.response.use(
  function (response: YAxiosResponse) {
    // 对响应数据做点什么
    return response;
  },
  function (error) {
    // 对响应错误做点什么
    // 如果不是请求自己API的请求，将进行跳过
    if (!isMyApi(error.response.config.url)) {
      return Promise.reject(error.response);
    }
    // 如果是登录过期或没有通过认证
    if (error.response.status == 401) {
      logout();
    }
    console.log(error.response)
    return Promise.reject(error.response);
  }
);


/**
 * 判断是否我们自己的接口
 * @param {String} url
 */
function isMyApi(url: String = ""): boolean {
  return url.indexOf(SERVER_BASE) === -1 ? false : true;
}

/**
 * 退出登录方法
 */
function logout() {
  // 删除token
  main().logout();
  // 跳转至登录界面
  $router.push({
    name: "Login",
    params: {
      // msg: data.msg
    }
  })
}

function request(method: Method, url: string, data: any, headers = {}) {
  // 如果要展示页面提醒
  // if (isShow) headers["Show-Time"] = "Hello";
  return axios({
    method: method,
    url: url,
    data: data !== "GET" ? data : null,
    // data: method === 'POST' || method === 'PUT' ? data : null,
    params: method === "GET" ? data : null,
    // `headers` 是即将被发送的自定义请求头
    headers: Object.assign({
      "Content-Type": "application/json;charset=UTF-8"
    }, headers),
  }).then((res) => {
    if (res.data.code === 200) {
      return res.data;
    } else {
      // message("warning", res.msg);
      return Promise.reject(res);
    }
  }).catch((err: AxiosResponse) => {
    // 如果服务端传了消息，则使用后台的消息提醒
    if (err.data!.msg) message.warning(err.data.msg);
    else if (err.data!.error) message.warning(err.data.error);
    else message.warning(RES_CODE[err.status]);
    // 将错误往方法调用的页面传
    return Promise.reject(err);
  });
}

const raw = axios.create();

export { raw }

export default {

  "raw": raw,
  "get": (url: string, data?: any) => {
    return request("GET", url, data);
  },
  "post": (url: string, data?: any, headers?: object | undefined) => {
    return request("POST", url, data, headers);
  },
  "put": (url: string, data: any) => {
    return request("PUT", url, data);
  },
  "delete": (url: string, data: any) => {
    return request("DELETE", url, data);
  },
  "upload": (url: string, data: any) => {
    return request("POST", data, { "Content-Type": "application/x-www-form-urlencoded" });
  },
  "message": message

}
