import { createRouter, createWebHistory } from "vue-router";
import HomeView from "@/views/HomeView.vue";
import { useMemberStore } from "@/stores/member.js";
import { storeToRefs } from "pinia";

const onlyAuthUser = async (to, from, next) => {
  const memberStore = useMemberStore();
  const { userInfo, isValidToken } = storeToRefs(memberStore);
  const { getUserInfo } = memberStore;

  let token = sessionStorage.getItem("accessToken");
  console.log(userInfo.value);
  if (userInfo.value != null && token) {
    await getUserInfo(token);
  }
  if (!isValidToken.value || userInfo.value === null) {
    next({ name: "user-login" });
  } else {
    next();
  }
};

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/freeboard",
      name: "freeboard",
      component: () => import("@/views/FreeBoardView.vue"),
      redirect: { name: "freeboard-list" },
      children: [
        {
          path: "list",
          name: "freeboard-list",
          component: () => import("@/components/freeboard/BoardList.vue"),
        },
        {
          path: "view/:fbNo",
          name: "freeboard-view",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/freeboard/BoardDetail.vue"),
          redirect: { name: "comment-view" },
        },
        {
          path: "write",
          name: "freeboard-write",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/freeboard/BoardWrite.vue"),
        },
        {
          path: "modify/:fbNo",
          name: "freeboard-modify",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/freeboard/BoardModify.vue"),
        },
        {
          path: "/:fbNo",
          name: "freeboard-view",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/freeboard/BoardDetail.vue"),
        },
        {
          path: "comment",
          name: "comment-view",
          component: () =>
            import("@/components/freeboard/BoardCommentDetail.vue"),
        },
      ],
    },
    {
      path: "/noticeboard",
      name: "noticeboard",
      component: () => import("@/views/NoticeBoardView.vue"),
      redirect: { name: "noticeboard-list" },
      children: [
        {
          path: "list",
          name: "noticeboard-list",
          component: () => import("@/components/noticeboard/BoardList.vue"),
        },
        {
          path: "view/:noticeNo",
          name: "noticeboard-view",
          // beforeEnter: onlyAuthUser,
          component: () => import("@/components/noticeboard/BoardDetail.vue"),
        },
        {
          path: "write",
          name: "noticeboard-write",
          // beforeEnter: onlyAuthUser,
          component: () => import("@/components/noticeboard/BoardWrite.vue"),
        },
        {
          path: "modify/:noticeNo",
          name: "noticeboard-modify",
          // beforeEnter: onlyAuthUser,
          component: () => import("@/components/noticeboard/BoardModify.vue"),
        },
      ],
    },
    {
      path: "/user",
      name: "user",
      component: () => import("@/views/TheUserView.vue"),
      children: [
        {
          path: "login",
          name: "user-login",
          component: () => import("@/components/users/UserLogin.vue"),
        },
        {
          path: "join",
          name: "user-join",
          component: () => import("@/components/users/UserRegister.vue"),
        },
        {
          path: "findId",
          name: "user-findId",
          component: () => import("@/components/users/UserFindId.vue"),
        },
        {
          path: "findIdSuccess/:userId",
          name: "user-findId-success",
          component: () => import("@/components/users/UserFindIdSuccess.vue"),
        },
        {
          path: "findPwd",
          name: "user-findPwd",
          component: () => import("@/components/users/UserFindPwd.vue"),
        },
        {
          path: "findPwdSuccess/:userId",
          name: "user-findPwd-success",
          component: () => import("@/components/users/UserFindPwdSuccess.vue"),
        },
      ],
    },
    {
      path: "/mypage",
      name: "mypage",
      component: () => import("@/views/MyPage.vue"),
      children: [
        {
          path: "home/:userId",
          name: "mypage-home",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/mypage/UserMyPage.vue"),
        },
        {
          path: "info/:userId",
          name: "mypage-info",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/mypage/UserInfo.vue"),
        },
        {
          path: "modify/:userId",
          name: "mypage-modify",
          component: () => import("@/components/mypage/UserModify.vue"),
        },
        {
          path: "delete/:userId",
          name: "mypage-delete",
          component: () => import("@/components/mypage/UserDelete.vue"),
        },
      ],
    },
    {
      path: "/flaticon",
      name: "flaticon",
      component: () => import("@/views/FlaticonRef.vue"),
    },
    // {
    //   path: "/todos",
    //   name: "todos",
    //   beforeEnter: onlyAuthUser,
    //   component: () => import("@/views/TheTodoView.vue"),
    // },
    {
      path: "/travelroute",
      name: "travelroute",
      component: () => import("../views/TravelRouteView.vue"),
      // redirect: { name: "travel-route-search" },
      children: [
        {
          path: "search",
          name: "travel-search",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/travel_route/TravelRouteSearch.vue"),
        },
        {
          path: "routeboard",
          name: "travel-routeboard",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/travel_route/TravelRouteBoard.vue"),
        },
      ],
    },
    {
      path: "/recommend",
      name: "recommend",
      component: () => import("@/views/UserRecommend.vue"),
      children: [
        {
          path: ":userId",
          name: "recommend-home",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/recommend/PlaceRecommend.vue"),
        },
        //   {
        //     path: "info/:userId",
        //     name: "mypage-info",
        //     beforeEnter: onlyAuthUser,
        //     component: () => import("@/components/mypage/UserInfo.vue"),
        //   },
        //   {
        //     path: "modify/:userId",
        //     name: "mypage-modify",
        //     component: () => import("@/components/mypage/UserModify.vue"),
        //   },
        //   {
        //     path: "delete/:userId",
        //     name: "mypage-delete",
        //     component: () => import("@/components/mypage/UserDelete.vue"),
        //   },
      ],
    },
  ],
});

export default router;
