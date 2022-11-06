import { reactive, ref, nextTick, getCurrentInstance, defineComponent } from 'vue'
import { main } from '@/store/main'
import { login as httpLogin } from "@/http/User"
import $router from "@/router/index"
import { FormInst, useMessage } from "naive-ui"

const loginForm = ref<FormInst | null>(null);
const guestLogin = ref(false);
const input = ref(null);
const user = reactive({
  username: "",
  password: ""
});


export const setup = {
  loginForm,
  guestLogin,
  input,
  user
}

export const toggle = () => {
  guestLogin.value = !guestLogin.value;
  nextTick(() => {
    user.username = "";
    user.password = "";
    if (input.value == null) return;
    // @ts-ignore
    input.value.focus();
  });
}

export const checkPwd = () => {
  if (!guestLogin.value) {
    if (user.username.trim() === "") {
      window.$message.warning("用户名不可以为空");
      user.username = "";
      return false;
    } else if (user.username.length > 10) {
      window.$message.warning("用户名长度在10位数以内");
      user.username = "";
      return false;
    }
  } if (user.password.trim() === "") {
    window.$message.warning("密码不可以为空");
    user.password = "";
    return false;
  } else if (user.password.length > 18) {
    window.$message.warning("密码长度在18位数以内");
    user.password = "";
    return false;
  }
  return true;
}

export const login = (url: string) => {
  if (typeof url === "string") {
    // 创建一个子窗口用于登录
    let strWindowFeatures =
      "width=600,height=500,top=100,left=100,menubar=yes,location=yes,resizable=yes,scrollbars=true,status=true";
    window.open(url, "_blank", strWindowFeatures);
    // 创建postMessage监听
    // window.addEventListener("message", receiveMsg);
    return;
  } else if (guestLogin.value === true) {
    if (!checkPwd()) return;
    httpLogin("guest", user.password).then(res => {
      // showPwd.value = false;
      $router.push({ path: "/explorer/local" });
    });
  } else {
    if (!checkPwd()) return;
    httpLogin(user.username, user.password).then(res => {
      $router.push({ path: "/explorer/local" });
    });
  }
}

export default {

}


/*
const receiveMsg = function (e) {
  if (e.origin === $api.SERVER_BASE) {
    let res = JSON.parse(e.data);
    console.log(res)
    // 如果登录成功
    if (res.code == 200) {
      // 添加Token信息
      main().setToken(res.token);
      $router.push({
        name: "Explore",
        params: {
          // 表示是从登录页面过来的
          login: true
        }
      });
      window.$message({
        title: res.msg,
        type: "success"
      });
    } else {
      window.$message({
        title: res.msg,
        type: "warning"
      });
    }
    // 移除 message 事件
    window.removeEventListener("message", receiveMsg);
  }
}*/
