<script setup>
import { ref, watch, onMounted } from "vue";
import { getSido, getGugun } from "@/api/user";
import { getDetail, getList, getMyFav, getBest } from "@/api/travel-info";
import {
  getNormalDistance,
  getDijkstraDistance,
  getDijkstraDistanceList,
} from "@/api/travel-board";
import VKakaoMap from "@/components/common/VKakaoMap.vue";
import TravelInfoItem from "@/components/travel_route/item/TravelInfoItem.vue";
import TravelRouteRegistModal from "@/components/travel_route/modal/TravelRouteRegistModal.vue";
// import TotalBestTravelItem from "./item/TotalBestTravelItem.vue";
import TravelDetailModal from "@/components/travel_route/modal/TravelDetailModal.vue";
// 외부 정보

import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";
const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);
const userId = userInfo.value.userId;

// 파라미터
const param = ref({
  title: "",
  sido: "",
  gugun: "",
  checkbox1: false,
  checkbox2: false,
  checkbox3: false,
  checkbox4: false,
  checkbox5: false,
  checkbox6: false,
  checkbox7: false,
  checkbox8: false,
});

// 변수 정보
const nowListFlag = ref([true]);
const travelList = ref([]); // 여행지 정보 리스트
const selectTravelInfo = ref([]);
const myTravelList = ref([]); // 내가 저장한 여행지 정보 리스트(우측)
const nowTravelInfo = ref([]);
const myFavTravelList = ref([]);
const bestTravelList = ref([]);
const mapReLoadFlag = ref(false); // 초기에는 false로 설정

const normalDistance = ref(0);
const dijkstraDistance = ref(0);
const dijkstraDistanceList = ref(0);
const dijskstraFlag = ref(false);
// 나중에 axios로 받아야 함

const sidoList = ref([]);
const gugunList = ref([]);
// 함수 정보
// 모달이 열릴 때마다 reLoadMapFlag를 토글하여 지도를 다시 로드

