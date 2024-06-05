<script setup>
import { ref } from "vue";
import { useRouter, useRoute } from "vue-router";
import { modifyPwd } from "@/api/user.js"
const router = useRouter();
const route = useRoute();

const { userId } = route.params;
const user = ref({
  userId: userId,
  userPassword: "",
})
const userDblPassword = ref("");

const isUsePwd = ref(false);
const pwdCheckShow = ref("");
const pwdValidClass = ref("");
const pwdShowClass = ref("");

const pwdCheck = () => {
  const len = user.value.userPassword.length;
  if (len < 7 || len > 12) {
    isUsePwd.value = false;
    pwdCheckShow.value = "비밀번호는 7자 이상 12자 이하입니다.";
    pwdValidClass.value = "is-invalid";
    pwdShowClass.value = "invalid-feedback";
  } else {
    isUsePwd.value = false;
    pwdCheckShow.value = "";
    pwdValidClass.value = "is-valid";
    pwdShowClass.value = "valid-feedback";
  }
};
const dblpwdCheckShow = ref("");
const dblpwdValidClass = ref("");
const dblpwdShowClass = ref("");
const dblpwdCheck = () => {
  if (user.value.userPassword === userDblPassword.value) {
    isUsePwd.value = true;
    dblpwdCheckShow.value = "";
    dblpwdValidClass.value = "is-valid";
    dblpwdShowClass.value = "valid-feedback";
  } else {
    isUsePwd.value = false;
    dblpwdCheckShow.value = "비밀번호가 일치하지 않습니다.";
    dblpwdValidClass.value = "is-invalid";
    dblpwdShowClass.value = "invalid-feedback";
  }
};

const updatePwd = () => {
  if (!isUsePwd.value) {
    alert("비밀번호 확인!");
    return;
  }
  //비밀번호 길이 7~12로 안했지만 비밀번호 확인이랑 일치하는 경우
  if (user.value.userPassword.length < 7 || user.value.userPassword.length > 12) {
    alert("비밀번호 확인!");
    return;
  }
  modifyPwd(
    user.value,
    (response) => {
      let msg = "비밀번호 재설정 시 문제가 발생했습니다"
      if (response.status == 200) msg = "비밀번호 재설정이 완료되었습니다"
      alert(msg)
      router.push({ name: "user-login" })
    }, (err) => console.log(err)
  )
}


</script>

<template>
  <div class="d-flex justify-content-center mt-5 mb-5">
    <div class="card sm-5 me-2 p-4" style="width: 50%">
      <div class="row justify-content-center mb-5 text-center">
        <div
          class="ms-auto me-auto px-3 col-lg-7 border-bottom border-secondary border-2 d-flex align-items-center justify-content-center">
          <img src="@/assets/register/lock.png" alt="freeboard" style="width: 30px; height: 30px; margin-right: 10px" />
          <h5 class="my-3">비밀번호 재설정</h5>
        </div>
      </div>


      <form id="form-join" @submit.prevent="updatePwd">


        <div class="row mb-5">
          <div class="test ms-auto me-auto px-3 d-flex align-items-center justify-content-center">
            <img src="@/assets/register/lock.png" alt="password"
              style="width: 30px; height: 30px; margin-right: 10px" />
            <input type="password" class="form-control" name="register-password" id="register-password"
              placeholder="새로운 비밀번호를 입력하세요" v-model="user.userPassword" required :class="pwdValidClass"
              @keyup="pwdCheck" />
          </div>
          <div style="display: block" id="register-password-show" class="mt-1 ms-5 w-100 text-start"
            :class="pwdShowClass">
            {{ pwdCheckShow }}
          </div>
        </div>

        <div class="row mb-5">
          <div class="test ms-auto me-auto px-3 d-flex align-items-center justify-content-center">
            <img src="@/assets/register/doublelock.png" alt="doublepassword"
              style="width: 30px; height: 30px; margin-right: 10px" />
            <input type="password" class="form-control" name="register-password-check" id="register-password-check"
              placeholder="다시 한 번 비밀번호를 입력하세요" required v-model="userDblPassword" :class="dblpwdValidClass"
              @keyup="dblpwdCheck" />
          </div>
          <div style="display: block" id="register-password-check-show" class="mt-1 ms-5 w-100 text-start"
            :class="dblpwdShowClass">
            {{ dblpwdCheckShow }}
          </div>
        </div>


        <div class="register-modal-btn d-flex flex-column align-items-center">
          <div class="row mb-3 w-100">
            <button type="submit" id="btn-register-member" class="btn btn-outline-info btn-sm register-close"
              style="width: 100%" value="로그인">
              비밀번호 재설정하기
            </button>
          </div>

        </div>
      </form>
    </div>
  </div>
  <div class="mt-5" style="height: 300px"></div>
</template>

<style scoped></style>
