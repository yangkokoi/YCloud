<script setup lang="ts">
import { zhCN, dateZhCN } from "naive-ui";
// This starter template is using Vue 3 <script setup> SFCs
// Check out https://v3.vuejs.org/api/sfc-script-setup.html#sfc-script-setup
// import HelloWorld from './components/HelloWorld.vue'
const isMobile = window.sessionStorage.getItem("isMobile");

const themeOverrides = {
  common: {
    // primaryColor: '#FF0000'
  },
  Button: {
    // textColor: '#FFFFFF'
  },
  Menu: {
    borderRadius: "10px",
    color: "#03885B",
    fontSize: "16px",
    itemTextColor: "#5BC8A4",
    itemTextColorHover: "#FFF",
    itemTextColorActive: "#FFF",
    itemTextColorActiveHover: "#FFF",
    itemColorHover: "#009A64",
    itemColorActive: "#009A64",
    itemColorActiveHover: "#009A64"

  }
};

</script>

<template>
  <template v-if="isMobile">
    <router-view style="height: 100%;" />
  </template>
  <template v-else>
    <n-config-provider
      id="appWindow"
      style="height: 100%"
      :theme-overrides="themeOverrides"
      :locale="zhCN"
      :date-locale="dateZhCN"
    >
      <n-message-provider>
        <n-dialog-provider>
          <n-loading-bar-provider>
            <router-view style="height: 100%" />
          </n-loading-bar-provider>
        </n-dialog-provider>
      </n-message-provider>
    </n-config-provider>
  </template>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  background-color: #f0f0f0;
  height: 100%;
}

</style>
