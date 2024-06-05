<script setup>
import { onMounted, ref } from "vue";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";
import { getSidoGugun } from "@/api/mypage.js";

const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);

const sidoCode = {
  sidoCode: userInfo.sidoCode,
  gugunCode: userInfo.gugunCode,
};
const sigunName = ref({});
onMounted(() => {
  getSidoGugun(
    sidoCode,
    ({ data }) => {
      sigunName.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
});
</script>

<template>
  <div class="col-md-9">
    <div class="card p-4 text-start">
      <div class="border-bottom border-secondary border-3 d-flex align-items-center mb-4">
        <img src="@/assets/mypage.png" alt="mypage" style="width: 30px; height: 30px; margin-right: 10px" />
        <h2 class="">나의 정보</h2>
      </div>
      <ul class="list-group list-group-flush">
        <li class="list-group-item">
          <div class="ms-auto me-auto pe-3 d-flex align-items-center">
            <img src="@/assets/register/identification.png" alt="name"
              style="width: 30px; height: 30px; margin-right: 20px" />
            <div>{{ userInfo.userName }}</div>
          </div>
        </li>
        <li class="list-group-item">
          <div class="ms-auto me-auto pe-3 d-flex align-items-center">
            <img src="@/assets/register/people.png" alt="id" style="width: 30px; height: 30px; margin-right: 20px" />
            <div>{{ userInfo.userId }}</div>
          </div>
        </li>
        <li class="list-group-item">
          <div class="ms-auto me-auto pe-3 d-flex align-items-center">
            <img src="@/assets/register/smartphone-call.png" alt="tel"
              style="width: 30px; height: 30px; margin-right: 20px" />
            <div>{{ userInfo.userTel }}</div>
          </div>
        </li>
        <li class="list-group-item">
          <div class="ms-auto me-auto pe-3 d-flex align-items-center">
            <img src="@/assets/register/calendar.png" alt="birth"
              style="width: 30px; height: 30px; margin-right: 20px" />
            <div>{{ userInfo.userBirth }}</div>
          </div>
        </li>
        <li class="list-group-item">
          <div class="ms-auto me-auto pe-3 d-flex align-items-center">
            <img src="@/assets/register/mail.png" alt="birth" style="width: 30px; height: 30px; margin-right: 20px" />
            <div>{{ userInfo.userEmailId }}@{{ userInfo.userEmailDomain }}</div>
          </div>
        </li>
        <li class="list-group-item">
          <div class="ms-auto me-auto pe-3 d-flex align-items-center">
            <img src="@/assets/register/map.png" alt="birth" style="width: 30px; height: 30px; margin-right: 20px" />
            <div>{{ sigunName.sidoName }} {{ sigunName.gugunName }}</div>
          </div>
        </li>
        <li class="list-group-item">
          <div class="ms-auto me-auto pe-3 d-flex align-items-center">
            <img src="@/assets/register/face-id.png" alt="birth"
              style="width: 30px; height: 30px; margin-right: 20px" />
            <div>
              <img src="https://source.unsplash.com/random/250x250/?food" alt="profile-img"
                style="width: 250px; height: 250px" class="rounded-4" />
            </div>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<style scoped></style>
