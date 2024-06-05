<script setup>
import { defineProps } from "vue";
const props = defineProps({ travelInfo: Object });
</script>
<template>
  <div class="col-3">
    <img class="total-best-item col-5" src="@/assets/img/10d@2x.png" />
    <span class="col-5">
      {{ props.travelInfo.title }}
    </span>
  </div>
</template>

<style scoped></style>
