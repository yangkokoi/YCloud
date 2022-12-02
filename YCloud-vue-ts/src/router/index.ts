import { user } from "@/store/user"
import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'


// 解决ElementUI导航栏中的vue-router在3.0版本以上重复点菜单报错问题
// const originalPush = Router.prototype.push
// Router.prototype.push = function push(location) {
//   return originalPush.call(this, location).catch(err => err);
// }


// 1. 定义路由组件.
// 也可以从其他文件导入
// const Home = { template: '<div>Home</div>' }
// const About = { template: '<div>About</div>' }

const isMobile = window.sessionStorage.getItem("isMobile");
// const view = {
//   index: () => isMobile ? import("@/views/Index-m.vue") : import("@/views/Index.vue"),
//   home: () => isMobile ? import("@/views/Home/Home-m.vue") : import("@/views/Home/Home.vue"),
//   login: () => isMobile ? import("@/views/Login/Login-m.vue") : import("@/views/Login/Login.vue"),
//   explorer: () => isMobile ? import("@/views/explorer/Explorer-m.vue") : import("@/views/explorer/Explorer.vue"),
//   mine: () => isMobile ? import("@/views/mine/Mine-m.vue") : import("@/views/mine/Mine.vue"),
// };

const routes: {
  [key: string]: RouteRecordRaw[]
} = {
  pc: [
    {
      path: "/", name: "Index", component: () => import("@/views/Index.vue"), children: [
        { path: "/", name: "Home", component: () => import("@/views/Home/Home.vue"), redirect: "/explorer/local", meta: { auth: false }, children: [] },
        { path: "/explorer/:name?", name: "Explorer", component: () => import("@/views/explorer/Explorer.vue") },
        { path: "/mine", name: "Mine", component: () => import("@/views/mine/Mine.vue") },
      ]
    },
    { path: "/login", name: "Login", component: () => import("@/views/Login/Login.vue"), props: false },
    { path: "/share/:id", name: "Share", component: () => import("@/views/share/Share.vue"), meta: { auth: false } },
    { path: "/test", name: "Test", component: () => import("@/views/Test.vue") },
    { path: "/:pathMatch(.*)*", name: "NotFound", component: () => import("@/views/error/404.vue") }
  ],
  mobile: [
    {
      path: "/", name: "Index", component: () => import("@/views/Index-m.vue"), children: [
        { path: "/", name: "Home", component: () => import("@/views/Home/Home-m.vue"), meta: { auth: false } },
        { path: "/explorer/:name?", name: "Explorer", component: () => import("@/views/explorer/Explorer-m.vue") },
        { path: "/mine", name: "Mine", component: () => import("@/views/mine/Mine-m.vue") },
      ]
    },
    { path: "/login", name: "Login", component: () => import("@/views/Login/Login-m.vue"), props: false },
    { path: "/preview/:id", name: "Preview", component: () => import("@/views/preview/Preview-m.vue") },
    { path: "/share/:id", name: "Share", component: () => import("@/views/share/Share.vue"), meta: { auth: false } },
    { path: "/:pathMatch(.*)*", name: "NotFound", component: () => import("@/views/error/404.vue") }
  ]
}

// 3. 创建路由实例并传递 `routes` 配置
// 你可以在这里输入更多的配置，但我们在这里
// 暂时保持简单
const router = createRouter({
  // 4. 内部提供了 history 模式的实现。为了简单起见，我们在这里使用 hash 模式。
  history: createWebHistory(),
  routes: isMobile ? routes.mobile : routes.pc, // `routes: routes` 的缩写
});

// router fitter
router.beforeEach((to, from, next) => {
  // console.log(to);
  for (let el of to.matched) {
    if (el.meta.auth === false) {
      next();
      return;
    }
  }
  const store = user();
  // 如果没有登录
  if (!store.token) {
    // 如果不是登录页面
    if (to.name !== "Login") next({ name: "Login" });
    else next();
  }
  else {
    if (to.name == "Login") { // 有登录信息，将保持原页面不进行跳转
      // 不要太快了，不然体验不好
      console.log("已登录了")
      next(false);
      // setTimeout(() => {
      // }, 1500);
    } else {
      if (to.path === null || to.name === null) {
        next({ name: "NotFound" });
      } else {
        // 添加Tabs
        next();
      }
    }
  }
});

export default router
