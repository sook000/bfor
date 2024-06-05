<script setup>
import { KakaoMap, KakaoMapMarker } from "vue3-kakao-maps";
import { ref, watch, defineProps, defineEmits, onMounted } from "vue";
import noImage from "@/assets/img/noImage.png";
const props = defineProps({
  travelList: Array,
  type: String,
  selectTravelInfo: Object,
});
const emits = defineEmits(["addMyTravelList"]);
const lat = ref(36.61882624);
const lng = ref(128.1342659);
const map = ref();
let bounds;

const onLoadKakaoMap = (mapRef) => {
  map.value = mapRef;
  bounds = new kakao.maps.LatLngBounds();
  let marker;
  let point;

  props.travelList.forEach((markerInfo) => {
    point = new kakao.maps.LatLng(markerInfo.latitude, markerInfo.longitude);

    marker = new kakao.maps.Marker({ position: point });
    if (map.value !== undefined) {
      marker.setMap(map.value);
    }

    bounds.extend(point);
    setCenter(props.travelList[0]);
  });
};

const setCenter = (marker) => {
  if (map.value && marker && marker.latitude && marker.longitude) {
    map.value.setCenter(new kakao.maps.LatLng(marker.latitude, marker.longitude));
  }
};

const handleAddMyTravelList = (travelInfo) => {
  emits("addMyTravelList", travelInfo);
};

const myContent = (marker) => {
  const content = document.createElement("div");
  content.classList.add("row", "align-items-center");
  content.style.width = "100%";
  // 왼쪽에 사진을 넣는 공간
  const leftCol = document.createElement("div");
  leftCol.classList.add("col-auto");

  // 여행지 사진을 표시하는 img 태그 생성
  const placeImage = document.createElement("img");
  placeImage.src = marker.firstImage2 ? marker.firstImage2 : noImage; // 여행지 이미지 URL 설정
  placeImage.style.width = "100px"; // 이미지 너비 고정
  placeImage.style.height = "100px"; // 이미지 높이 고정
  leftCol.appendChild(placeImage); // 사진을 왼쪽 열에 추가
  content.appendChild(leftCol); // 왼쪽 열을 content에 추가

  // 오른쪽 공간에 제목과 주소를 표시하는 공간
  const rightCol = document.createElement("div");
  rightCol.classList.add("col");

  // 제목을 표시하는 div 태그 생성
  const title = document.createElement("div");
  title.innerText = marker.title;
  title.classList.add("fw-bold", "mb-2"); // 제목 스타일 설정
  rightCol.appendChild(title); // 제목을 오른쪽 열에 추가

  // 주소를 표시하는 div 태그 생성
  const addr1 = document.createElement("div");
  addr1.innerText = marker.addr1;
  addr1.classList.add("mb-2"); // 주소 스타일 설정
  rightCol.appendChild(addr1); // 주소를 오른쪽 열에 추가

  content.appendChild(rightCol); // 오른쪽 열을 content에 추가

  // 버튼 생성
  const addButton = document.createElement("button");
  addButton.textContent = "추가";
  addButton.classList.add(
    "btn",
    "btn-outline-primary",
    "btn-sm",
    "w-auto",
    "position-absolute",
    "bottom-0",
    "end-0"
  ); // 버튼 스타일 설정, ms-auto는 오른쪽 정렬을 위함
  addButton.addEventListener("click", () => handleAddMyTravelList(marker)); // 버튼 클릭 이벤트 핸들러 추가
  content.appendChild(addButton); // 버튼을 content에 추가

  return content;
};

const markers = ref([]);
watch(
  () => props.travelList,
  async () => {
    markers.value.length = 0;
    await props.travelList.forEach((marker) => {
      marker.flag = false;
      marker.infoWindow = {
        content: myContent(marker),
        visible: false,
      };
      markers.value.push(marker);
    });
    markers.value = props.travelList;
    setCenter(markers.value[0]);
  }
);

watch(
  () => props.selectTravelInfo,
  () => {
    setCenter(props.selectTravelInfo);
  }
);

const toggleInfoWindow = (marker) => {
  marker.infoWindow.visible = !marker.infoWindow.visible;
  const updatedMarkers = markers.value.map((m) => (m === marker ? { ...m } : m));
  markers.value = updatedMarkers;
};
</script>

<template>
  <KakaoMap
    :width="'100%'"
    :height="'100%'"
    :lat="lat"
    :lng="lng"
    @onLoadKakaoMap="onLoadKakaoMap"
    :draggable="props.type === 'detail' ? false : true"
    :level="13"
  >
    <KakaoMapMarker
      v-for="marker in markers"
      :key="marker.contentId === undefined ? index : marker.contentId"
      :lat="marker.latitude"
      :lng="marker.longitude"
      :infoWindow="marker.infoWindow"
      :clickable="true"
      :scrollwheel="props.type === 'detail' ? false : true"
      @onClickKakaoMapMarker="
        () => {
          toggleInfoWindow(marker);
        }
      "
    />
  </KakaoMap>
</template>
