<script setup>
import { ref } from "vue";
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";
const router = useRouter();
import { deleteUser } from "@/api/mypage.js";

import { useMemberStore } from "@/stores/member.js";
const memberStore = useMemberStore();
const { isLogin, isValidToken, userInfo } = storeToRefs(memberStore);

import { useMenuStore } from "@/stores/menu";
const { changeMenuState } = useMenuStore();

const user = ref({
  userId: userInfo.userId,
  userPassword: "",
});
const userDblPassword = ref("");

const isUsePwd = ref(false);
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

const userDelete = () => {
  if (!isUsePwd.value) {
    alert("비밀번호 확인!");
    return;
  }
  if (confirm("정말 탈퇴 하시겠습니까?")) {
    deleteUser(
      user.value,
      (response) => {
        if (response.status == 200) {
          console.log("탈퇴 성공");
          isLogin.value = false;
          userInfo.value = null;
          isValidToken.value = false;
          changeMenuState();

          sessionStorage.removeItem("accessToken");
          sessionStorage.removeItem("refreshToken");

          router.replace({ name: "mypage-home" });
        } else {
          alert("비밀번호를 다시 확인하세요");
        }
      },
      (error) => {
        alert("비밀번호를 다시 확인하세요");
        console.log(error);
      }
    );
  }
};
</script>

<template>
  <div class="col-md-9">
    <div class="card p-4 text-start">
      <div class="border-bottom border-secondary border-3 d-flex align-items-center mb-5">
        <img src="@/assets/mypage.png" alt="mypage" style="width: 30px; height: 30px; margin-right: 10px" />
        <h2 class="">회원 탈퇴</h2>
      </div>

      <form id="form-join" @submit.prevent="userDelete">
        <div class="row mb-5">
          <div class="test ms-auto me-auto px-3 d-flex align-items-center justify-content-center">
            <img src="@/assets/register/lock.png" alt="password"
              style="width: 30px; height: 30px; margin-right: 10px" />
            <input type="password" class="form-control" name="register-password" id="register-password"
              placeholder="*비밀번호를 입력하세요" v-model="user.userPassword" required />
          </div>
        </div>

        <div class="row mb-5">
          <div class="test ms-auto me-auto px-3 d-flex align-items-center justify-content-center">
            <img src="@/assets/register/doublelock.png" alt="doublepassword"
              style="width: 30px; height: 30px; margin-right: 10px" />
            <input type="password" class="form-control" name="register-password-check" id="register-password-check"
              placeholder="*다시 한 번 비밀번호를 입력하세요" required v-model="userDblPassword" :class="dblpwdValidClass"
              @keyup="dblpwdCheck" />
          </div>
          <div style="display: block" id="register-password-check-show" class="mt-1 ms-5 w-100 text-start"
            :class="dblpwdShowClass">
            {{ dblpwdCheckShow }}
          </div>
        </div>

        <div class="register-modal-btn d-flex justify-content-end">
          <button type="submit" id="btn-register-member" class="btn btn-outline-danger btn-sm register-close m-1"
            style="display: block" value="회원탈퇴">
            회원탈퇴
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<style scoped></style>
