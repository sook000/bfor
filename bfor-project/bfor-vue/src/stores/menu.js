import { ref } from "vue";
import { defineStore } from "pinia";
// import { useMemberStore } from "@/stores/member";
// const memberStore = useMemberStore();
// const { userInfo } = memberStore;
export const useMenuStore = defineStore(
  "menuStore",
  () => {
    const menuList = ref([
      {
        name: "회원가입",
        show: true,
        routeName: "user-join",
        imgSrc: "/src/assets/register/register.png",
        imgAlt: "register",
      },
      {
        name: "로그인",
        show: true,
        routeName: "user-login",
        imgSrc: "/src/assets/enter.png",
        imgAlt: "login",
      },
      // {
      //   name: "오늘할일",
      //   show: false,
      //   routeName: "todos",
      //   imgSrc: "/src/assets/mypage.png",
      //   imgAlt: "a",
      // },
      {
        name: "마이페이지",
        show: false,
        routeName: "mypage-home",
        imgSrc: "/src/assets/check-user.png",
        imgAlt: "mypage",
      },
      {
        name: "추천페이지",
        show: false,
        routeName: "recommend-home",
        imgSrc: "/src/assets/recommendation.png",
        imgAlt: "recommend",
      },
      {
        name: "로그아웃",
        show: false,
        routeName: "user-logout",
        imgSrc: "/src/assets/exit.png",
        imgAlt: "logout",
      },
    ]);

    const changeMenuState = () => {
      console.log(menuList.value);
      menuList.value = menuList.value.map((item) => ({ ...item, show: !item.show }));
    };

    return {
      menuList,
      changeMenuState,
    };
  },
  { persist: true }
);
