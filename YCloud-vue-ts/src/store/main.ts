import { defineStore } from 'pinia'
import http from "@/http/XMLHttpRequest"
import API from "@/http/API"

const TOKEN_NAME = "TOKEN";
const UNSAFE_TOKEN_NAME = "UNSAFE_TOKEN";

//创建VueX对象
export const main = defineStore("main", {
	state: () => {
    return {
      token: window.localStorage.getItem(TOKEN_NAME) ? window.localStorage.getItem(TOKEN_NAME) : null,
      unsafeToken: "",
      user: null,
      loading: true,
      themeColor: null,
    }
	},
	actions: {
    login(data: any) {
      return http.post(API("/login"), JSON.stringify(data)).then(res => {
        console.log(res);
        
        // 添加Token信息
        this.token = res.token;
        window.localStorage.setItem(TOKEN_NAME, res.token);
        http.message.success("登录成功!");
      });
    },
    async getUnsafeToken() {
      if (!this.unsafeToken) {
        console.log("获取 unsafe token 中");
        await http.post(API("/unsafeToken")).then(res => {
          this.unsafeToken = res.token;
        });
      }
      return this.unsafeToken;
    },
		setUser(user:any) {
			this.user = user;
		},
    logout() {
      this.user = null;
      this.token = null;
      window.localStorage.removeItem(TOKEN_NAME);
    },
    setLoding(lodding:boolean) {
			this.loading = lodding;
		},
		setThemeColor(themeColor:any) {
			this.themeColor = themeColor;
		}
	}
})

export default main