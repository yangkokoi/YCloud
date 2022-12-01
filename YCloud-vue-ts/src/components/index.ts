// import DataTable from "./DataTable"
// import Pagination from "./Pagination"

const components = {
  install: function(Vue: any) {
    Vue.component("YIcon", () => import("./YIcon.vue"));
  }
}

export default components