onMounted(() => {
  getMyFav(
    { userId },
    ({ data }) => {
      myFavTravelList.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
  getBest(
    { userId: userId },
    ({ data }) => {
      bestTravelList.value = data;
      // console.log(data);
    },
    (error) => {
      console.log(error);
    }
  );
  getSido(
    ({ data }) => {
      sidoList.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
});

// method
const getTravelList = () => {
  getList(
    param.value,
    ({ data }) => {
      travelList.value = ref([]);
      travelList.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
};

const nowSelect = ref(false);
const clickToggle = () => {
  nowSelect.value = !nowSelect.value;
  if (nowSelect.value) {
    param.value.checkbox1 = true;
    param.value.checkbox2 = true;
    param.value.checkbox3 = true;
    param.value.checkbox4 = true;
    param.value.checkbox5 = true;
    param.value.checkbox6 = true;
    param.value.checkbox7 = true;
    param.value.checkbox8 = true;
  } else {
    param.value.checkbox1 = false;
    param.value.checkbox2 = false;
    param.value.checkbox3 = false;
    param.value.checkbox4 = false;
    param.value.checkbox5 = false;
    param.value.checkbox6 = false;
    param.value.checkbox7 = false;
    param.value.checkbox8 = false;
  }
};

// Todo : 번호 가져와야 함
const getTravelInfo = (travelInfo) => {
  getDetail(
    travelInfo,
    ({ data }) => {
      nowTravelInfo.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
};

// myFavList에 있는 여행지 정보는 heart가 켜져 있어야 한다.

const viewTravelInfo = (station) => {
  selectTravelInfo.value = station;
};

const addMyTravelList = (myTravelInfo) => {
  // myTravelList가 초기화되지 않았다면 빈 배열로 초기화
  if (!myTravelList.value) {
    myTravelList.value = [];
  }

  // 기존 항목에 새로운 항목을 추가
  myTravelList.value.push(myTravelInfo);

  // 고유 ID를 기준으로 중복을 제거한 새로운 배열 생성
  const uniqueTravelList = Array.from(
    new Map(myTravelList.value.map((item) => [item.contentId, item])).values()
  );

  // 중복이 제거된 배열을 다시 할당
  myTravelList.value = uniqueTravelList;
};
const closeModal = () => {
  mapReLoadFlag.value = false;
};
watch(
  () => param.value.sido,
  () => {
    if (param.value.sido !== "") {
      param.value.gugun = ""; // 시도 변경 시 구군 초기화
      getGugun(
        param.value.sido,
        ({ data }) => {
          gugunList.value = data;
        },
        (error) => {
          console.log(error);
        }
      );
    }
  }
);
watch(
  () => myTravelList.value,
  () => {
    console.log(myTravelList.value);
    getNormalDistance(
      myTravelList.value,
      ({ data }) => {
        normalDistance.value = data;
        console.log(data);
      },
      (error) => {
        console.log(error);
      }
    );
    getDijkstraDistance(
      myTravelList.value,
      ({ data }) => {
        console.log(data);
        dijkstraDistance.value = data;
      },
      (error) => {
        console.log(error);
      }
    );
    getDijkstraDistanceList(
      myTravelList.value,
      ({ data }) => {
        console.log(data);
        dijkstraDistanceList.value = data;
      },
      (error) => {
        console.log(error);
      }
    );
  }
);
</script>

<template>
  <div class="container-fluid border border-black">
    <!-- 검색 창 -->
    <div class="d-flex justify-content-around mt-3">
      <select
        class="form-select form-select-sm text-center"
        aria-label="Small select example"
        v-model="param.sido"
      >
        <option value="" selected>시도선택</option>
        <option v-for="sido in sidoList" :key="sido.sidoCode" :value="sido.sidoCode">
          {{ sido.sidoName }}
        </option>
      </select>
      <select
        class="form-select form-select-sm text-center"
        aria-label="Small select example"
        v-model="param.gugun"
      >
        <option value="" selected>구군선택</option>
        <option v-for="gugun in gugunList" :key="gugun.guugnCode" :value="gugun.gugunCode">
          {{ gugun.gugunName }}
        </option>
      </select>
      <div class="input-group">
        <input
          v-model="param.title"
          type="text"
          class="form-control text-center"
          placeholder="여행지를 입력해 주세요."
          aria-describedby="button-addon2"
        />
        <button
          @click="getTravelList"
          class="btn btn-outline-secondary"
          type="button"
          id="button-addon2"
        >
          Button
        </button>
      </div>
    </div>
    <!-- 선택 버튼 -->
    <div class="container d-flex justify-content-around">
      <div class="form-check form-switch p-2">
        <div class="btn border border-white">
          <input
            @click="clickToggle"
            class="form-check-input"
            type="checkbox"
            role="switch"
            id="flexSwitchCheckDefault"
          />
          <label class="form-check-label" for="flexSwitchCheckDefault">모두 선택</label>
        </div>

        <input
          v-model="param.checkbox1"
          type="checkbox"
          class="btn-check"
          id="btn-check-1"
          autocomplete="off"
        />
        <label class="btn" for="btn-check-1">관광지</label>
        <input
          v-model="param.checkbox2"
          type="checkbox"
          class="btn-check"
          id="btn-check-2"
          autocomplete="off"
        />
        <label class="btn" for="btn-check-2">문화시설</label>
        <input
          v-model="param.checkbox3"
          type="checkbox"
          class="btn-check"
          id="btn-check-3"
          autocomplete="off"
        />
        <label class="btn" for="btn-check-3">행사/공연/축제</label>
        <input
          v-model="param.checkbox4"
          type="checkbox"
          class="btn-check"
          id="btn-check-4"
          autocomplete="off"
        />
        <label class="btn" for="btn-check-4">여행코스</label
        ><input
          v-model="param.checkbox5"
          type="checkbox"
          class="btn-check"
          id="btn-check-5"
          autocomplete="off"
        />
        <label class="btn" for="btn-check-5">레포츠</label
        ><input
          v-model="param.checkbox6"
          type="checkbox"
          class="btn-check"
          id="btn-check-6"
          autocomplete="off"
        />
        <input
          v-model="param.checkbox7"
          type="checkbox"
          class="btn-check"
          id="btn-check-7"
          autocomplete="off"
        />
        <label class="btn" for="btn-check-7">쇼핑</label>
        <input
          v-model="param.checkbox8"
          type="checkbox"
          class="btn-check"
          id="btn-check-8"
          autocomplete="off"
        />
        <label class="btn" for="btn-check-8">음식점</label>
        <input
          v-model="param.checkbox8"
          type="checkbox"
          class="btn-check"
          id="btn-check-8"
          autocomplete="off"
        />
      </div>
    </div>
    <div class="container-fluid">
      <div class="row row-cols-1 row-cols-md-2 g-4">
        <div class="col-md-3">
          <button
            @click="nowListFlag = true"
            type=""
            :class="`w-50 btn border ${nowListFlag ? 'border-dark' : ''}`"
            style="height: 50px"
            data-bs-toggle="button"
          >
            검색 목록
          </button>
          <button
            @click="nowListFlag = false"
            type=""
            :class="`w-50 btn border ${!nowListFlag ? 'border-dark' : ''}`"
            style="height: 50px"
            data-bs-toggle="button"
          >
            찜 목록
          </button>
          <div class="overflow-y-scroll" style="height: 750px">
            <div
              class="list-group-item travelinfo-div row w-100"
              v-for="travelInfo in nowListFlag ? travelList : myFavTravelList"
              :key="travelInfo"
            >
              <TravelInfoItem
                v-if="nowListFlag"
                @viewTravelInfo="viewTravelInfo"
                @getTravelInfo="getTravelInfo"
                :travelInfo="travelInfo"
                :userId="userId"
                type="search"
              ></TravelInfoItem>
              <TravelInfoItem
                v-if="!nowListFlag"
                @viewTravelInfo="viewTravelInfo"
                @getTravelInfo="getTravelInfo"
                :travelInfo="travelInfo"
                :userId="userId"
                type="myFavTravel"
              ></TravelInfoItem>
            </div>
          </div>
        </div>
        <div class="col-md-6">
          <!-- 여행지 맵 -->
          <VKakaoMap
            @addMyTravelList="addMyTravelList"
            :travelList="travelList"
            :selectTravelInfo="selectTravelInfo"
          >
          </VKakaoMap>
        </div>
        <div class="col-md-3">
          <div class="justify-content-center">
            <button type="" :class="`w-100 btn border border-dark`" style="height: 50px">
              여행 경로
            </button>
          </div>
          <div class="justify-content-center">
            <button
              @click="dijskstraFlag = false"
              type=""
              :class="`w-50 btn border border-dark`"
              style="height: 50px"
            >
              일반 거리 : {{ normalDistance }} km
            </button>
            <button
              @click="dijskstraFlag = true"
              type=""
              :class="`w-50 btn border border-dark`"
              style="height: 50px"
            >
              최단 거리 : {{ dijkstraDistance }} km
            </button>
          </div>
          <div class="overflow-y-scroll position-relative" style="height: 750px; max-height: 750px">
            <div
              class="list-group-item travelinfo-div row w-100"
              v-for="travelInfo in dijskstraFlag ? dijkstraDistanceList : myTravelList"
              :key="travelInfo"
            >
              <TravelInfoItem
                @viewTravelInfo="viewTravelInfo"
                @getTravelInfo="getTravelInfo"
                :travelInfo="travelInfo"
                :userId="userId"
                type="myTravelList"
              ></TravelInfoItem>
            </div>
          </div>
          <button
            @click="
              () => {
                mapReLoadFlag = true;
              }
            "
            type="button"
            class="btn btn-dark position-relative bottom-0 end-0 w-100"
            data-bs-toggle="modal"
            data-bs-target="#staticBackdrop"
          >
            등록
          </button>
        </div>
      </div>
    </div>
  </div>
  <!-- Modal -->

  <TravelRouteRegistModal
    @closeModal="closeModal"
    :mapReLoadFlag="mapReLoadFlag"
    :myTravelList="dijskstraFlag ? dijkstraDistanceList : myTravelList"
    type="regist"
  ></TravelRouteRegistModal>
  <TravelDetailModal :nowTravelInfo="nowTravelInfo"> </TravelDetailModal>
</template>

<style scoped></style>
