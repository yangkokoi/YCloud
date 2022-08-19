import { createApp } from 'vue'
import App from './App.vue'
import store from './store';
import router from './router';
// import './registerServiceWorker'

const Vue = createApp(App)
Vue.use(router)
Vue.use(store)

// import requets of axios
import http from './http/XMLHttpRequest'
Vue.provide('$axios', http)
// Vue.config.globalProperties.$axios = axios;

// import api
import API from './http/API';
Vue.provide('$api', API)

// import Tools
import Tools from './utils/Tools.js'
Vue.config.globalProperties.$tools = Tools;

// import element-Notification
// import Notification from 'element-ui';

// import common component
import components from "./components/index";
Vue.use(components);

import { message } from 'ant-design-vue';
import 'ant-design-vue/es/message/style/css'; //vite只能用 ant-design-vue/es 而非 ant-design-vue/lib
Vue.config.globalProperties.$message = message;
// window.$message = message;

// 时间格式化 过滤器
import DateFormat from './utils/DateFormat'
Vue.config.globalProperties.$filters = {
  dateFormat(times: Date, pattern: string) {
    return DateFormat(times, pattern);
  }
}

Vue.mount('#app')
export default Vue

