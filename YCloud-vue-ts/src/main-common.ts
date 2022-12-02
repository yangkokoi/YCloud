
import { createApp } from 'vue'
import App from './App.vue'
import store from './store';
import router from './router';

// import './registerServiceWorker'

const Vue = createApp(App)
Vue.use(store)
Vue.use(router)

// import common component
import components from "./components/index";
Vue.use(components);

// 时间格式化 过滤器
import DateFormat from './utils/DateFormat'
Vue.config.globalProperties.$filters = {
  dateFormat(times: Date, pattern: string) {
    return DateFormat(times, pattern);
  }
}

export default Vue
