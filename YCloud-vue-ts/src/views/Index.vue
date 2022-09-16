<script setup lang="ts">
import { onMounted, ref, h } from 'vue'
import { RouterLink } from 'vue-router'
import { useMessage, useDialog } from 'naive-ui'
import type { MenuOption } from 'naive-ui'
import Footer from '/src/components/Footer.vue'

window.$message = useMessage();
window.$dialog = useDialog();

onMounted(() => {
  // this.$loading().close();
  console.log()
})


const appHeight = document.getElementById("appWindow")!.clientHeight;
console.log(appHeight)
const menuTheme = {
  heightMedium: '40px',
  textColor: 'rgba(24, 127, 231, 1)'
}
// let activeMenu:string = ref<string>("Home");
let activeMenu: any = ref<string>("Home");
const menuOptions: MenuOption[] = [
  {
    // label: 'Home',
    label: () => h(
      RouterLink,
      {
        to: { name: 'Home', }
      },
      { default: () => 'Home' }
    ),
    key: 'Home',
  },
  {
    // label: 'Local-Store',
    label: () => h(
      RouterLink,
      {
        to: { name: 'Explorer', params: { name: "local", type: "LOCAL" } }
      },
      { default: () => '本地存储' }
    ),
    key: 'Explorer',
  },
  {
    label: () => h(
      RouterLink,
      {
        to: { name: 'Explorer', params: { name: "阿里云存储", type: "ali" } }
      },
      { default: () => '阿里云存储' }
    ),
    key: '2',
  },
  {
    label: 'Settings',
    key: 'Settings',
  },
]

function handleUpdateValue(key: string, item: MenuOption) {
  activeMenu.value = key;
}
</script>

<template>
  <n-layout
    content-style="display: flex;flex-direction: column"
  >

    <!-- 网站主体内容 -->
    <n-layout id="main" style="padding: 20px">
      <router-view style="height: 100%"></router-view>
    </n-layout>

    <!-- 底部版权信息 -->
    <n-layout-footer
      bordered
      style="height: 60px; display: flex;align-items: center; justify-content: center"
    >
      <component :is="Footer"></component>
    </n-layout-footer>
  </n-layout>
</template>

<style scoped>
.n-layout-sider {
  color: #fff;
  background-color: #03885b;
  border-radius: 25px;
  font-weight: bold;
  height: 100%;
}
.n-layout {
  height: 100%;
}

</style>