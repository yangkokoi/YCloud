import { defineStore } from 'pinia'

const TOKEN_NAME = "SHARE_TOKEN";

//创建VueX对象
export const sharePinia = defineStore("share", {
    state: () => {
        return {
            token: window.sessionStorage.getItem(TOKEN_NAME) ? window.sessionStorage.getItem(TOKEN_NAME) : null,
        }
    },
    actions: {
        setToken(uuid: string, token: string) {
            this.token = token;
            window.sessionStorage.setItem(TOKEN_NAME, token);
        },
        clearToken() {
            this.token = null;
            window.sessionStorage.removeItem(TOKEN_NAME);
        },
    }
})

export default sharePinia