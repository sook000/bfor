<script setup>
import { ref, watch } from "vue";

import { modifyComment } from "@/api/freeboard";
const emit = defineEmits(["updateCommentList", "stateChange"]);

const props = defineProps({ type: String, comment: Object });
const localComment = ref(props.comment);
// console.log(localComment.value)
// console.log(props.comment.value);
const cmtContentErrMsg = ref("");
watch(
  () => props.comment.cmtContent,
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
    updateComment();
  }
}

function updateComment() {
  console.log("댓글수정하자!", localComment.value);
  modifyComment(
    localComment.value,
    (response) => {
      let msg = "글등록 처리시 문제 발생했습니다.";
      if (response.status == 200) msg = "댓글수정이 완료되었습니다.";
      alert(msg);
      emit("updateCommentList");
    },
    (error) => console.error(error)
  );
}

function stateChange() {
  emit("stateChange");
}
</script>

<template>
  <div class="col-lg-10 text-start">
    <form>
      <div class="row">
        <div class="clearfix align-content-center">
          <img class="avatar me-2 float-md-start bg-light p-2"
            src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg" />
          <p><span class="fw-bold">ssafy(로그인ID)</span> <br /></p>
        </div>
        <div class="input-group mb-3">
          <textarea type="text" class="form-control" aria-label="comment" aria-describedby="button-addon2"
            v-model="localComment.cmtContent"></textarea>
          <button class="btn btn-outline-secondary" type="button" id="button-addon2" @click="onSubmit">등록</button>
          <button class="btn btn-outline-danger" type="button" id="button-addon2" @click="stateChange">취소</button>
        </div>
      </div>
    </form>
  </div>
</template>

<style scoped></style>
