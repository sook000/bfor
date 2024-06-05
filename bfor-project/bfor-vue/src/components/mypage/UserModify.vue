<script setup>
import { onMounted, ref } from "vue";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";
import { modifyUser } from "@/api/mypage.js";
import { getSido, getGugun } from "@/api/user.js";
import { useRouter } from "vue-router";
const router = useRouter();

const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);

onMounted(() => {
  getRegionBig();
  getRegionSmall();
});
const regionBig = ref([]);
const getRegionBig = () => {
  getSido(
    ({ data }) => {
      //   console.log(data);
      regionBig.value = data;
    },
    (error) => console.log(error)
  );
};
const user = ref({
  userId: userInfo.userId,
  userPassword: "",
  userName: userInfo.userName,
  userTel: userInfo.userTel,
  userBirth: userInfo.userBirth,
  userEmailId: userInfo.userEmailId,
  userEmailDomain: userInfo.userEmailDomain,
  regdate: userInfo.regdate,
  profileImage: userInfo.profileImage,
  sidoCode: userInfo.sidoCode,
  gugunCode: userInfo.gugunCode,
});
const userDblPassword = ref("");

const nameCheckShow = ref("");
const nameValidClass = ref("");
const nameShowClass = ref("");
const nameCheck = () => {
  const len = user.value.userName.length;
  if (len > 0) {
    nameCheckShow.value = "";
    nameShowClass.value = "valid-feedback";
    nameValidClass.value = "is-valid";
  } else {
    nameCheckShow.value = "이름을 입력해 주세요";
    nameValidClass.value = "is-invalid";
    nameShowClass.value = "invalid-feedback";
  }
};

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

