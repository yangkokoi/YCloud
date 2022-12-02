/// <reference types="vite/client" />

import {MessageProviderInst, DialogProviderInst, LoadingBarProviderInst} from "naive-ui";
import { IDialog, ISnackbar } from "@varlet/ui";

declare module '*.vue' {
  import type { DefineComponent } from 'vue'
  // eslint-disable-next-line @typescript-eslint/no-explicit-any, @typescript-eslint/ban-types
  const component: DefineComponent<{}, {}, any>
  export default component
}

declare module 'vue' {
  interface ComponentCustomProperties {
    $message: MessageProviderInst;
    // $http: typeof axios;
  }
}

declare global {
  interface Window {
    $message: MessageProviderInst | ISnackbar;
    $dialog: DialogProviderInst | IDialog;
    $loadingBar: LoadingBarProviderInst;
    ace: any;
    isMobile: boolean;
  }

  interface Date {
    format: Function
  }
}

// export {}
