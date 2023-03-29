<template>
  <div class="container-fluid" style="margin-top: 96px">
    <div
      class="mt-4 page-header min-height-200 border-radius-xl"
      :style="{
        backgroundImage:
          'url(' + require('@/assets/img/curved-images/curved14.jpg') + ')',
        backgroundPositionY: '50%',
      }"
    >
      <span class="mask bg-gradient-success opacity-6"></span>
    </div>
    <div class="mx-4 overflow-hidden card card-body blur shadow-blur mt-n6">
      <div class="row gx-4">
        <div class="col-auto">
          <div class="avatar avatar-xl position-relative">
            <img
              src="@/assets/img/profile.png"
              alt="profile_image"
              class="shadow-sm w-100 border-radius-lg"
              v-if="userInfo == null || userInfo.social === 'HOME'"
            />
            <img
              src="@/assets/img/naver-login.png"
              alt="profile_image"
              class="shadow-sm w-100 border-radius-lg"
              v-if="userInfo.social === 'NAVER'"
            />
            <img
              src="@/assets/img/kakao-logo.png"
              alt="profile_image"
              class="shadow-sm w-100 border-radius-lg"
              v-if="userInfo.social === 'KAKAO'"
            />
          </div>
        </div>
        <div class="col-auto my-auto">
          <div class="h-100">
            <h5 class="mb-1">{{ userInfo.name }}</h5>
            <p class="mb-0 text-sm font-weight-bold">{{ userInfo.id }}</p>
          </div>
        </div>

        <div
          class="mx-auto mt-3 col-lg-3 col-md-6 my-sm-auto ms-sm-auto me-sm-0"
        >
          <div class="nav-wrapper position-relative end-0">
            <ul
              class="pt-5 px-1 bg-transparent nav nav-pills nav-fill"
              role="tablist"
            >
              <li class="nav-item px-1 py-1 mb-0" id="qna-link">
                <span
                  v-if="checkUserInfo.id === 'admin'"
                  @click="moveToQnAAdmin"
                  style="margin-left: 8px"
                  >Q&A</span
                >
                <span v-else @click="moveToQnA" style="margin-left: 8px"
                  >Q&A</span
                >
              </li>
              <li class="nav-item px-1 py-1 mb-0" id="qna-link">
                <span @click="onClickChagePassword" style="margin-left: 8px"
                  >비밀번호 변경</span
                >
              </li>
              <li class="nav-item px-1 py-1 mb-0" id="qna-link">
                <span @click="onClickLogout" style="margin-left: 8px"
                  >로그아웃</span
                >
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="py-4 container-fluid">
    <div class="mt-3 row">
      <div class="mt-4 col-12 col-md-6 col-xl-4 mt-md-0">
        <profile-info-card
          title="내 정보"
          description=""
          :info="{
            social:
              checkUserInfo.social == 'HOME'
                ? '서비스 가입 계정'
                : checkUserInfo.social + ' 소셜 로그인 계정',
            fullName: checkUserInfo.name,
            mobile: checkUserInfo.phone,
            email: checkUserInfo.email,
            location:
              checkUserInfo.address +
              ' ' +
              (checkUserInfo.detailAddress == null
                ? ''
                : checkUserInfo.detailAddress),
          }"
          :action="{ route: '/password-check' }"
        />
      </div>
      <div class="mt-4 col-12 col-xl-8 mt-xl-0">
        <like-table></like-table>
      </div>
    </div>
  </div>
</template>

<script>
import ProfileInfoCard from "./components/ProfileInfoCard.vue";
import LikeTable from "./components/LikeTable.vue";
import { mapState, mapActions, mapMutations, mapGetters } from "vuex";

const userStore = "userStore"; // 누구의 메소드

export default {
  name: "ProfileOverview",
  components: {
    ProfileInfoCard,
    LikeTable,
  },
  data() {
    return {
      showMenu: false,
      user: {},
    };
  },
  computed: {
    ...mapState(userStore, ["isLogin", "userInfo", "isSocialLogin"]),
    ...mapGetters(userStore, ["checkUserInfo"]),
  },
  methods: {
    moveToQnAAdmin() {
      this.$router.push({ name: "QnAAdmin" });
    },
    moveToQnA() {
      this.$router.push({ name: "QnA" });
    },
    onClickChagePassword() {
      this.$router.push({ name: "Change Password" });
    },

    ...mapActions(userStore, ["userLogout"]),
    ...mapMutations(userStore, ["SET_IS_LOGIN", "SET_USER_INFO"]),
    onClickLogout() {
      console.log(this.userInfo);
      console.log(this.isLogin);
      this.SET_IS_LOGIN(false);
      // this.SET_USER_INFO(null);
      sessionStorage.removeItem("access-token");
      // if (this.$route.path != "/") this.$router.push({ name: "main" });
      console.log(this.userInfo.id);
      //vuex actions에서 userLogout 실행(Backend에 저장 된 리프레시 토큰 없애기
      //+ satate에 isLogin, userInfo 정보 변경)
      // this.$store.dispatch("userLogout", this.userInfo.userid);
      this.userLogout(this.userInfo.id);
      sessionStorage.removeItem("access-token"); //저장된 토큰 없애기
      sessionStorage.removeItem("refresh-token"); //저장된 토큰 없애기
      if (this.$route.path != "/") this.$router.push({ name: "/" });
      this.SET_IS_LOGIN(false);
      console.log(this.isLogin);
    },
  },
  mounted() {
    this.$store.state.isAbsolute = true;
  },
  beforeUnmount() {
    this.$store.state.isAbsolute = false;
  },
};
</script>

<style scoped>
#qna-link {
  font-weight: bold;
  cursor: pointer;
}
li > span {
  font-size: 15px;
}
</style>
