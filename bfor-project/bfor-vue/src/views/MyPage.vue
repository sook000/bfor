<script setup>
import { ref, onMounted, computed } from "vue";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";
import { useRoute } from "vue-router";
import { howLike } from "@/api/mypage.js";

const route = useRoute();
const userId = computed(() => route.params.userId);

const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);
onMounted(() => {
  if (userId.value != userInfo.value.userId) calLike();
});
const gauge = ref(0);
const like = ref("");
const calLike = () => {
  howLike(
    {
      loginId: userInfo.value.userId,
      otherId: userId.value,
    },
    ({ data }) => {
      // travels.value = data;
      // console.log(travels.value);
      gauge.value = data * 150;
      console.log(gauge.value);
      like.value = "width: " + gauge.value + "%";
    },
    (error) => {
      console.log(error);
    }
  );
};
const connectShow = computed(() => {
  if (gauge.value < 25) {
    return {
      img: "&#128514;",
      text: "나와 공통된 취향이 적은 사용자 ",
    };
  } else if (gauge.value < 50) {
    return { img: "&#128517;", text: "나와 취향이 조금 다른 사용자" };
  } else if (gauge.value < 75) {
    return { img: "&#128515;", text: "나와 취향이 비슷한 사용자" };
  } else {
    return { img: "&#128518;", text: "나와 취향이 매우 유사한 사용자" };
  }
});

// console.log(userId.value);
</script>

<template>
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
          <h2 id="userid">{{ userId }}</h2>

          <div class="card-body text-start">
            <div v-if="userId != userInfo.userId" class="mb-3">
              <div
                class="progress"
                role="progressbar"
                aria-label="Basic example"
                :aria-valuenow="gauge"
                aria-valuemin="0"
                aria-valuemax="100"
              >
                <div class="progress-bar" :style="like"></div>
              </div>
              <div class="mt-3 text-center">
                <p>
                  {{ connectShow.text }}<span v-html="connectShow.img"></span>
                  <!-- <img :src="connectShow.img" alt="connectface">
                  <img :src="getImageUrl(contextshow)"> -->
                </p>
              </div>
            </div>
            <div class="list-group list-group-flush">
              <router-link
                :to="{ name: 'mypage-home', params: { userId: userId } }"
                class="list-group-item list-group-item-action list-group-item-light"
              >
                활동 내역
              </router-link>
              <template v-if="userInfo.userId === userId">
                <router-link
                  :to="{ name: 'mypage-info', params: { userId: userId } }"
                  class="list-group-item list-group-item-action list-group-item-light"
                >
                  나의정보
                </router-link>
                <router-link
                  :to="{ name: 'mypage-modify', params: { userId: userId } }"
                  class="list-group-item list-group-item-action list-group-item-light"
                >
                  정보수정
                </router-link>
                <router-link
                  :to="{ name: 'mypage-delete', params: { userId: userId } }"
                  class="list-group-item list-group-item-action list-group-item-light"
                >
                  회원탈퇴
                </router-link>
              </template>
            </div>
          </div>
        </div>
      </div>
      <router-view></router-view>
    </div>
  </div>
</template>

<style scoped></style>
