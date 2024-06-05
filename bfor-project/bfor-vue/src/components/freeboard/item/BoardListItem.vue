<script setup>
import { computed, onMounted, ref } from "vue";
import { countComment } from "@/api/freeboard.js";
const props = defineProps({ article: Object });
const category = computed(() => {
  if (props.article.categoryId === 1) {
    return "자유";
  } else if (props.article.categoryId === 2) {
    return "문의";
  } else if (props.article.categoryId === 3) {
    return "후기";
  } else if (props.article.categoryId === 4) {
    return "소식";
  } else {
    return "오류";
  }
});

const result = computed(() => {
  return {
    "bg-success": props.article.categoryId === 1,
    "bg-danger": props.article.categoryId === 2,
    "bg-primary": props.article.categoryId === 3,
    "bg-warning": props.article.categoryId === 4,
  };
});
const commentCount = ref(0)
onMounted(() => {
  // console.log(props.article.fbNo)
  getCommentListSize()
})
const getCommentListSize = () => {
  countComment(
    props.article.fbNo,
    ({ data }) => {
      commentCount.value = data;
    },
    (error) => {
      console.error(error);
    }
  );
};
</script>

<template>
  <tr class="text-center">
    <th scope="row">{{ article.fbNo }}</th>
    <td class="text-start">
      <span class="border border-black-subtle me-2 fw-lighter rounded-1 text-white"
        style="--bs-border-opacity: 0.5; padding: 2px; font-size: 14.5px;" :class="result">
        {{ category }}
      </span>

      <router-link :to="{ name: 'freeboard-view', params: { fbNo: article.fbNo } }" class="article-title link-dark">
        {{ article.title }}
        <span v-if="commentCount > 0">[{{ commentCount }}]</span>
      </router-link>
    </td>
    <td>{{ article.userId }}</td>
    <td>{{ article.regdate.substr(0, 10) }}</td>
    <td>{{ article.hit }}</td>
  </tr>
</template>

<style scoped>
a {
  text-decoration: none;
}
</style>
