import { createRouter, createWebHistory } from "vue-router";
import Profile from "@/views/Profile.vue";
import SignIn from "@/views/SignIn.vue";
import SignUp from "@/views/SignUp.vue";
import Search from "@/views/Search.vue";
import Home from "@/views/Home.vue";
import QnA from "@/views/QnA.vue";
import QnATable from "@/views/components/qna/QnATable.vue";
import QnAAdminTable from "@/views/components/qna/QnAAdminTable.vue";
import QnAWrite from "@/views/components/qna/QnAWrite.vue";
import QnAdminWrite from "@/views/components/qna/QnAdminWrite.vue";
import QnAModify from "@/views/components/qna/QnAModify.vue";
import QnAAdminModify from "@/views/components/qna/QnAAdminModify.vue";
import QnADetail from "@/views/components/qna/QnADetail.vue";
import QnAAdminDetail from "@/views/components/qna/QnAAdminDetail.vue";
import FindPass from "@/views/FindPass.vue";
import Notice from "@/views/Notice.vue";
import Agreement from "@/views/Agreement.vue";
import NoticeTable from "@/views/components/notice/NoticeTable.vue";
import NoticeDetail from "@/views/components/notice/NoticeDetail.vue";
import NoticeWrite from "@/views/components/notice/NoticeWrite.vue";
import NoticeModify from "@/views/components/notice/NoticeModify.vue";
import PasswordCheck from "@/views/PasswordCheck.vue";
import ProfileEdit from "@/views/ProfileEdit.vue";
import ChangePassword from "@/views/ChangePassword.vue";
import News from "@/views/News.vue";
import NotFound from "@/views/NotFound.vue";

import store from "@/store";

const onlyAuthUser = async (to, from, next) => {
  const checkUserInfo = store.getters["userStore/checkUserInfo"];
  const checkToken = store.getters["userStore/checkToken"];
  let token = sessionStorage.getItem("access-token");
  // console.log(sessionStorage.getItem("access-token"));
  // console.log("로그인 처리 전", checkUserInfo, token);
  // console.log("***로그인 처리의 데이터", to, from);

  if (checkUserInfo != null && token) {
    if (token === undefined || token == null || token == "undefined") {
      console.log("토큰 없다.");
    } else {
      // console.log(token);
      // console.log("??토큰 유효성 체크하러 가자!!!!");
      await store.dispatch("userStore/getUserInfo", token);
      // console.log("??토큰 유효성 체크하고 왔다!!!!");
    }
  }
  if (!checkToken || checkUserInfo === null) {
    alert("로그인이 필요한 페이지입니다..");
    // next({ name: "login" });
    router.push({ name: "Sign In" });
  } else {
    // console.log("로그인 했다!!!!!!!!!!!!!.");
    next();
  }
};
const onlyLocal = async (to, from, next) => {
  const checkUserInfo = store.getters["userStore/checkUserInfo"];
  const checkToken = store.getters["userStore/checkToken"];
  let token = sessionStorage.getItem("access-token");
  // console.log("로그인 처리 전", checkUserInfo, token);
  // console.log("***로그인 처리의 데이터", to, from);

  if (checkUserInfo != null && token) {
    if (token === undefined || token == null || token == "undefined") {
      console.log("토큰 없다.");
    } else {
      // console.log(token);
      // console.log("??토큰 유효성 체크하러 가자!!!!");
      await store.dispatch("userStore/getUserInfo", token);
      // console.log("??토큰 유효성 체크하고 왔다!!!!");
    }
  }
  if (!checkToken || checkUserInfo === null) {
    alert("로그인이 필요한 페이지입니다..");
    // next({ name: "login" });
    router.push({ name: "Sign In" });
  } else {
    // console.log("로그인 했다!!!!!!!!!!!!!.");
    next();
  }

  if (checkUserInfo.social != "HOME") {
    alert("소셜 로그인 사용자는 불가능합니다.");
    router.push({ name: from.name });
  }
};

const routes = [
  {
    path: "/",
    name: "/",
    component: Home,
  },
  {
    path: "/notice",
    name: "Notice",
    component: Notice,
    beforeEnter: onlyAuthUser,
    redirect: "/notice/list",
    children: [
      {
        path: "list",
        name: "NoticeList",
        component: NoticeTable,
      },
      {
        path: ":num",
        name: "NoticeDetail",
        component: NoticeDetail,
      },
      {
        path: "write",
        name: "NoticeWrite",
        component: NoticeWrite,
      },
      {
        path: "modify/:num",
        name: "NoticeModify",
        component: NoticeModify,
      },
    ],
  },
  {
    path: "/qna",
    name: "QnA",
    component: QnA,
    redirect: "/qna/list",
    children: [
      {
        path: "list",
        name: "QnAList",
        component: QnATable,
      },
      {
        path: "admin/list",
        name: "QnAAdmin",
        component: QnAAdminTable,
      },
      {
        path: ":num",
        name: "QnADetail",
        component: QnADetail,
      },
      {
        path: "admin/:num",
        name: "QnAAdminDetail",
        component: QnAAdminDetail,
      },
      {
        path: "write",
        name: "QnAWrite",
        component: QnAWrite,
      },
      {
        path: "/admin/write/:num",
        name: "QnAdminWrite",
        component: QnAdminWrite,
      },
      {
        path: "modify/:num",
        name: "QnAModify",
        component: QnAModify,
      },
      {
        path: "admin/modify/:num",
        name: "QnAAdminModify",
        component: QnAAdminModify,
      },
    ],
  },
  {
    path: "/profile",
    name: "Profile",
    beforeEnter: onlyAuthUser,
    component: Profile,
  },
  {
    path: "/search",
    name: "Search",
    component: Search,
  },
  {
    path: "/news",
    name: "News",
    component: News,
  },
  {
    path: "/sign-in",
    name: "Sign In",
    component: SignIn,
  },
  {
    path: "/sign-up",
    name: "Sign Up",
    component: SignUp,
  },
  {
    path: "/find-pass",
    name: "Find Pass",
    component: FindPass,
  },
  {
    path: "/agreement",
    name: "Agreement",
    component: Agreement,
  },
  {
    path: "/password-check",
    name: "Password Check",
    beforeEnter: onlyLocal,
    component: PasswordCheck,
  },
  {
    path: "/change-password",
    name: "Change Password",
    beforeEnter: onlyLocal,
    component: ChangePassword,
  },
  {
    path: "/profile-edit",
    name: "Profile Edit",
    component: ProfileEdit,
  },
  {
    path: "/:catchAll(.*)",
    component: NotFound,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
  linkActiveClass: "active",
});

export default router;
