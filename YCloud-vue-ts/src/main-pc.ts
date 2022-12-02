import Vue from "./main-common";

import { createDiscreteApi } from "naive-ui";

const { dialog, loadingBar, message } = createDiscreteApi(
  ["dialog", "loadingBar", "message"],
);
if (!window.isMobile) {
  window.$dialog = dialog;
  window.$loadingBar = loadingBar;
  window.$message = message;
}

export default Vue
