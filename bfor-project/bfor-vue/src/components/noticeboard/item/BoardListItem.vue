<script setup>
defineProps({ article: Object });
</script>

<template>
  <tr class="text-center">
    <th scope="row">{{ article.noticeNo }}</th>
    <td class="text-start">
      <router-link
        :to="{ name: 'noticeboard-view', params: { noticeNo: article.noticeNo } }"
        class="article-title link-dark"
      >
        {{ article.title }}
      </router-link>
    </td>
    <td>{{ article.adminId }}</td>
    <td>{{ article.regdate }}</td>
  </tr>
</template>

<style scoped>
a {
  text-decoration: none;
}
</style>
