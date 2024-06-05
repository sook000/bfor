<script setup>
import { ref, onMounted, onUpdated, computed } from "vue";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";
import { listPlace } from "@/api/recommend.js";
import { Carousel, Navigation, Slide } from "vue3-carousel";
import TravelInfoItem from "@/components/mypage/item/TravelInfoItem.vue";

import "vue3-carousel/dist/carousel.css";
const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);
const userId = userInfo.value.userId;

onMounted(() => {
  zzimList();
});

const places = ref([]);
const favCount = ref(0);
const favCategory = ref("");
const favUserId = ref("");
const zzimList = () => {
  listPlace(
    userId,
    ({ data }) => {
      //   places.value = data;
      console.log(data);
      places.value = data.places;
      favCount.value = data.count;
      favCategory.value = data.favCategory;
      favUserId.value = data.userId;
    },
    (error) => {
      console.log(error);
    }
  );
};
const breakpoints = {
  700: {
    itemsToShow: 2.5,
    snapAlign: "center",
  },
  // 1024 and up
  1024: {
    itemsToShow: 2.5,
    snapAlign: "start",
  },
};
</script>

<template>
  <div class="col-md-9">
    <div class="card p-4 text-start">
      <div class="border-bottom border-secondary border-3 d-flex align-items-center mb-4">
        <img src="@/assets/file.png" alt="mypage" style="width: 30px; height: 30px; margin-right: 10px" />
        <h2 class="">맞춤리포트</h2>
      </div>

      <div class="conatainer-fluid mb-5">
        <div class="d-flex align-items-center mb-3">
          <img src="@/assets/heart.png" alt="favorite-place" style="width: 30px; height: 30px; margin-right: 10px" />
          <h4 class="mb-0">추천 여행지</h4>
        </div>

        <Carousel :breakpoints="breakpoints" :wrap-around="true">
          <!-- <Carousel :itemsToShow="3.5" :wrapAround="true" :transition="500" :snapAlign="start"> -->
          <Slide v-for="place in places" :key="place.content_id">
            <TravelInfoItem :place="place" />
          </Slide>
          <template #addons>
            <Navigation />
          </template>
        </Carousel>
      </div>

      <div class="conatainer-fluid mt-4 mb-5">
        <div class="d-flex align-items-center mb-4">
          <img src="@/assets/heart.png" alt="favorite-place" style="width: 30px; height: 30px; margin-right: 10px" />
          <h4 class="mb-0">유사한 사용자</h4>
        </div>

        <div>
          <h5 class="mb-3">
            당신과 가장 유사한 사용자는
            <b>
              <span class="dropdown">
                <span class="fw-bold dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">{{
                  favUserId }}</span>
                <ul class="dropdown-menu">
                  <li class="ps-2">
                    <RouterLink v-if="favUserId" :to="{ name: 'mypage-home', params: { userId: favUserId } }"
                      style="text-decoration: none; color: black">활동 내역 보러가기</RouterLink>
                  </li>
                </ul>
              </span>
            </b>
            입니다.
          </h5>
          <p>총 {{ favCount }} 개의 장소가 일치합니다!!</p>
        </div>
      </div>

      <div class="conatainer-fluid mt-4 mb-5">
        <div class="d-flex align-items-center mb-4">
          <img src="@/assets/heart.png" alt="favorite-place" style="width: 30px; height: 30px; margin-right: 10px" />
          <h4 class="mb-0">좋아하는 카테고리</h4>
        </div>

        <div>
          <h5 class="mb-3">
            당신이 가장 좋아하는 장소들은
            <b>{{ favCategory }} </b>
            입니다.
          </h5>
          <p>다른 '{{ favCategory }}'들도 여행가는 건 어떨까요:)</p>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.card {
  position: relative;
}

.favorite-icon {
  position: absolute;
  top: 10px;
  right: 10px;
}
</style>
