import { defineStore } from 'pinia'
import http from "@/http/XMLHttpRequest"
import API from "@/http/API"
import type { User } from "@/type/type";

const TOKEN_NAME = "TOKEN";
const UNSAFE_TOKEN_NAME = "UNSAFE_TOKEN";


//创建VueX对象
export const user = defineStore("user", {
	state: () => {
    let data: {
      token: string | null,
      user: User | undefined,
      unsafeToken: string,
      loading: boolean,
      themeColor: any
    } = {
      token: window.localStorage.getItem(TOKEN_NAME) ? window.localStorage.getItem(TOKEN_NAME) : null,
      unsafeToken: "",
      user: undefined,
      loading: true,
      themeColor: null,
    }
    return data;
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
		async info() {
      if (!this.user) {
        await http.get(API("/user/info")).then(res => {
          this.user = res.data;
          console.log(this.user);
          
        });
      }
      return this.user;
		},
    logout() {
      this.user = null;
      this.token = null;
      window.localStorage.removeItem(TOKEN_NAME);
      window.localStorage.removeItem(UNSAFE_TOKEN_NAME);

    },
    setLoding(lodding:boolean) {
			this.loading = lodding;
		},
		setThemeColor(themeColor:any) {
			this.themeColor = themeColor;
		}
	}
})

export default user