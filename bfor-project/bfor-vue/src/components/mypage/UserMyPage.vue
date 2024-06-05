<script setup>
import { ref, onMounted, onUpdated, computed } from "vue";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";
import { useRoute } from "vue-router";
import { Carousel, Navigation, Slide } from "vue3-carousel";
import { listFreeArticle, listzzim, listroute } from "@/api/mypage.js";
import { getDetail } from "@/api/travel-info";
import TravelDetailModal from "@/components/mypage/modal/TravelDetailModal.vue";
import TravelInfoItem from "@/components/mypage/item/TravelInfoItem.vue";
import "vue3-carousel/dist/carousel.css";

const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);

const route = useRoute();
// 다른 사람 아이디
const userId = computed(() => route.params.userId);

// 임시 데이터 places, travels
const places = ref([]);
const travels = ref([]);
// console.log(userInfo.value.userId);
const articles = ref([]);

onMounted(() => {
  getArticleList();
  zzimList();
  routeList();
});
//업데이트는 잘 되지만 무한로딩됨
// onUpdated(() => {
//   getArticleList();
//   zzimList();
//   routeList();
// });
const getArticleList = () => {
  listFreeArticle(
    userId.value,
    ({ data }) => {
      articles.value = data;
    },
    (error) => {
      console.error(error);
    }
  );
};
const zzimList = () => {
  listzzim(
    userId.value,
    ({ data }) => {
      places.value = data;
      console.log(data);
    },
    (error) => {
      console.log(error);
    }
  );
};
const routeList = () => {
  listroute(
    userId.value,
    ({ data }) => {
      travels.value = data;
      console.log("travels", travels.value);
      // console.log(data);
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

//여행지 상세정보
const nowTravelInfo = ref([]);
const showModal = ref(false); // 모달 표시 여부를 제어하는 상태
const getTravelInfo = (travelInfo) => {
  getDetail(
    travelInfo,
    ({ data }) => {
      nowTravelInfo.value = data;
      console.log(nowTravelInfo.value);
      showModal.value = true; // 모달을 표시하도록 상태 변경
    },
    (error) => {
      console.log(error);
    }
  );
};
// 모달을 닫는 함수
const closeModal = () => {
  showModal.value = false;
};
</script>

<template>
  <!-- 메인 중앙 콘텐츠 -->
  <div class="col-md-9">
    <div class="card p-4 text-start">
      <div class="border-bottom border-secondary border-3 d-flex align-items-center mb-4">
        <img src="@/assets/mypage.png" alt="mypage" style="width: 30px; height: 30px; margin-right: 10px" />
        <h2 class="">활동 내역</h2>
      </div>

      <div class="conatainer-fluid mb-5">
        <div class="d-flex align-items-center mb-3">
          <img src="@/assets/heart.png" alt="favorite-place" style="width: 30px; height: 30px; margin-right: 10px" />
          <h4 class="mb-0">찜한 여행지</h4>
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

      <div class="conatainer-fluid mb-5">
        <div class="d-flex align-items-center mb-3">
          <img src="@/assets/place-heart.png" alt="favorite-place"
            style="width: 35px; height: 35px; margin-right: 10px" />
          <h4 class="mb-0">작성한 여행코스</h4>
        </div>
        <Carousel :breakpoints="breakpoints" :wrap-around="true">
          <!-- <Carousel :itemsToShow="3.5" :wrapAround="true" :transition="500" :snapAlign="start"> -->
          <Slide v-for="travel in travels" :key="travel.title">
            <div>
              <div class="card h-100">
                <img :src="travel.first_image" class="card-img-top" alt="..." />
                <div class="card-body text-start">
                  <h5 class="card-title">
                    {{ travel.addr1 }}
                    {{ travel.title === null ? "여행 코스" : travel.title }}
                  </h5>
                  <p class="card-text">
                    <span v-for="hashcode in travel.hashcode" :key="hashcode">#{{ content.substr(10) }}</span>
                  </p>
                  <button class="btn btn-secondary">상세 정보</button>
                </div>
              </div>
            </div>
          </Slide>
          <template #addons>
            <Navigation />
          </template>
        </Carousel>
      </div>

      <div class="conatainer-fluid mb-5">
        <div class="d-flex align-items-center mb-3">
          <img src="@/assets/clipboard.png" alt="favorite-place"
            style="width: 30px; height: 30px; margin-right: 10px" />
          <h4 class="mb-0">자유게시판</h4>
        </div>
        <table class="table table-hover">
          <thead>
            <tr class="text-center">
              <th scope="col">글번호</th>
              <th scope="col">제목</th>
              <th scope="col">작성자</th>
              <th scope="col">작성일</th>
            </tr>
          </thead>
          <tbody class="table-group-divider">
            <template v-for="article in articles" :key="article.fbNo">
              <tr class="text-center">
                <th scope="row">{{ article.fbNo }}</th>
                <td class="text-start">
                  <span class="border border-black-subtle me-2 fw-lighter rounded-1 text-white"
                    style="--bs-border-opacity: 0.5; padding: 2px; font-size: 14.5px" :class="{
                      'bg-success': article.categoryId === 1,
                      'bg-danger': article.categoryId === 2,
                      'bg-primary': article.categoryId === 3,
                      'bg-warning': article.categoryId === 4,
                    }">
                    {{
                      article.categoryId === 1
                        ? "자유"
                        : article.categoryId === 2
                          ? "문의"
                          : article.categoryId === 3
                            ? "후기"
                            : "소식"
                    }}
                  </span>
                  <router-link :to="{ name: 'freeboard-view', params: { fbNo: article.fbNo } }"
                    class="article-title link-dark">
                    {{ article.title }}
                  </router-link>
                </td>
                <td>{{ article.userId }}</td>
                <td>{{ article.regdate.substr(0, 10) }}</td>
              </tr>
            </template>
          </tbody>
        </table>
      </div>
    </div>
  </div>

  <TravelDetailModal :nowTravelInfo="nowTravelInfo" :show="showModal" @close="closeModal"></TravelDetailModal>
</template>

<style scoped>
a {
  text-decoration: none;
}
</style>
