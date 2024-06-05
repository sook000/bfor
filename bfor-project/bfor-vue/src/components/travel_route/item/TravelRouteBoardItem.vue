<script setup>
import { defineProps, defineEmits } from "vue";
import noImage from "@/assets/img/noImage.png";
const emits = defineEmits(["fetchData", "clickCard"]);
const props = defineProps({ travelRouteInfo: Object });
</script>

<template>
  <!-- {{ travelRouteInfo }} -->
  <button
    type="button"
    class="card btn btn-white border-grey w-100"
    style="height: 400px"
    data-bs-toggle="modal"
    data-bs-target="#staticBackdrop"
    @click="
      () => {
        emits('clickCard', travelRouteInfo);
      }
    "
  >
    <img
      :src="props.travelRouteInfo.first_image ? props.travelRouteInfo.first_image : noImage"
      class="card-img-top rounded"
      style="height: 300px; object-fit: cover"
    />
    <!-- 이미지 높이 설정 -->
    <div class="card-body d-flex flex-column">
      <h5 class="card-title">{{ props.travelRouteInfo.title }}</h5>
      <!-- <p class="card-text flex-grow-1">작성자 {{ props.travelRouteInfo.userId }}</p> -->
      <!-- <p class="card-text"> </p>-->
      <small class="text-muted">{{ props.travelRouteInfo.userId }}</small>
    </div>
  </button>

  <!-- 무한 스크롤을 위한 InfiniteLoading 컴포넌트입니다. -->
  <!-- travelrouteboard 배열을 반복하여 여행 정보를 표시합니다. -->
  <TravelRouteInfoModal :myTravelList="myTravelList" type="regist"></TravelRouteInfoModal>
</template>

<!-- 컴포넌트 내부에만 적용되는 스타일입니다. -->
<style scoped>
.container-fluid {
  padding-left: 15px;
  padding-right: 15px;
}

.card {
  margin-left: 15px;
  margin-right: 15px;
}
</style>
