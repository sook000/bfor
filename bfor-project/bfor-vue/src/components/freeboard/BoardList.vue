<script setup>
import { ref, onMounted, watch } from "vue";
import { useRouter } from "vue-router";
import { storeToRefs } from "pinia";

import { listArticle } from "@/api/freeboard.js";
import { useMemberStore } from "@/stores/member";
const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);
import VSelect from "@/components/common/VSelect.vue";
import BoardListItem from "@/components/freeboard/item/BoardListItem.vue";
import VPageNavigation from "@/components/common/VPageNavigation.vue";

const router = useRouter();

const selectOption = ref([
  { text: "검색조건", value: "" },
  { text: "글번호", value: "fb_no" },
  { text: "제목", value: "title" },
  { text: "작성자아이디", value: "user_id" },
]);

const articles = ref([]);
const currentPage = ref(1);
const totalPage = ref(0);
const { VITE_ARTICLE_LIST_SIZE } = import.meta.env;
const mypost = ref(false);

const param = ref({
  pgno: currentPage.value,
  spp: VITE_ARTICLE_LIST_SIZE,
  key: "",
  word: "",
  type: "",
  mypost: "",
});
watch(mypost, (mypost) => {
  if (mypost === true) {
    param.value.mypost = userInfo.value.userId;
  } else {
    param.value.mypost = "";
  }
});
onMounted(() => {
  getArticleList();
});

const changeKey = (val) => {
  param.value.key = val;
};

const getArticleList = () => {
  listArticle(
    param.value,
    ({ data }) => {
      articles.value = data.articles;
      currentPage.value = data.currentPage;
      totalPage.value = data.totalPageCount;
    },
    (error) => {
      console.error(error);
    }
  );
};

const onPageChange = (val) => {
  currentPage.value = val;
  param.value.pgno = val;
  getArticleList();
};

const moveWrite = () => {
  router.push({ name: "freeboard-write" });
};
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <div class="row align-self-center mb-3">
          <div class="col-md-5 offset-3">
            <!-- offset-5 ms-auto -->
            <form class="d-flex">
              <VSelect :selectOption="selectOption" @onKeySelect="changeKey" />
              <div class="input-group input-group-sm ms-1">
                <input
                  type="text"
                  class="form-control"
                  v-model="param.word"
                  placeholder="검색어..."
                  @keyup.enter="getArticleList"
                />
                <button class="btn btn-dark" type="button" @click="getArticleList">검색</button>
              </div>
            </form>
          </div>
          <div class="col-md-2 me-auto">
            <div class="form-check form-check-inline">
              <input
                class="form-check-input"
                type="checkbox"
                id="mypost"
                v-model="mypost"
                @change="getArticleList"
              />
              <label class="form-check-label" for="mypost">내가 쓴 글</label>
            </div>
          </div>
          <!-- <div class="col-md-2 offset-1 text-start "> -->
          <div class="col-auto text-start">
            <button type="button" class="btn btn-outline-dark btn-sm" @click="moveWrite">
              글쓰기
            </button>
          </div>
        </div>

        <div class="col-auto ms-md-auto mb-4">
          <!-- <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">글목록</mark>
          </h2> -->
          <div class="row align-self-center mb-2">
            <form>
              <div class="form-check form-check-inline">
                <input
                  class="form-check-input"
                  type="radio"
                  name="freeboardtype"
                  id="full"
                  value=""
                  v-model="param.type"
                  @change="getArticleList"
                />
                <label class="form-check-label" for="full">전체 </label>
              </div>
              <div class="form-check form-check-inline">
                <input
                  class="form-check-input"
                  type="radio"
                  name="freeboardtype"
                  id="free"
                  value="1"
                  v-model="param.type"
                  @change="getArticleList"
                />
                <label class="form-check-label" for="free">자유 </label>
              </div>
              <div class="form-check form-check-inline">
                <input
                  class="form-check-input"
                  type="radio"
                  name="freeboardtype"
                  id="question"
                  value="2"
                  v-model="param.type"
                  @change="getArticleList"
                />
                <label class="form-check-label" for="question">문의 </label>
              </div>
              <div class="form-check form-check-inline">
                <input
                  class="form-check-input"
                  type="radio"
                  name="freeboardtype"
                  id="feeling"
                  value="3"
                  v-model="param.type"
                  @change="getArticleList"
                />
                <label class="form-check-label" for="feeling">후기 </label>
              </div>
              <div class="form-check form-check-inline">
                <input
                  class="form-check-input"
                  type="radio"
                  name="freeboardtype"
                  id="news"
                  value="4"
                  v-model="param.type"
                  @change="getArticleList"
                />
                <label class="form-check-label" for="news">소식 </label>
              </div>
            </form>
          </div>
        </div>
        <table class="table table-hover">
          <thead>
            <tr class="text-center">
              <th scope="col">글번호</th>
              <th scope="col">제목</th>
              <th scope="col">작성자</th>
              <th scope="col">작성일</th>
              <th scope="col">조회수</th>
            </tr>
          </thead>
          <tbody class="table-group-divider">
            <BoardListItem v-for="article in articles" :key="article.fbNo" :article="article">
            </BoardListItem>
          </tbody>
        </table>
      </div>
      <VPageNavigation
        :current-page="currentPage"
        :total-page="totalPage"
        @pageChange="onPageChange"
      >
      </VPageNavigation>
    </div>
  </div>
</template>

<style scoped></style>
