<script setup>
import { defineProps, defineEmits } from "vue";
const props = defineProps({ nowTravelInfo: Object, show: Boolean });
console.log(props.nowTravelInfo);
const emits = defineEmits(["close"]);

const closeModal = () => {
  emits("close");
};
</script>
<template>
  <div
    v-if="show"
    class="modal fade h-100 w-100"
    id="exampleModal"
    tabindex="-1"
    aria-labelledby="exampleModalLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog modal-xl modal-dialog-scrollable">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="exampleModalLabel">여행지 상세 정보</h1>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>
        <div class="modal-body row">
          <!-- firstimage1 -->
          <img :src="props.nowTravelInfo.firstImage" class="col-md-6 mb-3" alt="..." />
          <div class="col-md-6 mb-3">
            <div class="form-control mb-3" v-if="props.nowTravelInfo.title">
              {{ props.nowTravelInfo.title }}
            </div>
            <div class="form-control mb-3" v-if="props.nowTravelInfo.tel">
              {{ props.nowTravelInfo.tel }}
            </div>
            <div class="form-control mb-3" v-if="props.nowTravelInfo.addr1">
              {{ props.nowTravelInfo.addr1 }}
            </div>
            <div class="form-control mb-3" v-if="props.nowTravelInfo.addr2">
              {{ props.nowTravelInfo.addr2 }}
            </div>
            <div class="form-control mb-3" v-if="props.nowTravelInfo.overview">
              {{ props.nowTravelInfo.overview }}
            </div>
            <div class="form-control mb-3" v-if="props.nowTravelInfo.homepage">
              <a :href="props.nowTravelInfo.homepage">홈페이지</a>
            </div>
          </div>
          <!-- <span>{{ nowTravelInfo.firstimage1 }}</span>
            <span>{{ nowTravelInfo.gugunCode }}</span>
            <span>{{ nowTravelInfo }}</span> -->
        </div>

        <!-- <div class="modal-footer">
          <p>설명하겠읍니다</p>
        </div> -->
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
