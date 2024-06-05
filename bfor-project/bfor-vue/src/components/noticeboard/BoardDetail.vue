<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { detailArticle, deleteArticle } from "@/api/noticeboard";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";

const route = useRoute();
const router = useRouter();
const memberStore = useMemberStore();

const { noticeNo } = route.params;
const { userInfo } = storeToRefs(memberStore);

const article = ref({
  regdate: "2024-05-14",
});

onMounted(() => {
  getArticle();
});

const getArticle = () => {
  detailArticle(
    noticeNo,
    ({ data }) => {
      article.value = data;
    },
    (error) => {
      console.error(error);
    }
  );
};

function moveList() {
  router.push({ name: "noticeboard-list" });
}

function moveModify() {
  router.push({ name: "noticeboard-modify", params: { noticeNo } });
}

function onDeleteArticle() {
  const input = confirm("정말로 삭제하시겠습니까?");
  if (input) {
    deleteArticle(
      noticeNo,
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
      <div class="col-lg-10"></div>
      <div class="col-lg-10 text-start">
        <div class="row">
          <div class="col-auto my-2">
            <h2 class="text-secondary px-5">{{ article.noticeNo }}. {{ article.title }}</h2>
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
                <span class="fw-bold">{{ article.adminId }}</span> <br />
                <span class="text-secondary fw-light">
                  {{ article.regdate }}
                </span>
              </p>
            </div>
          </div>
          <div class="divider mb-3"></div>
          <div class="text-secondary" v-html="article.noticeContent"></div>
          <!-- <div class="text-secondary">{{ article.noticeContent }}</div> -->
          <div class="divider mt-3 mb-3"></div>
          <div class="d-flex justify-content-end">
            <button type="button" class="btn btn-outline-secondary mb-3" @click="moveList">
              글목록
            </button>
            <template v-if="userInfo != null && userInfo.userId === 'admin'">
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
  </div>
</template>

<style scoped></style>
