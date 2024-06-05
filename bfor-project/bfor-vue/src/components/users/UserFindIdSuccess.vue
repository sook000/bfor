<script setup>
import { useRouter, useRoute } from "vue-router";
const router = useRouter();
const route = useRoute();

const { userId } = route.params;


const moveLogin = () => {
  router.push({ name: "user-login" });
};
const moveFindPwd = () => {
  router.push({ name: "user-findPwd" })
}


</script>

<template>
  <div class="d-flex justify-content-center mt-5 mb-5">
    <div class="card sm-5 me-2 p-4" style="width: 45%">
      <div class="row justify-content-center mb-5 text-center">
        <div
          class="ms-auto me-auto px-3 col-lg-6 border-bottom border-secondary border-2 d-flex align-items-center justify-content-center">
          <img src="@/assets/register/people.png" alt="freeboard"
            style="width: 30px; height: 30px; margin-right: 10px" />
          <h5 class="my-3">아이디찾기</h5>
        </div>
      </div>

      <form id="form-join" @submit.prevent="findId">


        <div class="row mb-5">
          <div class="card">
            <div class="card-body">
              <p class="card-text">당신의 아이디는 "{{ userId }}"입니다.</p>
            </div>
          </div>
        </div>

        <div class="register-modal-btn d-flex flex-column align-items-center">
          <div class="d-flex justify-content-between w-100">
            <button type="button" class="btn btn-outline-secondary btn-sm register-close" style="width: calc(50% - 5px)"
              @click="moveFindPwd">
              비밀번호찾기
            </button>
            <button type="button" class="btn btn-outline-secondary btn-sm register-close" style="width: calc(50% - 5px)"
              @click="moveLogin">
              로그인하러 가기
            </button>
          </div>
        </div>
      </form>
    </div>
  </div>
  <div class="mt-5" style="height: 300px"></div>
</template>

<style scoped></style>
