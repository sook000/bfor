<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { findUserId } from "@/api/user.js"
const router = useRouter();
const findUser = ref({
  userName: "",
  userTel: "",
});

const isUsePhone = ref(false);
const phoneCheckShow = ref("");
const phoneValidClass = ref("");
const phoneShowClass = ref("");
const phoneCheck = () => {
  const len = findUser.value.userTel.length;
  if (len === 13) {
    const telInput = findUser.value.userTel;
    if (telInput[3] === "-" && telInput[8] === "-") {
      isUsePhone.value = true;
      phoneCheckShow.value = "";
      phoneValidClass.value = "is-valid";
      phoneShowClass.value = "valid-feedback";
    } else {
      isUsePhone.value = false;
      phoneCheckShow.value = "입력 형식을 맞춰주세요";
      phoneValidClass.value = "is-invalid";
      phoneShowClass.value = "invalid-feedback";
    }
  } else {
    isUsePhone.value = false;
    phoneCheckShow.value = "입력 형식을 맞춰주세요(000-0000-0000)";
    phoneValidClass.value = "is-invalid";
    phoneShowClass.value = "invalid-feedback";
  }
};

const moveLogin = () => {
  router.push({ name: "user-login" });
};
const moveFindPwd = () => {
  router.push({ name: "user-findPwd" })
}

const findId = () => {
  // console.log(findUser.value)
  if (!isUsePhone.value) {
    alert("전화번호 형식을 맞춰주세요")
    return;
  }
  findUserId(
    findUser.value,
    (res) => {
      router.push({ name: "user-findId-success", params: { userId: res.data } })
    }, (error) => {
      alert("이름 또는 전화번호를 확인해주세요")
      console.log(error)
    })
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
        <div class="row mb-3">
          <div class="test ms-auto me-auto px-3 d-flex align-items-center justify-content-center">
            <img src="@/assets/register/identification.png" alt="name"
              style="width: 30px; height: 30px; margin-right: 10px" />
            <input type="text" class="form-control" placeholder="이름" required v-model="findUser.userName" />
          </div>
        </div>

        <div class="row mb-5">
          <div class="test ms-auto me-auto px-3 d-flex align-items-center justify-content-center">
            <img src="@/assets/register/smartphone-call.png" alt="phone"
              style="width: 30px; height: 30px; margin-right: 10px" />
            <input type="text" class="form-control" placeholder="전화번호" required @keyup="phoneCheck"
              :class="phoneValidClass" v-model="findUser.userTel" />
          </div>
          <div id="register-password-check-show" class="mt-1 ms-5 w-100 text-start" :class="phoneShowClass"
            style="display: block">
            {{ phoneCheckShow }}
          </div>
        </div>

        <div class="register-modal-btn d-flex flex-column align-items-center">
          <div class="row mb-3 w-100">
            <button type="submit" class="btn btn-outline-info btn-sm register-close" style="width: 100%">
              아이디찾기
            </button>
          </div>
          <div class="d-flex justify-content-between w-100">
            <button type="button" class="btn btn-outline-secondary btn-sm register-close" style="width: calc(50% - 5px)"
              @click="moveFindPwd">
              비밀번호재설정
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
