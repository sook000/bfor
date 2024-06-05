<script setup>
import { ref, onMounted, watch, watchEffect, defineProps } from "vue";
import { KakaoMap, KakaoMapMarkerPolyline } from "vue3-kakao-maps";

// 외부 값
const props = defineProps({ detailTravelList: Array, mapReLoadFlag: Boolean });
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
  }
);

const onLoadKakaoMap = (mapRef) => {
  map.value = mapRef;
  // 지도 중심을 변경합니다.
};

// watch, watchEffect...
watchEffect(() => {
  if (map.value && props.mapReLoadFlag) {
    setTimeout(() => {
      map.value.relayout(); // 일정 시간 후에 relayout 수행
    }, 200); // 밀리초 단위로 시간 지정
    if (map.value !== undefined) {
      map.value.relayout();
      getBounds();
    }
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
    console.log(markerList);
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
    map.value.relayout();
    map.value.setBounds(bounds);
  }
};
</script>

<template>
  <KakaoMap
    v-if="props.detailTravelList.length >= 1 && props.mapReLoadFlag"
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
