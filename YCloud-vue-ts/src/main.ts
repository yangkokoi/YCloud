import { createApp } from 'vue'
import App from './App.vue'
import store from './store';
import router from './router';

// import './registerServiceWorker'

const Vue = createApp(App)
Vue.use(store)
Vue.use(router)

// import http from './http/XMLHttpRequest'
// Vue.provide('$axios', http)
// Vue.config.globalProperties.$axios = axios;

// import API from './http/API';
// Vue.provide('$api', API)

// import Tools from './utils/Tools.js'
// Vue.config.globalProperties.$tools = Tools;

// import common component
import components from "./components/index";
Vue.use(components);

// window.$message = message;

import {createDiscreteApi} from "naive-ui";
const { message, loadingBar } = createDiscreteApi(
  ["message", "loadingBar"],
);
Vue.config.globalProperties.$message = message

// 时间格式化 过滤器
import DateFormat from './utils/DateFormat'
Vue.config.globalProperties.$filters = {
  dateFormat(times: Date, pattern: string) {
    return DateFormat(times, pattern);
  }
}

Vue.mount('#app');

export default Vue

