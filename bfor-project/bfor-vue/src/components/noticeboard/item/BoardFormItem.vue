<script setup>
import { ref, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { registArticle, getModifyArticle, modifyArticle } from "@/api/noticeboard";

const router = useRouter();
const route = useRoute();

const props = defineProps({ type: String });

// const isUseId = ref(false);

const article = ref({
  noticeNo: 0,
  title: "",
  noticeContent: "",
  adminId: "admin",
  regdate: "",
});

if (props.type === "modify") {
  let { noticeNo } = route.params;
  console.log(noticeNo);
  getModifyArticle(
    noticeNo,
    ({ data }) => {
      article.value = data;
      //   isUseId.value = true;
    },
    (error) => {
      console.error(error);
    }
  );
  //   isUseId.value = true;
}

const titleErrMsg = ref("");
const contentErrMsg = ref("");
watch(
  () => article.value.title,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 30) {
      titleErrMsg.value = "제목을 확인해 주세요!!!";
    } else titleErrMsg.value = "";
  },
  { immediate: true }
);
watch(
  () => article.value.noticeContent,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 500) {
      contentErrMsg.value = "내용을 확인해 주세요!!!";
    } else contentErrMsg.value = "";
  },
  { immediate: true }
);

function onSubmit() {
  // event.preventDefault();

  if (titleErrMsg.value) {
    alert(titleErrMsg.value);
  } else if (contentErrMsg.value) {
    alert(contentErrMsg.value);
  } else {
    props.type === "regist" ? writeArticle() : updateArticle();
  }
}

function writeArticle() {
  console.log("글등록하자!!", article.value);
  registArticle(
    article.value,
    (response) => {
      let msg = "글등록 처리시 문제 발생했습니다.";
      if (response.status == 201) msg = "글등록이 완료되었습니다.";
      alert(msg);
      moveList();
    },
    (error) => console.error(error)
  );
}

function updateArticle() {
  console.log(article.value.noticeNo + "번글 수정하자!!", article.value);
  modifyArticle(
    article.value,
    (response) => {
      let msg = "글수정 처리시 문제 발생했습니다.";
      if (response.status == 200) msg = "글정보 수정이 완료되었습니다.";
      alert(msg);
      moveList();
      // router.push({ name: "article-view" });
      // router.push(`/board/view/${article.value.articleNo}`);
    },
    (error) => console.log(error)
  );
}

function moveList() {
  router.push({ name: "noticeboard-list" });
}
</script>

<template>
  <form @submit.prevent="onSubmit">
    <div class="mb-3">
      <label for="userid" class="form-label">작성자 ID : </label>
      <input
        type="text"
        class="form-control"
        v-model="article.adminId"
        :disabled="true"
        placeholder="작성자ID..."
      />
    </div>
    <div class="mb-3">
      <label for="subject" class="form-label">제목 : </label>
      <input type="text" class="form-control" v-model="article.title" placeholder="제목..." />
    </div>
    <div class="mb-3">
      <label for="content" class="form-label">내용 : </label>
      <!-- <textarea class="form-control" v-model="article.content" rows="10"></textarea> -->
      <QuillEditor
        theme="snow"
        v-model:content="article.noticeContent"
        contentType="html"
        style="height: 200px"
      />
    </div>

    <div class="col-auto text-center">
      <button type="submit" class="btn btn-outline-primary mb-3" v-if="type === 'regist'">
        글작성
      </button>
      <button type="submit" class="btn btn-outline-success mb-3" v-else>글수정</button>
      <button type="button" class="btn btn-outline-secondary mb-3 ms-1" @click="moveList">
        목록으로이동...
      </button>
    </div>
  </form>
</template>

<style scoped></style>
