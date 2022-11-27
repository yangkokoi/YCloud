<script setup lang="ts">
import { onMounted } from "vue";
// import { RouterLink } from "vue-router";
// import type { MenuOption } from "naive-ui";
import Footer from "@/components/Footer.vue";
import user from "@/store/user";

// import { useMessage, useDialog, useLoadingBar } from "naive-ui";
// window.$message = useMessage();
// window.$dialog = useDialog();
// window.$loadingBar = useLoadingBar();

onMounted(() => {
  // this.$loading().close();
  user().info();
});

// const appHeight = document.getElementById("appWindow")!.clientHeight;
// console.log(appHeight)
// const menuTheme = {
//   heightMedium: '40px',
//   textColor: 'rgba(24, 127, 231, 1)'
// }
// // let activeMenu:string = ref<string>("Home");
// let activeMenu: any = ref<string>("Home");
// const menuOptions: MenuOption[] = [
//   {
//     // label: 'Home',
//     label: () => h(
//       RouterLink,
//       {
//         to: { name: 'Home', }
//       },
//       { default: () => 'Home' }
//     ),
//     key: 'Home',
//   },
//   {
//     // label: 'Local-Store',
//     label: () => h(
//       RouterLink,
//       {
//         to: { name: 'Explorer', params: { name: "local", type: "LOCAL" } }
//       },
//       { default: () => '本地存储' }
//     ),
//     key: 'Explorer',
//   },
//   {
//     label: () => h(
//       RouterLink,
//       {
//         to: { name: 'Explorer', params: { name: "阿里云存储", type: "ali" } }
//       },
//       { default: () => '阿里云存储' }
//     ),
//     key: '2',
//   },
//   {
//     label: 'Settings',
//     key: 'Settings',
//   },
// ]

</script>

<template>
  <div class="warpper">
    <!-- 网站主体内容 -->
    <div
      id="main"
      style="padding: 20px 20px 0 20px;"
    >
      <router-view style="height: 100%" />
    </div>

    <!-- 底部版权信息 -->
    <footer
      bordered
      style="height: 60px; display: flex;align-items: center; justify-content: center"
    >
      <component :is="Footer" />
    </footer>
  </div>
</template>

<style scoped>
.n-layout-sider {
  color: #fff;
  background-color: #03885b;
  border-radius: 25px;
  font-weight: bold;
  height: 100%;
}

.warpper {
  display: flex;flex-direction: column;height: 100%;
  background-color: #fafafc;
}

#main {
  height: calc(100% - 60px);
  background-color: #FFF;
}
</style>
