<script setup>
import BoardFormItem from "./item/BoardFormItem.vue";
</script>

<template>
  <div class="container">
    <div class="row justify-content-start mb-3">
      <div class="col-lg-1"></div>
      <div class="col-auto md-auto">
        <h3 class="my-3 bg-light shadow-sm">글쓰기</h3>
        <!-- <h2 class="my-3 py-3 shadow-sm bg-light text-center">
              <mark class="sky">글수정</mark>
            </h2> -->
      </div>
      <div class="col-lg-9"></div>
    </div>
    <!-- <div class="col-lg-10">
      <h2 class="my-3 py-3 shadow-sm bg-light text-center">
        <mark class="sky">글쓰기</mark>
      </h2>
    </div> -->
    <div class="row justify-content-center">
      <div class="col-lg-10 text-start">
        <BoardFormItem type="regist" />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
