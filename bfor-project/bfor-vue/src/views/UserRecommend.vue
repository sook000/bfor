<script setup>
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";
// import { computed } from "vue";
// import { useRoute } from "vue-router";
// const route = useRoute();
// const userId = computed(() => route.params.userId);

const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);
</script>

<template>
  <div class="row justify-content-center text-center">
    <div
      class="ms-auto me-auto px-3 col-lg-4 border-bottom border-secondary border-2 d-flex align-items-center justify-content-center"
    >
      <img
        src="@/assets/recommendation.png"
        alt="freeboard"
        style="width: 30px; height: 30px; margin-right: 10px"
      />
      <h2 class="my-3">추천페이지</h2>
    </div>
  </div>
  <div class="container-fluid text-center mt-3 p-5">
    <div class="row">
      <!-- 메인 왼쪽 콘텐츠 -->
      <div class="col-md-3">
        <div class="card sm-5 me-2 p-4">
          <div class="mx-auto mt-2 mb-3">
            <img
              src="https://source.unsplash.com/random/250x250/?food"
              class="img-fluid rounded-4"
              alt="..."
              style="width: 200px"
            />
          </div>
          <h2 id="userid">{{ userInfo.userId }}</h2>

          <div class="card-body text-start">
            <div class="list-group list-group-flush">
              <div class="list-group-item list-group-item-action list-group-item-light"></div>
            </div>
          </div>
        </div>
      </div>
      <router-view></router-view>
    </div>
  </div>
</template>

<style scoped></style>
