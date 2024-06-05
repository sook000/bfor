<script setup></script>

<template>
  <div class="container text-center mt-3">
    <!-- <div class="alert alert-info" role="alert">자유롭게 글쓰는 공간</div> -->
    <div class="row justify-content-center mb-5 text-center">
      <div
        class="ms-auto me-auto px-3 col-lg-4 border-bottom border-secondary border-2 d-flex align-items-center justify-content-center">
        <img src="@/assets/speaker.png" alt="freeboard" style="width:30px;height:30px; margin-right: 10px">
        <h2 class="my-3">공지사항</h2>
      </div>
    </div>


    <router-view></router-view>
  </div>
</template>

<style scoped>
mark.sky {
  background: linear-gradient(to top, #54fff9 20%, transparent 30%);
}
</style>
