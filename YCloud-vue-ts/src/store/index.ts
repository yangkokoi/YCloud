import { createPinia, defineStore } from 'pinia'


const TOKEN_NAME = "TOKEN";

//创建VueX对象
const main = defineStore("main", {
	state: () => {
    return {
      token: window.localStorage.getItem(TOKEN_NAME) ? window.localStorage.getItem(TOKEN_NAME) : null,
      user: null,
      loading: true,
      themeColor: null,
    }
	},
	actions: {
    setToken(token:string) {
    	this.token = token;
      window.localStorage.setItem(TOKEN_NAME, token);
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

export { main }

export default createPinia()
