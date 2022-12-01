import Vue from "./main-common";

import { createDiscreteApi } from "naive-ui";
const { dialog, loadingBar, message } = createDiscreteApi(
  ["dialog", "loadingBar", "message"],
);
window.$dialog = dialog;
window.$loadingBar = loadingBar;
window.$message = message;

Vue.mount('#app');

export default Vue
