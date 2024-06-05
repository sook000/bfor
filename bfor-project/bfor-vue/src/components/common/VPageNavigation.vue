<script setup>
import { computed } from "vue";

const props = defineProps({ currentPage: Number, totalPage: Number });
const emit = defineEmits(["pageChange"]);

const navigationSize = parseInt(import.meta.env.VITE_ARTICLE_NAVIGATION_SIZE);

const startPage = computed(() => {
  return parseInt((props.currentPage - 1) / navigationSize) * navigationSize + 1;
});

const endPage = computed(() => {
  let lastPage =
    parseInt((props.currentPage - 1) / navigationSize) * navigationSize + navigationSize;
  return props.totalPage < lastPage ? props.totalPage : lastPage;
});

const endRange = computed(() => {
  return parseInt((props.totalPage - 1) / navigationSize) * navigationSize < props.currentPage;
});

function range(start, end) {
  const list = [];
  for (let i = start; i <= end; i++) list.push(i);
  return list;
  //   return Array(end - start + 1)
  //     .fill()
  //     .map((val, i) => start + i);
}

function onPageChange(pg) {
  console.log(pg + "로 이동!!!");
  emit("pageChange", pg);
}
</script>

<template>
  <div class="row">
    <ul class="pagination justify-content-center" style="color: black">
      <li class="page-item">
        <a class="page-link" @click="onPageChange(1)" style="color: black">&laquo;</a>
      </li>
      <li class="page-item">
        <a
          class="page-link"
          @click="onPageChange(startPage == 1 ? 1 : startPage - 1)"
          style="color: black"
          >&lt;</a
        >
      </li>
      <template v-for="pg in range(startPage, endPage)" :key="pg">
        <li :class="currentPage === pg ? 'page-item active' : 'page-item'">
          <a class="page-link" @click="onPageChange(pg)" style="color: black">{{ pg }}</a>
        </li>
      </template>
      <li class="page-item">
        <a
          class="page-link"
          @click="onPageChange(endRange ? totalPage : endPage + 1)"
          style="color: black"
          >&gt;</a
        >
      </li>
      <li class="page-item">
        <a class="page-link" @click="onPageChange(totalPage)" style="color: black">&raquo;</a>
      </li>
    </ul>
  </div>
</template>

<style scoped>
a {
  cursor: pointer;
}
</style>
