/// <reference types="vite/client" />

import {MessageProviderInst, DialogProviderInst, LoadingBarProviderInst} from "naive-ui";

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
    $message: MessageProviderInst;
    $dialog: DialogProviderInst;
    $loadingBar: LoadingBarProviderInst;
    ace: any;
  }
}

// export {}
