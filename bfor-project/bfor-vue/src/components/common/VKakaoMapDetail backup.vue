<script setup>
import { ref, onMounted, watch, watchEffect, defineProps } from "vue";
import { KakaoMap, KakaoMapMarkerPolyline } from "vue3-kakao-maps";

// 외부 값
const props = defineProps({
  detailTravelList: Array,
  firstStation: Object,
  mapReLoadFlag: Boolean,
});
const lat = ref(36.61882624);
const lng = ref(128.1342659);

// 변수
const map = ref(null);
const markerList = ref([]);
let bounds; // 지도를 재설정할 범위정보를 가지고 있을 LatLngBounds 객체;

watch(
  () => props.mapReLoadFlag,
  () => {
    console.log(props.mapReLoadFlag);
    getBounds();
  }
);

const onLoadKakaoMap = (mapRef) => {
  map.value = mapRef;
  setCenter();
  // 지도 중심을 변경합니다.
};

// const lat = ref(33.450701);
// const lng = ref(126.570667);
// const latRange = [33.45058, 33.450701];
// const lngRange = [126.570667, 126.574942];

// const changeLatLng = () => {
//   lat.value = Math.random() * (latRange[1] - latRange[0]) + latRange[0];
//   lng.value = Math.random() * (lngRange[1] - lngRange[0]) + lngRange[0];
// };

const setCenter = () => {
  if (map.value) {
    map.value.setCenter(new kakao.maps.LatLng(33.450701, 126.570667));
  }
  map.value.relayout();
};

// const panTo = () => {
//   if (map.value) {
//     // 지도 중심을 부드럽게 이동시킵니다
//     // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
//     map.value.panTo(new kakao.maps.LatLng(33.45058, 126.574942));
//   }
//   map.value.relayout();
// };

// watch, watchEffect...
watchEffect(() => {
  if (map.value && props.mapReLoadFlag) {
    setTimeout(() => {
      map.value.relayout(); // 일정 시간 후에 relayout 수행
      getBounds();
    }, 200); // 밀리초 단위로 시간 지정
  }
  if (map.value !== null) {
    map.value.relayout();
  }
});

watch(
  () => props.detailTravelList,
  () => {
    // 마커 리스트 변경
    markerList.value = props.detailTravelList.map((place, index) => ({
      key: index + 1,
      lat: place.latitude,
      lng: place.longitude,
    }));
    getBounds();
    setBounds();
  }
);
const getBounds = () => {
  if (map.value) {
    bounds = new kakao.maps.LatLngBounds();
    let marker;
    let point;

    markerList.value.forEach((markerInfo) => {
      // 배열의 좌표들이 잘 보이게 마커를 지도에 추가합니다
      point = new kakao.maps.LatLng(markerInfo.lat, markerInfo.lng);

      marker = new kakao.maps.Marker({ position: point });
      if (map.value !== undefined) {
        marker.setMap(map.value);
      }

      // LatLngBounds 객체에 좌표를 추가합니다
      bounds.extend(point);
    });
    setBounds();
  }
};

const setBounds = () => {
  // LatLngBounds 객체에 추가된 좌표들을 기준으로 지도의 범위를 재설정합니다
  // 이때 지도의 중심좌표와 레벨이 변경될 수 있습니다
  if (map.value && bounds) {
    console.log("bounds");
    map.value.setBounds(bounds);
  }
};
</script>

<template>
  <!-- {{ props.detailTravelList }} -->
  <KakaoMap
    v-if="props.detailTravelList.length >= 1"
    :width="'100%'"
    :height="'100%'"
    :lat="lat"
    :lng="lng"
    :level="13"
    @onLoadKakaoMap="onLoadKakaoMap"
  >
    <KakaoMapMarkerPolyline :markerList="markerList" :showMarkerOrder="true" />
  </KakaoMap>
  <div class="w-100 h-100 t" v-else>행선지를 선정하지 않으셨습니다.</div>
</template>
