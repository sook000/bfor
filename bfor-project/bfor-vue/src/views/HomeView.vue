<script setup>
import { ref, onMounted } from "vue";
import { Carousel, Navigation, Slide, Pagination } from "vue3-carousel";
import "vue3-carousel/dist/carousel.css";
import { useMemberStore } from "@/stores/member";
import { listFreeArticle, listNoticeArticle, listPlace } from "@/api/home.js";
import { storeToRefs } from "pinia";
const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);
const placeList = ref([]);
const freeArticles = ref([]);
const noticeArticles = ref([]);
onMounted(() => {
  getArticleList();
  getNoticeList();
  getplaceList();
});
const getplaceList = () => {
  listPlace(
    userInfo.value === null ? 1 : userInfo.value.sidoCode,
    ({ data }) => {
      placeList.value = data;
      console.log(data);
    },
    (error) => {
      console.log(error);
    }
  );
};
const getArticleList = () => {
  listFreeArticle(
    ({ data }) => {
      // console.log(data)
      freeArticles.value = data;
    },
    (error) => {
      console.error(error);
    }
  );
};
const getNoticeList = () => {
  listNoticeArticle(
    ({ data }) => {
      // console.log(data)
      noticeArticles.value = data;
    },
    (error) => {
      console.error(error);
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
    itemsToShow: 4.5,
    snapAlign: "start",
  },
};

const slidesData = [
  {
    id: 1,
    title: "신나는 봄 나들이",
    imgSrc: "https://source.unsplash.com/random/250x250/?spring",
    tags: ["여행", "힐링", "나들이"],
  },
  {
    id: 2,
    title: "제주도 여행",
    imgSrc: "https://source.unsplash.com/random/250x250/?jeju",
    tags: ["새로움", "바다"],
  },
  {
    id: 3,
    title: "등산 코스 여행",
    imgSrc: "https://source.unsplash.com/random/250x250/?hiking",
    tags: ["건강", "맛집"],
  },
  {
    id: 4,
    title: "바다에서 즐기기",
    imgSrc: "https://source.unsplash.com/random/250x250/?sea",
    tags: ["편안", "건강", "날씨중요"],
  },
  {
    id: 5,
    title: "즐거운 호캉스",
    imgSrc: "https://source.unsplash.com/random/250x250/?hotel",
    tags: ["휴식", "놀거리"],
  },
];
</script>

<template>
  <div class="container-fluid p-3 pb-0">
    <div class="container-fluid justify-center mb-3">
      <div class="rounded-2">
        <Carousel>
          <Slide v-for="slide in 10" :key="slide">
            <div class="carousel__item">
              <img
                src="https://source.unsplash.com/random/?trip"
                class="card-img-top"
                alt="..."
                style="height: 500px"
              />
            </div>
            <div class="carousel__caption">FIND YOUR TRIP</div>
          </Slide>

          <template #addons>
            <Navigation class="nav-button" />
            <Pagination />
          </template>
        </Carousel>
      </div>
    </div>

    <div class="conatainer-fluid mb-5 mt-5 p-3">
      <div class="d-flex align-items-center mb-4">
        <img
          src="@/assets/heart.png"
          alt="favorite-place"
          style="width: 30px; height: 30px; margin-right: 10px"
        />
        <h4 class="mb-0">지역 여행지 best 4</h4>
      </div>

      <div class="row row-cols-1 row-cols-md-4 g-4">
        <div class="col" v-for="place in placeList" :key="place.contentId">
          <div class="card">
            <img :src="place.firstImage2" class="card-img-top" alt="..." />
            <div class="card-body">
              <h5 class="card-title">{{ place.title }}</h5>
              <p class="card-text">
                {{ place.addr1 }}
              </p>
              <a href="#" class="btn btn-secondary">상세 정보</a>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="conatainer-fluid mb-5 mt-5 p-3">
      <div class="d-flex align-items-center mb-3">
        <img
          src="@/assets/place-heart.png"
          alt="favorite-place"
          style="width: 35px; height: 35px; margin-right: 10px"
        />
        <h4 class="mb-0">여행 경로 best 5</h4>
      </div>
      <Carousel :breakpoints="breakpoints" :wrap-around="true">
        <!-- <Carousel :itemsToShow="3.5" :wrapAround="true" :transition="500" :snapAlign="start"> -->
        <Slide v-for="slide in slidesData" :key="slide.id">
          <div>
            <div class="card h-100">
              <img :src="slide.imgSrc" class="card-img-top" alt="..." />
              <div class="card-body text-start">
                <h5 class="card-title">{{ slide.title }}</h5>
                <p class="card-text">
                  <span v-for="tag in slide.tags" :key="tag">#{{ tag }}</span>
                </p>
                <a href="#" class="btn btn-secondary">상세 정보</a>
              </div>
            </div>
          </div>
        </Slide>
        <!-- <Slide>
          <div>
            <div class="card h-100">
              <img src="https://source.unsplash.com/random/250x250/?spring" class="card-img-top" alt="..." />
              <div class="card-body text-start">
                <h5 class="card-title">신나는 봄 나들이</h5>
                <p class="card-text">
                  <span># 여행</span>
                  <span># 힐링</span>
                  <span># 나들이</span>
                </p>
                <a href="#" class="btn btn-secondary">상세 정보</a>
              </div>
            </div>
          </div>
        </Slide>
        <Slide>
          <div>
            <div class="card h-100">
              <img src="https://source.unsplash.com/random/250x250/?spring" class="card-img-top" alt="..." />
              <div class="card-body text-start">
                <h5 class="card-title">신나는 봄 나들이</h5>
                <p class="card-text">
                  <span># 여행</span>
                  <span># 힐링</span>
                  <span># 나들이</span>
                </p>
                <a href="#" class="btn btn-secondary">상세 정보</a>
              </div>
            </div>
          </div>
        </Slide>
        <Slide>
          <div>
            <div class="card h-100">
              <img src="https://source.unsplash.com/random/250x250/?mountain" class="card-img-top" alt="..." />
              <div class="card-body text-start">
                <h5 class="card-title">등산 코스 여행</h5>
                <p class="card-text">
                  <span># 건강</span>
                  <span># 맛집</span>
                </p>
                <a href="#" class="btn btn-secondary">상세 정보</a>
              </div>
            </div>
          </div>
        </Slide>
        <Slide>
          <div>
            <div class="card h-100">
              <img src="https://source.unsplash.com/random/250x250/?fall" class="card-img-top" alt="..." />
              <div class="card-body text-start">
                <h5 class="card-title">바다에서 즐기기</h5>
                <p class="card-text">
                  <span># 편안</span>
                  <span># 건강</span>
                  <span># 날씨중요</span>
                </p>
                <a href="#" class="btn btn-secondary">상세 정보</a>
              </div>
            </div>
          </div>
        </Slide>
        <Slide>
          <div>
            <div class="card h-100">
              <img src="https://source.unsplash.com/random/250x250/?spring" class="card-img-top" alt="..." />
              <div class="card-body text-start">
                <h5 class="card-title">즐거운 호캉스</h5>
                <p class="card-text">
                  <span># 휴식</span>
                  <span># 놀거리</span>
                </p>
                <a href="#" class="btn btn-secondary">상세 정보</a>
              </div>
            </div>
          </div>
        </Slide> -->
        <template #addons>
          <Navigation />
        </template>
      </Carousel>
    </div>

    <div class="conatainer-fluid p-3">
      <div class="row row-cols-1 row-cols-md-2 g-4">
        <div class="col">
          <div class="d-flex align-items-center">
            <img
              src="@/assets/clipboard.png"
              alt="favorite-place"
              style="width: 30px; height: 30px; margin-right: 10px"
            />
            <h4 class="mb-0">자유게시판</h4>
            <router-link class="ms-auto fs-1 text-black" :to="{ name: 'freeboard-list' }"
              >+</router-link
            >
          </div>
          <table class="table table-hover">
            <!-- <thead>
              <tr class="text-center">
                <th scope="col">글번호</th>
                <th scope="col">제목</th>
                <th scope="col">작성자</th>
                <th scope="col">작성일</th>
              </tr>
            </thead> -->
            <tbody class="table-group-divider">
              <template v-for="article in freeArticles" :key="article.fbNo">
                <tr class="text-center">
                  <th scope="row">{{ article.fbNo }}</th>
                  <td class="text-start">
                    <span
                      class="border border-black-subtle me-2 fw-lighter rounded-1 text-white"
                      style="--bs-border-opacity: 0.5; padding: 2px; font-size: 14.5px"
                      :class="{
                        'bg-success': article.categoryId === 1,
                        'bg-danger': article.categoryId === 2,
                        'bg-primary': article.categoryId === 3,
                        'bg-warning': article.categoryId === 4,
                      }"
                    >
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
                    <router-link
                      :to="{ name: 'freeboard-view', params: { fbNo: article.fbNo } }"
                      class="article-title link-dark"
                    >
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

        <div class="col">
          <div class="d-flex align-items-center">
            <img
              src="@/assets/speaker.png"
              alt="favorite-place"
              style="width: 30px; height: 30px; margin-right: 10px"
            />
            <h4 class="mb-0">공지사항</h4>
            <router-link class="ms-auto fs-1 text-black" :to="{ name: 'noticeboard-list' }"
              >+</router-link
            >
          </div>
          <table class="table table-hover">
            <!-- <thead>
              <tr class="text-center">
                <th scope="col">글번호</th>
                <th scope="col">제목</th>
                <th scope="col">작성자</th>
                <th scope="col">작성일</th>
              </tr>
            </thead> -->
            <tbody class="table-group-divider">
              <template v-for="article in noticeArticles" :key="article.fbNo">
                <tr class="text-center">
                  <th scope="row">{{ article.noticeNo }}</th>
                  <td class="text-start">
                    <router-link
                      :to="{ name: 'noticeboard-view', params: { noticeNo: article.noticeNo } }"
                      class="article-title link-dark"
                    >
                      {{ article.title }}
                    </router-link>
                  </td>
                  <td>{{ article.adminId }}</td>
                  <td>{{ article.regdate.substr(0, 10) }}</td>
                </tr>
              </template>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.carousel__item {
  min-height: 200px;
  width: 100%;
  background-color: var(--vc-clr-primary);
  color: var(--vc-clr-white);
  font-size: 20px;
  border-radius: 8px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.carousel__item img {
  width: 100%;
  border-radius: 8px;
}

.carousel__slide {
  padding: 10px;
}

.carousel__prev,
.carousel__next {
  box-sizing: content-box;
  border: 5px solid white;
  background-color: white;
}

.nav-button {
  background-color: white;
  color: white;
  border: 5px solid white;
}

.carousel__prev:hover,
.carousel__next:hover {
  background-color: white;
}

.carousel__prev:focus,
.carousel__next:focus {
  background-color: white;
}

.carousel__caption {
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  color: white;
  font-size: 100px;
  font-weight: bold;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.7);
  width: 100%;
  font-family: "Work Sans", sans-serif;
  font-optical-sizing: auto;
  font-weight: 700;
  font-style: normal;
}

a {
  text-decoration: none;
}
</style>
