<script setup>
import { ref, defineProps, defineEmits, onMounted } from "vue";
import { useRouter } from "vue-router";
import { insert } from "@/api/travel-board";
import VKakaoMapDetail from "@/components/common/VKakaoMapDetail.vue";
import VKakaoMapBoard from "@/components/common/VKakaoMapBoard.vue";
import TravelInfoItem from "@/components/travel_route/item/TravelInfoItem.vue";
// @click="closeModal"
//     :reLoadMapFlag="reLoadMapFlag"
//     v-show="flag"
//     :myTravelList="myTravelList"
//     type="regist"
// 외부 정보

import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";
const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);
const userId = userInfo.value.userId;

const router = useRouter();
const props = defineProps({
  myTravelList: Array,
  type: String, // regist, board
  nowTravelRouteBoardDetailInfo: Object,
  mapReLoadFlag: Boolean,
});
const emits = defineEmits(["closeModal"]);

const param = ref({
  userId: userId,
  title: "",
  startDate: getFormattedDate(0),
  endDate: getFormattedDate(0),
  content: "",
  travelInfoList: [],
});

const sDate = ref(getFormattedDate(0));
const eDate = ref(getFormattedDate(0));

// 등록 버튼 클릭 시 호출되는 함수
const registTravelRoute = async () => {
  props.myTravelList.map((item) => {
    param.value.travelInfoList.push(item);
  });
  param.value.startDate = sDate.value;
  param.value.endDate = eDate.value;
  insert(
    param.value,
    ({ data }) => {
      const newParam = [];
      props.myTravelList.map((item, index) => {
        const newItem = {
          index: index,
          contentId: item.contentId,
        };
        newParam.push(newItem);
      });
      param.value.title = "";
      sDate.value = getFormattedDate(0);
      eDate.value = getFormattedDate(0);
      param.value.content = "";
      router.push({ name: "travel-routeboard" });
      alert("등록 성공!");
    },
    (error) => {
      alert("등록 실패!");
      console.log(error);
    }
  );
};

// 날짜를 형식에 맞게 가져오는 함수
function getFormattedDate(myear) {
  const now = new Date();
  const year = now.getFullYear() - myear;
  const month = String(now.getMonth() + 1).padStart(2, "0");
  const day = String(now.getDate()).padStart(2, "0");
  return `${year}-${month}-${day}`;
}

onMounted(() => {
  if (props.type === "board") {
    param.value.title = props.nowTravelRouteBoardDetailInfo.title;
    sDate.value = props.nowTravelRouteBoardDetailInfo.startDate;
    eDate.value = props.nowTravelRouteBoardDetailInfo.endDate;
    param.value.content = props.nowTravelRouteBoardDetailInfo.content;
  }
});
const closeModalAndNavigate = (userId) => {
  router.push({ name: "mypage-home", params: { userId } });
};
const closeModalHandler = () => {
  emits("closeModal");
};
</script>

