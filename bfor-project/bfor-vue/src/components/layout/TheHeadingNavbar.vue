<script setup>
import { useMenuStore } from "@/stores/menu.js";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member.js";

const menuStore = useMenuStore();
const memberStore = useMemberStore();

const { menuList } = storeToRefs(menuStore);
const { changeMenuState } = menuStore;
const { userLogout } = memberStore;
const { userInfo } = storeToRefs(memberStore);

const logout = () => {
  const input = confirm("로그아웃 하시겠습니까?");
  if (input) {
    userLogout();
    console.log("hi");
    changeMenuState();
  }
};
</script>

<template>
  <div>
    <nav class="navbar navbar-expand-lg border border-end">
      <div class="container-fluid">
        <router-link :to="{ name: 'home' }" class="navbar-brand me-0" style="margin-right: 5px">
          <img
            src="@/assets/bforlogo.png"
            class="rounded me-0"
            alt="logo"
            style="width: 100px; height: auto"
          />
        </router-link>

        <button
          class="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarText"
          aria-controls="navbarText"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarText">
          <ul class="navbar-nav me-auto mb-2 mb-lg-0 ms-3">
            <!-- <li class="nav-item d-flex align-items-center m-3">
              <div class="link">
                <RouterLink :to="{ name: 'travel-search' }"
                  ><img
                    src="@/assets/travel-search.png"
                    alt="freeboard"
                    style="width: 25px; height: 25px; margin-right: 8px"
                /></RouterLink>
              </div>
            </li>
            <li class="nav-item m-3 d-flex align-items-center">
              <div class="link">
                <RouterLink :to="{ name: 'travel-routeboard' }"
                  ><img
                    src="@/assets/place-heart.png"
                    alt="freeboard"
                    style="width: 20px; height: 20px; margin-right: 8px"
                /></RouterLink>
              </div>
            </li>
            <li class="nav-item d-flex align-items-center m-3">
              <div class="link">
                <RouterLink :to="{ name: 'freeboard' }"
                  ><img
                    src="@/assets/clipboard.png"
                    alt="freeboard"
                    style="width: 20px; height: 20px; margin-right: 8px"
                /></RouterLink>
              </div>
            </li>
            <li class="nav-item m-3 d-flex align-items-center">
              <div class="link">
                <RouterLink :to="{ name: 'noticeboard' }"
                  ><img
                    src="@/assets/speaker.png"
                    alt="freeboard"
                    style="width: 20px; height: 20px; margin-right: 8px"
                /></RouterLink>
              </div>
            </li> -->
            <li class="nav-item d-flex align-items-center m-3">
              <img
                src="@/assets/travel-search.png"
                alt="freeboard"
                style="width: 25px; height: 25px; margin-right: 8px"
              />
              <div class="link">
                <RouterLink :to="{ name: 'travel-search' }">여행 만들기</RouterLink>
              </div>
            </li>
            <li class="nav-item m-3 d-flex align-items-center">
              <img
                src="@/assets/place-heart.png"
                alt="freeboard"
                style="width: 20px; height: 20px; margin-right: 8px"
              />
              <div class="link">
                <RouterLink :to="{ name: 'travel-routeboard' }">여행게시판</RouterLink>
              </div>
            </li>
            <li class="nav-item d-flex align-items-center m-3">
              <img
                src="@/assets/clipboard.png"
                alt="freeboard"
                style="width: 20px; height: 20px; margin-right: 8px"
              />
              <div class="link">
                <RouterLink :to="{ name: 'freeboard' }">자유게시판</RouterLink>
              </div>
            </li>
            <li class="nav-item m-3 d-flex align-items-center">
              <img
                src="@/assets/speaker.png"
                alt="freeboard"
                style="width: 20px; height: 20px; margin-right: 8px"
              />
              <div class="link">
                <RouterLink :to="{ name: 'noticeboard' }">공지사항</RouterLink>
              </div>
            </li>
            <!-- <li class="nav-item m-3 d-flex align-items-center">
              <div class="link">
                <router-link :to="{ name: 'home' }" @click.prevent="logout">임시로그아웃</router-link>
              </div>
            </li> -->
            <!-- <li class="nav-item m-3 d-flex align-items-center">
              <img src="@/assets/recommendation.png" alt="recommend"
                style="width: 20px; height: 20px; margin-right: 8px" />
              <div class="link">
                <RouterLink :to="{ name: 'recommend-home', params: { userId: userInfo.userId } }">추천</RouterLink>
              </div>
            </li> -->
          </ul>
          <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
            <template v-for="menu in menuList" :key="menu.routeName">
              <template v-if="menu.show">
                <template v-if="menu.routeName === 'user-logout'">
                  <li class="nav-item d-flex align-items-center m-3">
                    <img
                      :src="menu.imgSrc"
                      :alt="menu.imgAlt"
                      style="width: 20px; height: 20px; margin-right: 8px"
                    />
                    <div class="link">
                      <router-link :to="{ name: 'home' }" @click.prevent="logout">{{
                        menu.name
                      }}</router-link>
                    </div>
                  </li>
                </template>
                <template v-else-if="menu.routeName === 'mypage-home'">
                  <li class="nav-item d-flex align-items-center m-3">
                    <img
                      :src="menu.imgSrc"
                      :alt="menu.imgAlt"
                      style="width: 20px; height: 20px; margin-right: 8px"
                    />
                    <div class="link">
                      <router-link
                        v-if="userInfo"
                        :to="{ name: 'mypage-home', params: { userId: userInfo.userId } }"
                        >마이페이지</router-link
                      >
                    </div>
                  </li>
                </template>
                <template v-else-if="menu.routeName === 'recommend-home'">
                  <li class="nav-item d-flex align-items-center m-3">
                    <img
                      :src="menu.imgSrc"
                      :alt="menu.imgAlt"
                      style="width: 20px; height: 20px; margin-right: 8px"
                    />
                    <div class="link">
                      <router-link
                        v-if="userInfo"
                        :to="{ name: 'recommend-home', params: { userId: userInfo.userId } }"
                        >추천</router-link
                      >
                    </div>
                  </li>
                </template>
                <template v-else>
                  <li class="nav-item d-flex align-items-center m-3">
                    <img
                      :src="menu.imgSrc"
                      :alt="menu.imgAlt"
                      style="width: 20px; height: 20px; margin-right: 8px"
                      @error="console.log(`Image load error for ${menu.imgSrc}`)"
                    />
                    <div class="link">
                      <RouterLink :to="{ name: menu.routeName }">{{ menu.name }}</RouterLink>
                    </div>
                  </li>
                </template>
              </template>
            </template>
          </ul>
        </div>
      </div>
    </nav>
  </div>
  <div class="divider mt-4 mb-4"></div>
</template>

<style scoped>
.link {
  display: inline-block;
  margin: 0 2px;
}

.link a,
.link a:visited {
  /* 기본 링크와 방문한 링크에 대해 */
  color: black;
  /* 텍스트 색상을 검정색으로 변경 */
  text-decoration: none;
  /* 밑줄 제거 */
  /* font-weight: 700; */
  font-size: 20px;
  /* font-family: "Gowun Dodum", sans-serif;
  font-weight: 400;
  font-style: normal; */
}
</style>
