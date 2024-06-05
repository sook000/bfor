<script setup>
import { ref, defineProps, defineEmits, onMounted } from "vue";
import { checkFav, clickFav } from "@/api/travel-info";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";
const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);

const props = defineProps({
  place: Object,
});
const favoriteFlag = ref(false);
const param = {
  userId: userInfo.value.userId,
  contentId: props.place.contentId,
};

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
  <div class="h-100">
    <div>
      <div class="card h-100 position-relative">
        <img :src="place.firstImage2" class="card-img-top" alt="..." style="height: 200px" />
        <div class="favorite-icon">
          <img
            style="width: 35px; height: 35px; cursor: pointer"
            v-if="!favoriteFlag"
            @click="toggleFavorite"
            class="semi-like img-fluid"
            src="@/assets/img/mdi--heart-outline-off.png"
          />
          <img
            v-else
            style="width: 35px; height: 35px; cursor: pointer"
            @click="toggleFavorite"
            class="semi-like img-fluid"
            src="@/assets/img/mdi--heart-on.png"
          />
        </div>
        <div class="card-body">
          <h5 class="card-title">{{ place.title }}</h5>
          <p class="card-text">
            {{ place.addr1 }}
          </p>
          <div>
            <button class="btn btn-secondary me-2" @click="getTravelInfo(place.contentId)">
              상세 정보
            </button>
          </div>
          <div class="row align-center">
            <div class="col-auto"></div>

            <div class="col-2"></div>
          </div>
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
