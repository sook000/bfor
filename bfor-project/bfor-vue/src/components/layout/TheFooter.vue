<script setup></script>

<template>
  <div class="my-5 py-3"></div>
  <div class="container-fluid mt-5 pt-0 p-3">
    <div class="pt-0 p-3 mt-5">
      <div class="text-center border-bottom border-top">
        <div class="row align-items-center" style="height: 50px">
          <div class="col-2 border-end">회사소개</div>
          <div class="col-4 border-end border-start">개인정보처리방침 및 쿠키 정책</div>
          <div class="col-2 border-end border-start">사이트 운영 방식</div>
          <div class="col-2 border-end border-start">
            <router-link :to="{ name: 'flaticon' }" style="text-decoration: none; color: inherit"
              >픽토그램 사용출처</router-link
            >
          </div>
          <div class="col-2 border-start">고객센터</div>
        </div>
      </div>
      <div class="mt-4">
        <p style="line-height: 200%">
          (주)&nbsp;bforTrip &nbsp;| &nbsp;대표 Sook, Chan&nbsp; |&nbsp; 주소 경상북도 구미시 3공단
          3로 302 KR &nbsp;|&nbsp; 사업자등록번호 106-87-93420 &nbsp;|&nbsp; 이메일
          ssafygumifiveban@google.com &nbsp;|&nbsp; 대표번호 1544-3423 &nbsp;|&nbsp; 고객문의
          02-2345-1524
        </p>
      </div>
      <div class="row">
        <div class="col-6">
          <p style="color: gray">
            Copyright © 2024 bforenjoytripssafy.com Co. all rights reserved.
          </p>
        </div>
        <div class="col-auto ms-auto">
          <div class="row align-items-center">
            <div class="col-auto">
              <a href="https://www.ssafy.com" target="_blank"
                ><img class="footer-img" src="@/assets/homelogo/ssafylogo.png" alt="ssafy"
              /></a>
            </div>
            <div class="col-auto">
              <a href="https://api.visitkorea.or.kr" target="_blank">
                <img src="@/assets/homelogo/tourapi.png" alt="tourapi" class="footer-img" />
              </a>
            </div>
            <div class="col-auto">
              <a href="https://knto.or.kr/index" target="_blank">
                <img src="@/assets/homelogo/koreatourlogo.png" alt="koreatour" class="footer-img" />
              </a>
            </div>
            <div class="col-auto">
              <a href="https://www.mcst.go.kr" target="_blank">
                <img
                  src="@/assets/homelogo/cultourkorealogo.png"
                  alt="cultourkorea"
                  class="footer-img"
                />
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.footer-img {
  width: 100px;
  height: auto;
}
</style>
