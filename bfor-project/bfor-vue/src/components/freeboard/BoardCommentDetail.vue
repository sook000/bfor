<script setup>
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
import { listComment } from "@/api/freeboard.js";
import BoardCommentUpdateForm from "@/components/freeboard/item/BoardCommentUpdateForm.vue";
import BoardCommentItem from "@/components/freeboard/item/BoardCommentItem.vue";
defineProps({ comment: Object });
const updateButton = ref(false)
const route = useRoute();

const { fbNo } = route.params;
const comments = ref([])
const emit = defineEmits(['delComment'])

onMounted(() => {
    getCommentList()
})
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
const delComment = () => {
    emit('delComment')
}
</script>

<template>
    <div class="row justify-content-center">

        <div class="divider mt-3 mb-3"></div>

        <BoardCommentItem :comment="comment" v-show="!updateButton" @clickUpdate="updateButton = !updateButton"
            @delComment="delComment" />
        <BoardCommentUpdateForm type="modify" :comment="comment" v-show="updateButton"
            @updateCommentList="getCommentList, updateButton = false" @stateChange="updateButton = false" />
    </div>
</template>

<style scoped></style>