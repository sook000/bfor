<script setup>
import { ref } from "vue";
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member.js";
import { useMenuStore } from "@/stores/menu";

const router = useRouter();
const memberStore = useMemberStore();

const { isLogin, isLoginError } = storeToRefs(memberStore);
const { userLogin, getUserInfo } = memberStore;
const { changeMenuState } = useMenuStore();

const loginUser = ref({
  userId: "",
  userPassword: "",
});

const login = async () => {
  console.log("로그인중");
  await userLogin(loginUser.value);
  let token = sessionStorage.getItem("accessToken");
  console.log(token);
  console.log("isLogin: " + isLogin.value);
  if (isLogin.value) {
    getUserInfo(token);
    changeMenuState();
    router.replace("/");
  } else alert("아이디 또는 비밀번호를 확인해주세요!");
};

const moveRegister = () => {
  router.push({ name: "user-join" });
};
const moveFindId = () => {
  router.push({ name: "user-findId" });
};
const moveFindPwd = () => {
  router.push({ name: "user-findPwd" });
};
</script>

<template>
  <div class="d-flex justify-content-center mt-5 mb-5">
    <div class="card sm-5 me-2 p-4" style="width: 45%">
      <div class="row justify-content-center mb-5 text-center">
        <div
          class="ms-auto me-auto px-3 col-lg-5 border-bottom border-secondary border-2 d-flex align-items-center justify-content-center"
        >
          <img
            src="@/assets/enter.png"
            alt="freeboard"
            style="width: 30px; height: 30px; margin-right: 10px"
          />
          <h5 class="my-3">로그인</h5>
        </div>
      </div>

      <form id="form-join" @submit.prevent="login">
        <div class="row mb-3">
          <div class="test ms-auto me-auto px-3 d-flex align-items-center justify-content-center">
            <img
              src="@/assets/register/people.png"
              alt="id"
              style="width: 30px; height: 30px; margin-right: 10px"
            />
            <input
              type="text"
              class="form-control"
              name="register-id"
              id="register-id"
              placeholder="아이디"
              required
              v-model="loginUser.userId"
            />
          </div>
        </div>

        <div class="row mb-5">
          <div class="test ms-auto me-auto px-3 d-flex align-items-center justify-content-center">
            <img
              src="@/assets/register/lock.png"
              alt="password"
              style="width: 30px; height: 30px; margin-right: 10px"
            />
            <input
              type="password"
              class="form-control"
              name="register-password"
              id="register-password"
              placeholder="비밀번호"
              required
              v-model="loginUser.userPassword"
            />
          </div>
        </div>
        <div class="mb-5 text-start" v-if="isLoginError === true">
          <div class="alert alert-danger" role="alert">아이디 또는 비밀번호를 확인해 주세요</div>
        </div>
        <div class="register-modal-btn d-flex flex-column align-items-center">
          <div class="row mb-3 w-100">
            <button
              type="submit"
              class="btn btn-outline-info btn-sm register-close"
              style="width: 100%"
              value="로그인"
            >
              로그인
            </button>
          </div>
          <div class="d-flex justify-content-between w-100">
            <button
              type="button"
              class="btn btn-outline-secondary btn-sm register-close"
              style="width: 32%"
              value="로그인"
              @click="moveRegister"
            >
              회원가입
            </button>
            <button
              type="button"
              class="btn btn-outline-secondary btn-sm register-close"
              style="width: 32%"
              @click="moveFindId"
            >
              아이디찾기
            </button>
            <button
              type="button"
              class="btn btn-outline-secondary btn-sm register-close"
              style="width: 32%"
              @click="moveFindPwd"
            >
              비밀번호재설정
            </button>
          </div>
        </div>
      </form>
    </div>
  </div>
  <div class="mt-5" style="height: 300px"></div>
</template>

<style scoped></style>
