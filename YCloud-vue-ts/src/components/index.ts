// import DataTable from "./DataTable"
// import Pagination from "./Pagination"

const components = {
  install: function(Vue: any) {
    Vue.component("AdminHome", () => import("./AdminHome.vue"));
  }
}

export default components