const isUsePhone = ref(true);
const phoneCheckShow = ref("");
const phoneValidClass = ref("");
const phoneShowClass = ref("");
const phoneCheck = () => {
  const len = user.value.userTel.length;
  if (len === 13) {
    const telInput = user.value.userTel;
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

const emailIdValidClass = ref("");
const emailIdCheck = () => {
  const len = user.value.userEmailId.length;
  if (len > 0) {
    emailIdValidClass.value = "is-valid";
  } else {
    emailIdValidClass.value = "";
  }
};

const emailDomainValidClass = ref("");
const emailDomainCheck = () => {
  const len = user.value.userEmailDomain.length;
  if (len > 0) {
    emailDomainValidClass.value = "is-valid";
  } else {
    emailDomainValidClass.value = "";
  }
};

const regionSmall = ref({});
const getRegionSmall = () => {
  const userSido = user.value.sidoCode;
  getGugun(
    userSido,
    ({ data }) => {
      regionSmall.value = data;
    },
    (error) => console.log(error)
  );
};

const fileUpload = () => {
  console.log(user.value.profileImage);
  console.log(user.value.profileImage.length);
};

const userUpdate = () => {
  if (!isUsePwd.value) {
    alert("비밀번호 확인!");
    return;
  }
  //비밀번호 길이 7~12로 안했지만 비밀번호 확인이랑 일치하는 경우
  if (user.value.userPassword.length < 7 || user.value.userPassword.length > 12) {
    alert("비밀번호 확인!");
    return;
  }
  if (!isUsePhone.value) {
    alert("전화번호 확인!");
    return;
  }
  if (confirm("정보 수정을 하시겠습니까?")) {
    console.log("a");
    modifyUser(
      user.value,
      (response) => {
        let msg = "유저 정보 수정 시 문제가 발생했습니다";
        if (response.status == 200) msg = "정보 수정이 완료되었습니다";
        alert(msg);
        router.push({ name: "mypage-home" });
      },
      (error) => console.log(error)
    );
  }
};
</script>

<template>
  <div class="col-md-9">
    <div class="card p-4 text-start">
      <div class="border-bottom border-secondary border-3 d-flex align-items-center mb-5">
        <img src="@/assets/mypage.png" alt="mypage" style="width: 30px; height: 30px; margin-right: 10px" />
        <h2 class="">정보 수정</h2>
      </div>

      <form id="form-join" @submit.prevent="userUpdate">
        <div class="row mb-5">
          <div class="ms-auto me-auto px-3 d-flex align-items-center justify-content-center">
            <img src="@/assets/register/identification.png" alt="name"
              style="width: 30px; height: 30px; margin-right: 10px" />
            <input type="text" class="form-control" placeholder="*이름을 입력하세요" required v-model="user.userName"
              :class="nameValidClass" @keyup="nameCheck" />
          </div>
          <div style="display: block" class="mt-1 ms-5 w-100 text-start" :class="nameShowClass">
            {{ nameCheckShow }}
          </div>
        </div>
        <div class="row mb-5">
          <div class="test ms-auto me-auto px-3 d-flex align-items-center justify-content-center">
            <img src="@/assets/register/people.png" alt="id" style="width: 30px; height: 30px; margin-right: 10px" />
            <input type="text" class="form-control" name="register-id" id="register-id" placeholder="*아이디를 입력하세요"
              disabled="true" v-model="user.userId" @keyup="idCheck" />
          </div>
        </div>

        <div class="row mb-5">
          <div class="test ms-auto me-auto px-3 d-flex align-items-center justify-content-center">
            <img src="@/assets/register/lock.png" alt="password"
              style="width: 30px; height: 30px; margin-right: 10px" />
            <input type="password" class="form-control" name="register-password" id="register-password"
              placeholder="*비밀번호를 입력하세요" v-model="user.userPassword" required :class="pwdValidClass"
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
              placeholder="*다시 한 번 비밀번호를 입력하세요" required v-model="userDblPassword" :class="dblpwdValidClass"
              @keyup="dblpwdCheck" />
          </div>
          <div style="display: block" id="register-password-check-show" class="mt-1 ms-5 w-100 text-start"
            :class="dblpwdShowClass">
            {{ dblpwdCheckShow }}
          </div>
        </div>

        <div class="row mb-5">
          <div class="test ms-auto me-auto px-3 d-flex align-items-center justify-content-center">
            <img src="@/assets/register/smartphone-call.png" alt="phone"
              style="width: 30px; height: 30px; margin-right: 10px" />
            <input type="text" class="form-control" name="phone" id="phone" placeholder="*전화번호를 입력하세요(000-0000-0000)"
              required v-model="user.userTel" :class="phoneValidClass" @keyup="phoneCheck" />
          </div>
          <div id="register-password-check-show" class="mt-1 ms-5 w-100 text-start" :class="phoneShowClass"
            style="display: block">
            {{ phoneCheckShow }}
          </div>
        </div>

        <div class="row mb-5">
          <div class="test ms-auto me-auto px-3 d-flex align-items-center justify-content-center">
            <img src="@/assets/register/calendar.png" alt="birth"
              style="width: 25px; height: 25px; margin-right: 15px" />
            <input type="date" class="form-control" name="birth" id="birth" placeholder="생일을 입력하세요"
              v-model="user.userBirth" />
          </div>
          <!-- <div id="register-password-check-show" class="mt-1"></div> -->
        </div>

        <div class="row mb-5">
          <div class="test ms-auto me-auto px-3 d-flex align-items-center justify-content-center">
            <img src="@/assets/register/mail.png" alt="email" style="width: 25px; height: 25px; margin-right: 15px" />
            <div class="input-group">
              <input type="text" class="form-control" id="register-email-id" name="register-email-id"
                placeholder="*이메일 아이디" required v-model="user.userEmailId" :class="emailIdValidClass"
                @keyup="emailIdCheck" />
              <div class="input-group-append">
                <span class="input-group-text">@</span>
              </div>
              <select class="form-select" id="register-email-address" name="register-email-address"
                aria-label="Floating label select example" required v-model="user.userEmailDomain"
                :class="emailDomainValidClass" @change="emailDomainCheck">
                <option value="" selected>이메일 선택</option>
                <option value="google.com">google.com</option>
                <option value="naver.com">naver.com</option>
                <option value="daum.net">daum.net</option>
              </select>
            </div>
          </div>
          <!-- <div id="register-password-check-show" class="mt-1"></div> -->
        </div>

        <div class="row mb-5">
          <div class="test ms-auto me-auto px-3 d-flex align-items-center justify-content-center">
            <img src="@/assets/register/map.png" alt="map" style="width: 25px; height: 25px; margin-right: 15px" />
            <div class="input-group">
              <select class="form-select me-1" id="register-region-big" name="register-region-big"
                aria-label="Floating label select example" v-model="user.sidoCode" @change="getRegionSmall">
                <option selected value="">시도 선택</option>
                <option v-for="region in regionBig" :key="region.sidoCode" :value="region.sidoCode">
                  {{ region.sidoName }}
                </option>
              </select>
              <select class="form-select ms-1" id="register-region-small" name="register-region-small"
                aria-label="Floating label select example" v-model="user.gugunCode">
                <option selected value="">구군선택</option>
                <option v-for="region in regionSmall" :key="region.gugunCode" :value="region.gugunCode">
                  {{ region.gugunName }}
                </option>
              </select>
            </div>
          </div>
          <!-- <div id="register-password-check-show" class="mt-1"></div> -->
        </div>

        <div class="row mb-5">
          <div class="test ms-auto me-auto px-3 d-flex align-items-center justify-content-center">
            <img src="@/assets/register/face-id.png" alt="picture"
              style="width: 30px; height: 30px; margin-right: 10px" />

            <input type="file" class="form-control" id="inputGroupFile02" placeholer="프로필 사진을 올리세요"
              @change="fileUpload" />
          </div>
          <!-- <div id="register-password-check-show" class="mt-1"></div> -->
        </div>

        <div class="register-modal-btn d-flex justify-content-end">
          <button type="submit" id="btn-register-member" class="btn btn-outline-info btn-sm register-close m-1"
            style="display: block" value="회원가입">
            정보 수정
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<style scoped></style>
