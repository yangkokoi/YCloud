import Vue from "./main-common";
import Header from "@/components/mobile/CloudHeader.vue";

Vue.component("CloudHeader", Header);

import '@varlet/ui/es/style.js'
import '@varlet/ui/es/snackbar/style/index.js';
import { Snackbar } from '@varlet/ui';

if (window.isMobile) {
    window.$message = Snackbar;
}

Vue.directive("search", {
    mounted(el, binding) {
        const input = (el as HTMLElement).querySelector("input");
        if (input) {
            input.type = "search";
        }
    }
});

export default Vue;