<template>
  <div
    class="modal fade"
    id="staticBackdrop"
    data-bs-backdrop="static"
    data-bs-keyboard="false"
    tabindex="-1"
    aria-labelledby="staticBackdropLabel"
    aria-hidden="true"
  >
    <div class="modal-xl modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="staticBackdropLabel" v-if="props.type === 'regist'">
            나만의 여행 등록
          </h5>
          <h5 class="modal-title" id="staticBackdropLabel" v-if="props.type === 'board'">
            여행 경로 상세 보기
          </h5>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>
        <div class="modal-body row row-cols-1 row-cols-md-2 g-2">
          <div class="col-3">
            <div v-if="props.type === 'board'" class="input-group flex-nowrap mb-3">
              작성자: &nbsp;
              <span class="dropdown">
                <span
                  class="fw-bold dropdown-toggle"
                  type="button"
                  data-bs-toggle="dropdown"
                  aria-expanded="false"
                  >{{ props.nowTravelRouteBoardDetailInfo.userId }}</span
                >
                <ul class="dropdown-menu">
                  <li class="ps-2">
                    <a
                      v-if="props.nowTravelRouteBoardDetailInfo.userId"
                      @click.prevent="
                        closeModalAndNavigate(props.nowTravelRouteBoardDetailInfo.userId)
                      "
                      style="text-decoration: none; color: black; cursor: pointer"
                      data-bs-dismiss="modal"
                      aria-label="Close"
                      >활동 내역 보러가기</a
                    >
                    <!-- <RouterLink
                      v-if="props.nowTravelRouteBoardDetailInfo.userId"
                      :to="{
                        name: 'mypage-home',
                        params: { userId: props.nowTravelRouteBoardDetailInfo.userId },
                      }"
                      style="text-decoration: none; color: black"
                      class="btn-close"
                      data-bs-dismiss="modal"
                      aria-label="Close"
                      >활동 내역 보러가기</RouterLink
                    > -->
                  </li>
                </ul>
              </span>
              <!-- {{ props.nowTravelRouteBoardDetailInfo.userId }} -->
            </div>
            <div class="input-group flex-nowrap mb-3">
              <span class="input-group-text" id="addon-wrapping">제목</span>
              <input
                v-if="props.type === 'regist'"
                v-model="param.title"
                type="text"
                class="form-control"
                placeholder="계획 이름을 입력하세요"
                aria-label="plan-title"
                aria-describedby="addon-wrapping"
              />
              <input
                v-if="props.type === 'board'"
                type="text"
                class="form-control"
                aria-label="plan-title"
                aria-describedby="addon-wrapping"
                :value="props.nowTravelRouteBoardDetailInfo.title"
                readonly
              />
            </div>

            <div class="input-group flex-nowrap mb-3 justify-content-between">
              <span class="input-group-text" id="addon-wrapping">날짜</span>
              <input
                v-if="props.type === 'regist'"
                v-model="sDate"
                type="date"
                class="form-control"
                id="date"
                :max="eDate"
                :min="getFormattedDate(30)"
              />
              <input
                v-if="props.type === 'regist'"
                v-model="eDate"
                type="date"
                class="form-control"
                id="date"
                :max="getFormattedDate(0)"
                :min="sDate"
              />
              <input
                v-if="props.type === 'board'"
                type="date"
                class="form-control"
                id="date"
                :value="props.nowTravelRouteBoardDetailInfo.startdate"
                readonly
              />
              <input
                v-if="props.type === 'board'"
                type="date"
                class="form-control"
                id="date"
                :value="props.nowTravelRouteBoardDetailInfo.enddate"
                readonly
              />
            </div>
            <div class="input-group">
              <span class="input-group-text" style="height: auto">내용</span>
              <textarea
                v-if="props.type === 'regist'"
                v-model="param.content"
                class="form-control"
                aria-label="With textarea"
                style="height: 200px"
              ></textarea>
              <textarea
                v-if="props.type === 'board'"
                :value="props.nowTravelRouteBoardDetailInfo.content"
                class="form-control"
                aria-label="With textarea"
                readonly
              ></textarea>
            </div>
          </div>
          <div>
            <VKakaoMapDetail
              v-if="props.type === 'regist'"
              :detailTravelList="myTravelList"
              :firstStation="myTravelList[0]"
              :mapReLoadFlag="props.mapReLoadFlag"
            ></VKakaoMapDetail>
            <VKakaoMapBoard
              v-if="props.type === 'board'"
              :detailTravelList="myTravelList"
              :firstStation="myTravelList[0]"
              :mapReLoadFlag="props.mapReLoadFlag"
            ></VKakaoMapBoard>
          </div>
        </div>

        <div class="modal-header">
          <h5 class="modal-title" id="staticBackdropLabel">여행 상세 코스</h5>
        </div>
        <div class="modal-body row d-flex flex-wrap justify-content-center">
          <div class="w-50 m-2" v-for="(travelInfo, index) in props.myTravelList" :key="index">
            <TravelInfoItem :travelInfo="travelInfo" :type="type"></TravelInfoItem>
          </div>
        </div>

        <div class="modal-footer">
          <button
            v-if="type === 'regist'"
            type="button"
            class="btn btn-primary"
            data-bs-dismiss="modal"
            @click="registTravelRoute"
          >
            등록
          </button>
          <button
            v-if="type === 'board'"
            type="button"
            class="btn btn-primary"
            data-bs-dismiss="modal"
          >
            수정
          </button>
          <button
            @click="closeModalHandler"
            type="button"
            class="btn btn-secondary"
            data-bs-dismiss="modal"
          >
            닫기
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* Custom styles for better layout */
.modal-body {
  max-height: 60vh;
  overflow-y: auto;
}
</style>
