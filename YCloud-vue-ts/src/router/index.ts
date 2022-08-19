import store from '@/store'
import { createRouter, createWebHistory } from 'vue-router'

// 解决ElementUI导航栏中的vue-router在3.0版本以上重复点菜单报错问题
// const originalPush = Router.prototype.push
// Router.prototype.push = function push(location) {
//   return originalPush.call(this, location).catch(err => err);
// }


// 1. 定义路由组件.
// 也可以从其他文件导入
// const Home = { template: '<div>Home</div>' }
// const About = { template: '<div>About</div>' }

// 2. 定义一些路由
// 每个路由都需要映射到一个组件。
// 我们后面再讨论嵌套路由。
const routes = [
  { path: "/", name: "Index", component: () => import("@/views/Index.vue"), children: [
      { path: "/", name: "Home", component: () => import("@/views/Home.vue") },
      { path: "/explorer/:name", name: "Explorer", component: () => import("@/views/Explorer/Explorer.vue") }
    ]
  },
  { path: "/login", name: "Login", component: () => import("@/views/Login.vue") },
  { path: "/test", name: "Test", component: () => import("@/views/Test.vue") },
]

// 3. 创建路由实例并传递 `routes` 配置
// 你可以在这里输入更多的配置，但我们在这里
// 暂时保持简单
const router = createRouter({
  // 4. 内部提供了 history 模式的实现。为了简单起见，我们在这里使用 hash 模式。
  history: createWebHistory(),
  routes, // `routes: routes` 的缩写
})


// router fitter
router.beforeEach((to, from, next) => {
  // 如果没有登录
  next();
  // if ( !store.state.token ) {
  //   // 如果不是登录页面
  //   if (to.name !== "Login") next({ name: "Login" });
  //   else next();
  // }
  // else {
  //   if (to.name == "Login") { // 有登录信息，将保持原页面不进行跳转
  //     // 不要太快了，不然体验不好
  //     console.log("已登录了")
  //     setTimeout(() => {
  //       next(false);
  //     }, 1500);
  //   } else {
  //     if (to.path == null || to.name == null) {
  //       next({ name: "NotFound" });
  //     } else {
  //       // 添加Tabs
  //       next();
  //     }
  //   }
  // }
});

export default router
