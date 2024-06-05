<script setup></script>

<template>
  <div class="container text-center mt-3">


    <div class="row justify-content-center mb-5 text-center">
      <div
        class="ms-auto me-auto px-3 col-lg-4 border-bottom border-secondary border-2 d-flex align-items-center justify-content-center">
        <img src="@/assets/clipboard.png" alt="freeboard" style="width:30px;height:30px; margin-right: 10px">
        <h2 class="my-3">자유게시판</h2>
      </div>
    </div>


    <div>
      <router-view></router-view>
    </div>

  </div>
</template>

<style scoped></style>
