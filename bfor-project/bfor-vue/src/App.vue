<script setup>
import { RouterView } from "vue-router";
import TheHeadingNavbar from "@/components/layout/TheHeadingNavbar.vue";
import TheFooter from "@/components/layout/TheFooter.vue";
</script>

<template>
  <TheHeadingNavbar />
  <div class="divider mt-4 mb-4"></div>
  <div class="m-0">
    <router-view></router-view>
  </div>
  <TheFooter />
</template>

<style scoped></style>
