<script setup>
import { ref } from "vue";
import { deleteComment } from "@/api/freeboard";
import { useMemberStore } from "@/stores/member";
const memberStore = useMemberStore();
const { userInfo } = memberStore;
const props = defineProps({
  comment: Object,
});
const localComment = ref(props.comment);
const param = ref({
  fbNo: localComment.value.fbNo,
  cmtNo: localComment.value.cmtNo,
});
const emit = defineEmits(["clickUpdate", "delComment"]);
function moveModify() {
  // console.log(props.comment)
  // router.push({ name: "comment-modify", params: { fbNo: props.comment.fbNo } });
  emit("clickUpdate");
}

function onDeleteComment() {
  // console.log(param.value)
  const input = confirm("정말 삭제하시겠습니까?");
  if (input) {
    deleteComment(
      param.value,
      (response) => {
        if (response.status == 200) emit("delComment");
      },
      (error) => {
        console.error(error);
      }
    );
  }
}
</script>

<template>
  <div class="col-lg-10 text-start">
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
                >{{ comment.userId }}</span
              >
              <ul class="dropdown-menu">
                <li class="ps-2">
                  <RouterLink
                    v-if="comment && comment.userId"
                    :to="{ name: 'mypage-home', params: { userId: comment.userId } }"
                    style="text-decoration: none; color: black"
                    >활동 내역 보러가기</RouterLink
                  >
                </li>
              </ul>
            </span>

            <br />
            <span class="text-secondary fw-light">
              {{ comment.regdate }}
            </span>
          </p>
        </div>
      </div>
      <div class="col-md-4 align-self-center text-end" v-if="userInfo.userId === comment.userId">
        <button
          type="button"
          class="border border-1 border-success-subtle mb-3 ms-1 rounded"
          @click="moveModify"
        >
          수정
        </button>
        <button
          type="button"
          class="border border-1 border-danger-subtle mb-3 ms-1 rounded"
          @click="onDeleteComment"
        >
          삭제
        </button>
      </div>
      <!-- <div class="divider mb-1"></div> -->
      <div class="text-secondary ps-5">
        {{ comment.cmtContent }}
      </div>
      <div class="divider mt-4 mb-4"></div>
    </div>
  </div>
</template>

<style scoped></style>
