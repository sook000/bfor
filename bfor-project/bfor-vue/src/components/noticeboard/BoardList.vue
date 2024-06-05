<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { listArticle } from "@/api/noticeboard.js";
import { useMemberStore } from "@/stores/member";
const memberStore = useMemberStore();
const { userInfo } = memberStore;
import BoardListItem from "@/components/noticeboard/item/BoardListItem.vue";
import VPageNavigation from "@/components/common/VPageNavigation.vue";

const router = useRouter();

const articles = ref([]);
const currentPage = ref(1);
const totalPage = ref(0);
const { VITE_ARTICLE_LIST_SIZE } = import.meta.env;

const param = ref({
  pgno: currentPage.value,
  spp: VITE_ARTICLE_LIST_SIZE,
});

onMounted(() => {
  getArticleList();
});

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
  router.push({ name: "noticeboard-write" });
};
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <div class="row align-self-center mb-3">
          <div class="col-md-2 me-auto"></div>
          <div class="col-auto text-start" v-if="userInfo != null && userInfo.userId === 'admin'">
            <button type="button" class="btn btn-outline-dark btn-sm" @click="moveWrite">
              글쓰기
            </button>
          </div>
        </div>

        <table class="table table-hover">
          <thead>
            <tr class="text-center">
              <th scope="col">글번호</th>
              <th scope="col">제목</th>
              <th scope="col">작성자</th>
              <th scope="col">작성일</th>
            </tr>
          </thead>
          <tbody class="table-group-divider">
            <BoardListItem v-for="article in articles" :key="article.noticeNo" :article="article">
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
