import { defineStore } from 'pinia';
import API from "@/http/API"
import { instance } from "@/http/Share";

const TOKEN_NAME = "SHARE_TOKEN";

//创建VueX对象
export const sharePinia = defineStore("share", {
    state: () => {
        return {
            token: window.sessionStorage.getItem(TOKEN_NAME) ? window.sessionStorage.getItem(TOKEN_NAME) : null,
            unsafeToken: "",
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
        async getUnsafeToken() {
            if (!this.unsafeToken) {
                console.log("获取 unsafe token 中 (share)");
                await instance.post(API("/unsafeToken"), null).then(res => {
                    console.log(res.token);
                    this.unsafeToken = res.token;
                });
            }
            return this.unsafeToken;
        },
    }
})

export default sharePinia