import pc from "./main-pc";
import mobile from "./main-m";

const Vue = window.isMobile ? mobile : pc;

Vue.mount("#app");

export default Vue;