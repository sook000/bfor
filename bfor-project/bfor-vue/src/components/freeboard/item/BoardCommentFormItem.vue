<script setup>
import { ref, watch } from "vue";
import { useRoute } from "vue-router";
import { useMemberStore } from "@/stores/member";
import { registComment } from "@/api/freeboard";

const route = useRoute();
const memberStore = useMemberStore();
const { userInfo } = memberStore;
const emit = defineEmits(["updateCommentList"]);
const comment = ref({
  cmtNo: 0,
  fbNo: route.params.fbNo,
  userId: userInfo.userId,
  cmtContent: "",
  regdate: "",
});

const cmtContentErrMsg = ref("");
watch(
  () => comment.value.cmtContent,
  (value) => {
    let len = value.length;
    if (len == 0) {
      cmtContentErrMsg.value = "댓글을 확인해 주세요!!!";
    } else cmtContentErrMsg.value = "";
  },
  { immediate: true }
);

function onSubmit() {
  if (cmtContentErrMsg.value) {
    alert(cmtContentErrMsg);
  } else {
    writeArticle();
  }
}

function writeArticle() {
  console.log("댓글등록하자!", comment.value);
  registComment(
    comment.value,
    (response) => {
      let msg = "글등록 처리시 문제 발생했습니다.";
      if (response.status == 201) msg = "댓글등록이 완료되었습니다.";
      alert(msg);
      comment.value.cmtContent = "";
      emit("updateCommentList");
    },
    (error) => console.error(error)
  );
}
</script>

<template>
  <div class="col-lg-10 text-start">
    <form @submit.prevent="onSubmit">
      <div class="row">
        <div class="clearfix align-content-center">
          <img
            class="avatar me-2 float-md-start bg-light p-2"
            src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg"
          />
          <p>
            <span class="fw-bold">{{ userInfo.userId }}</span> <br />
          </p>
        </div>
        <div class="input-group mb-3">
          <textarea
            type="text"
            class="form-control"
            placeholder="댓글을 입력해주세요"
            aria-label="comment"
            aria-describedby="button-addon2"
            v-model="comment.cmtContent"
          ></textarea>
          <button class="btn btn-outline-secondary" type="submit" id="button-addon2">등록</button>
        </div>
      </div>
    </form>
  </div>
</template>

<style scoped></style>
