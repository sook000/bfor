<script setup>
import { ref, onMounted, watch, computed } from "vue";
import { useRoute, useRouter } from "vue-router";
import { detailArticle, deleteArticle, listComment } from "@/api/freeboard";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";
import BoardCommentFormItem from "@/components/freeboard/item/BoardCommentFormItem.vue";
import BoardCommentDetail from "@/components/freeboard/BoardCommentDetail.vue";
const route = useRoute();
const router = useRouter();
const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);
// console.log(userInfo.value.userId);
const { fbNo } = route.params;
const comments = ref([]);
const article = ref({
  fbNo: "",
  title: "",
  userId: "",
  regdate: "2024-05-14",
  categoryId: "1",
  hit: 0,
  content: "",
});
const category = computed(() => {
  if (article.value.categoryId === 1) {
    return "자유";
  } else if (article.value.categoryId === 2) {
    return "문의";
  } else if (article.value.categoryId === 3) {
    return "후기";
  } else if (article.value.categoryId === 4) {
    return "소식";
  } else {
    return "오류";
  }
});
const result = computed(() => {
  return {
    "bg-success": article.value.categoryId === 1,
    "bg-danger": article.value.categoryId === 2,
    "bg-primary": article.value.categoryId === 3,
    "bg-warning": article.value.categoryId === 4,
  };
});
const changeRegdate = ref();
watch(
  () => article.value.regdate,
  () => {
    changeRegdate.value =
      article.value.regdate.substr(0, 10) + " " + article.value.regdate.substr(11, 8);
  },
  { immediate: true }
);

onMounted(() => {
  getArticle();
  getCommentList();
});

const getArticle = () => {
  detailArticle(
    fbNo,
    ({ data }) => {
      article.value = data;
    },
    (error) => {
      console.error(error);
    }
  );
};
const getCommentList = () => {
  listComment(
    fbNo,
    ({ data }) => {
      comments.value = data;
      // console.log(comments.value);
    },
    (error) => {
      console.error(error);
    }
  );
};

function moveList() {
  router.push({ name: "freeboard-list" });
}

function moveModify() {
  router.push({ name: "freeboard-modify", params: { fbNo } });
}

function onDeleteArticle() {
  const input = confirm("정말로 삭제하시겠습니까?");
  if (input) {
    deleteArticle(
      fbNo,
      (response) => {
        if (response.status == 200) moveList();
      },
      (error) => {
        console.error(error);
      }
    );
  }
}
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <!-- <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">글보기</mark>
        </h2> -->
      </div>
      <div class="col-lg-10 text-start">
        <div class="row">
          <div class="col-auto my-2">
            <h2 class="text-secondary px-5">{{ article.fbNo }}. {{ article.title }}</h2>
          </div>
          <div class="col-auto ms-auto align-self-center text-end">
            <span
              class="border border-black-subtle me-2 fw-lighter rounded-1 text-white"
              style="--bs-border-opacity: 0.5"
              :class="result"
            >
              {{ category }}
            </span>
          </div>
        </div>

        <div class="row">
          <div class="col-md-8">
            <div class="clearfix align-content-center">
              <img
                class="avatar me-2 float-md-start bg-light p-2"
                src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg"
              />
              <p>
                <span class="dropdown">
                  <span
                    class="fw-bold dropdown-toggle"
                    type="button"
                    data-bs-toggle="dropdown"
                    aria-expanded="false"
                    >{{ article.userId }}</span
                  >
                  <ul class="dropdown-menu">
                    <li class="ps-2">
                      <RouterLink
                        v-if="article && article.userId"
                        :to="{ name: 'mypage-home', params: { userId: article.userId } }"
                        style="text-decoration: none; color: black"
                        >활동 내역 보러가기</RouterLink
                      >
                    </li>
                  </ul>
                </span>
                <br />
                <!-- <span class="fw-bold">{{ article.userId }}</span> <br /> -->
                <span class="text-secondary fw-light">
                  {{ article.regdate }} 조회수 : {{ article.hit }}
                </span>
              </p>
            </div>
          </div>
          <div class="col-md-4 align-self-center text-end">댓글 : {{ comments.length }}</div>
          <div class="divider mb-3"></div>
          <div class="text-secondary" v-html="article.content"></div>
          <div class="divider mt-3 mb-3"></div>
          <div class="d-flex justify-content-end">
            <button type="button" class="btn btn-outline-secondary mb-3" @click="moveList">
              글목록
            </button>
            <template v-if="userInfo.userId === article.userId">
              <button type="button" class="btn btn-outline-success mb-3 ms-1" @click="moveModify">
                글수정
              </button>
              <button
                type="button"
                class="btn btn-outline-danger mb-3 ms-1"
                @click="onDeleteArticle"
              >
                글삭제
              </button>
            </template>
          </div>
          <div class="divider mt-3 mb-4"></div>
        </div>
      </div>
    </div>
    <div class="row justify-content-center">
      <BoardCommentFormItem type="regist" @updateCommentList="getCommentList" />
      <div class="divider mt-3 mb-3"></div>
      <div v-for="comment in comments" :key="comment.cmtNo">
        <BoardCommentDetail
          :comment="comment"
          @updateCommentList="getCommentList"
          @delComment="getCommentList"
        />
      </div>
      <!-- <BoardCommentItem type="regist" v-for="comment in comments" :key="comment.cmtNo" :comment="comment" /> -->
    </div>
  </div>
</template>

<style scoped></style>
