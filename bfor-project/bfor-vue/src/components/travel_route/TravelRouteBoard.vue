<script setup>
import { ref, onMounted, watch } from "vue";
import { getList, getOrderInfo, getThumbnailList } from "@/api/travel-board.js";
import TravelRouteBoardItem from "@/components/travel_route/item/TravelRouteBoardItem.vue";
import TravelRouteRegistModal from "@/components/travel_route/modal/TravelRouteRegistModal.vue";
import InfiniteLoading from "v3-infinite-loading";

import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";
const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);

// console.log(userInfo.value);
const myTravelList = ref([]);
const param = ref({
  key: "",
  word: "",
  pgno: 1,
  spp: 20,
  userId: "",
});

const mapReLoadFlag = ref(false);

// const userId = ref("user123");
// const key = ref("");
// const word = ref("");
const myBoardFlag = ref(false);
const travelRouteBoard = ref([]);
const nowTravelRouteBoardDetailInfo = ref("");
watch(myBoardFlag, (myBoardFlag) => {
  if (myBoardFlag === true) {
    console.log(userInfo.value.userId);
    param.value.userId = userInfo.value.userId;
  } else {
    console.log("내가쓴글false");
    param.value.userId = "";
  }
});

onMounted(async () => {
  fetchData();
});
// 서버에서 데이터를 가져오는 함수입니다.
const fetchData = async () => {
  try {
    // getList 함수를 사용하여 여행 정보를 가져옵니다.
    await getList(
      param.value,
      ({ data }) => {
        console.log(data);
        data.list.forEach((item) => {
          travelRouteBoard.value.push(item);
        });
        // console.log(travelRouteBoard.value);
        // travelRouteBoard.value = data.list;
      },
      (error) => {
        console.log(error);
      }
    );
    // travelrouteboard 배열에 가져온 데이터를 추가합니다.
    // 페이지 번호를 증가시킵니다.
    param.value.pgno++;
  } catch (error) {
    console.error("Error fetching data:", error);
  }
};

const searchList = () => {
  // 서버에서 데이터를 가져오는 함수입니다.
  travelRouteBoard.value = [];
  param.value.pgno = 1;
  // param.word = word.value;
  // param.key = key.value;
  fetchData();
};

const clickCard = (travelInfo) => {
  nowTravelRouteBoardDetailInfo.value = travelInfo;
  getOrderInfo(
    travelInfo.trbNo,
    ({ data }) => {
      myTravelList.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
};

const openModal = () => {
  mapReLoadFlag.value = true;
};

const closeModal = () => {
  mapReLoadFlag.value = false;
};
</script>

<template>
  <div class="row justify-content-center mb-5 text-center">
    <div
      class="ms-auto me-auto px-3 col-lg-4 border-bottom border-secondary border-2 d-flex align-items-center justify-content-center"
    >
      <img
        src="@/assets/place-heart.png"
        alt="freeboard"
        style="width: 30px; height: 30px; margin-right: 10px"
      />
      <h2 class="my-3">여행게시판</h2>
    </div>
  </div>
  <div class="container-fluid">
    <div class="row justify-content-center">
      <div class="col-md-2 mb-3">
        <select class="form-select" v-model="param.key" aria-label="Default select example">
          <option value="" selected>구분</option>
          <option value="title">제목</option>
          <option value="user_id">작성자</option>
        </select>
      </div>
      <div class="col-md-5 mb-3">
        <div class="input-group">
          <input
            v-model="param.word"
            type="text"
            class="form-control"
            placeholder="검색"
            aria-label="param.word"
            aria-describedby="button-addon2"
          />
          <button
            @click="searchList"
            class="btn btn-outline-secondary"
            type="button"
            id="button-addon2"
          >
            검색
          </button>
        </div>
      </div>
      <div class="col-md-3 mb-3">
        <div class="form-check">
          <input
            class="form-check-input"
            type="checkbox"
            v-model="myBoardFlag"
            @change="searchList"
            id="flexCheckDefault"
          />
          <label class="form-check-label" for="flexCheckDefault"> 내가 쓴 글 보기 </label>
        </div>
      </div>
    </div>
    <div
      class="container-fluid mb-5 text-center"
      style="height: 720px"
      v-if="travelRouteBoard.length == 0"
    >
      <span> 검색 결과가 없습니다. </span>
    </div>
    <div class="container-fluid" v-else>
      <div class="row row-cols-1 row-cols-md-4 g-4">
        <div
          :key="travelRouteInfo.id"
          v-for="travelRouteInfo in travelRouteBoard"
          class="col d-flex"
          @click="openModal"
        >
          <TravelRouteBoardItem
            @clickCard="clickCard"
            @closeModal="closeModal"
            @mapReLoadFlag="mapReLoadFlag"
            @fetchData="fetchData"
            :travelRouteInfo="travelRouteInfo"
          >
          </TravelRouteBoardItem>
        </div>
        <InfiniteLoading @infinite="fetchData" />
      </div>
    </div>
  </div>

  <TravelRouteRegistModal
    :myTravelList="myTravelList"
    :nowTravelRouteBoardDetailInfo="nowTravelRouteBoardDetailInfo"
    type="board"
  ></TravelRouteRegistModal>
</template>

<style scoped>
/* 추가적인 스타일링은 여기에 작성합니다. */
</style>
