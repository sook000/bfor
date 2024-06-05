<script setup>
import { ref, defineProps, defineEmits, onMounted } from "vue";
import { checkFav, clickFav } from "@/api/travel-info";
import noImage from "@/assets/img/noImage.png";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";
const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);

const props = defineProps({
  travelInfo: Object,
  userId: String,
  type: String,
});
const emits = defineEmits(["viewTravelInfo", "getTravelInfo"]);

const param = {
  userId: userInfo.value.userId,
  contentId: parseInt(props.travelInfo.contentId),
};
const favoriteFlag = ref(false);
onMounted(() => {
  checkFav(
    param,
    ({ data }) => {
      favoriteFlag.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
});

const clickTravelInfo = (travelInfo) => {
  if (props.type !== "regist") {
    // 클릭 이벤트 핸들러 내에서 이벤트 발송
    // emits("viewTravelInfo", travelInfo);
    emits("getTravelInfo", travelInfo.contentId);
  }
};
const clickPin = (station) => {
  console.log(station);
  emits("viewTravelInfo", station);
};

const toggleFavorite = () => {
  favoriteFlag.value = !favoriteFlag.value;
  clickFav(
    param,
    ({ data }) => {
      console.log(data);
      // favoriteFlag.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
};
</script>

<template>
  <div
    class="d-flex align-items-center justify-content-center container rounded row row-cols-1 row-cols-md-3 g-2 travelinfo-div justify-content-around w-100"
  >
    <img
      class="w-50 h-75 rounded-3"
      :src="props.travelInfo.firstImage2 ? props.travelInfo.firstImage2 : noImage"
    />
    <div class="w-50">
      <h5
        @click="clickTravelInfo(props.travelInfo)"
        type="button"
        class="btn-white"
        :data-bs-toggle="props.type === 'regist' ? '' : 'modal'"
        data-bs-target="#exampleModal"
      >
        {{ props.travelInfo.title }}
      </h5>
      <div class="">
        {{ props.travelInfo.addr1 }}
      </div>
    </div>

    <div
      v-if="props.type !== 'board' && props.type !== 'regist'"
      class="d-flex position-absolute justify-content-end end-0 bottom-0"
    >
      <img
        @click="clickPin(props.travelInfo)"
        class="semi-btn img-fluid"
        src="@/assets/img/majesticons-pin-line.png"
      />
      <img
        v-if="!favoriteFlag"
        @click="toggleFavorite"
        class="semi-like img-fluid"
        src="@/assets/img/mdi--heart-outline-off.png"
      />
      <img
        v-else
        @click="toggleFavorite"
        class="semi-like img-fluid"
        src="@/assets/img/mdi--heart-on.png"
      />
    </div>
  </div>
</template>

<style scoped>
.semi-btn,
.semi-like {
  width: 24px;
  height: 24px;
  padding: 2px;
}
.semi-btn:hover,
.semi-like:hover {
  cursor: pointer;
  width: 28px;
  height: 28px;
  padding: 2px;
}
.travelinfo-div {
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.12), 0 1px 2px rgba(0, 0, 0, 0.24);
  transition: all 0.3s cubic-bezier(0.25, 0.8, 0.25, 1);
}

.travelinfo-div:hover {
  box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
}
</style>
