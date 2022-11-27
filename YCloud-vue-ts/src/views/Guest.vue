<template>
  
</template>

<script setup lang="ts">
import { ref } from "vue"
import { useRouter } from 'vue-router';
import { main } from '@/store/user'
import { login } from "@/http/User"

const $router = useRouter();


</script>

<style scoped>
  .home-box {
    height: 100%;
    display: flex;
    align-items: center;
  }
</style>