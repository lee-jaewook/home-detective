<template>
  <main class="mt-0 main-content main-content-bg">
    <section>
      <div class="page-header min-vh-75">
        <div class="container">
          <div class="row">
            <div class="mx-auto col-xl-4 col-lg-5 col-md-6 d-flex flex-column">
              <div class="mt-8 card card-plain">
                <div class="pb-0 card-header text-start">
                  <h3 class="font-weight-bolder text-success text-gradient">
                    Welcome back
                  </h3>
                  <p class="mb-0">Enter your ID and password to sign in</p>
                </div>
                <div class="card-body">
                  <form role="form" class="text-start">
                    <label>ID</label>

                    <soft-input
                      id="id"
                      type="text"
                      placeholder="ID"
                      name="uid"
                      :value="user.id"
                      @keyup.enter="confirm"
                      @searchchange="searchChangeFunc"
                    />

                    <label>Password</label>

                    <soft-input
                      id="pass"
                      ß
                      type="password"
                      placeholder="Password"
                      name="password"
                      :value="user.pass"
                      @keyup.enter="confirm"
                      @searchchange="searchChangeFunc"
                    />

                    <p
                      v-if="isLoginError"
                      class="mx-auto mb-2 text-sm text-danger"
                    >
                      아이디(로그인 전용 아이디) 또는 비밀번호를 잘못
                      입력했습니다. <br />
                      입력하신 내용을 다시 확인해주세요.
                    </p>

                    <div class="form-check form-switch">
                      <input
                        id="rememberMe"
                        class="form-check-input"
                        type="checkbox"
                        name="rememberMe"
                        v-model="isRemember"
                      />
                      <label class="form-check-label">
                        <slot />
                        Remember me
                      </label>
                    </div>

                    <div class="text-center">
                      <soft-button
                        class="my-4 mb-2"
                        variant="gradient"
                        color="success"
                        full-width
                        @click.prevent="confirm"
                        >Sign in
                      </soft-button>
                    </div>
                  </form>
                </div>
                <div class="px-1 pt-0 pb-0 text-center card-footer px-lg-2">
                  <p class="mx-auto mb-2 text-sm">
                    Don't have an account?
                    <router-link
                      :to="{ name: 'Sign Up' }"
                      class="text-success text-gradient font-weight-bold"
                    >
                      Sign up</router-link
                    >
                  </p>
                  <p class="mx-auto mb-2 text-sm">
                    Forgot your password?
                    <router-link
                      :to="{ name: 'Find Pass' }"
                      class="text-danger text-gradient font-weight-bold"
                    >
                      find</router-link
                    >
                  </p>
                </div>
                <div
                  class="px-3 row px-xl-5 px-sm-4 d-flex justify-content-center"
                >
                  <div class="my-3 position-relative text-center">
                    <p
                      class="text-sm font-weight-bold mb-2 text-secondary text-border d-inline z-index-2 bg-white px-3"
                    >
                      or
                    </p>
                  </div>
                  <div class="px-1 col-3 mx-2">
                    <a
                      class="btn btn-outline-light w-100"
                      :href="naverLoginURL"
                    >
                      <img
                        src="../assets/img/logos/naver.png"
                        style="width: 32px; height: 32px"
                      />
                    </a>
                  </div>
                  <div class="px-1 col-3 mx-2">
                    <a
                      class="btn btn-outline-light w-100"
                      :href="kakaoLoginURL"
                    >
                      <img
                        src="../assets/img/logos/kakao.png"
                        style="width: 32px; height: 32px"
                      />
                    </a>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-md-6">
              <div
                class="top-0 oblique position-absolute h-100 d-md-block d-none me-n8"
              >
                <div
                  class="bg-cover oblique-image position-absolute fixed-top ms-auto h-100 z-index-0 ms-n6"
                  :style="{
                    backgroundImage:
                      'url(' +
                      require('@/assets/img/curved-images/seoul-apt2.png') +
                      ')',
                  }"
                ></div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  </main>
  <app-footer />
</template>

<script>
import AppFooter from "@/examples/PageLayout/Footer.vue";
import SoftInput from "@/components/SoftInput.vue";
import SoftButton from "@/components/SoftButton.vue";
const body = document.getElementsByTagName("body")[0];
import { mapMutations, mapState, mapActions } from "vuex";

const userStore = "userStore"; // 누구의 메소드
export default {
  name: "SignIn",
  components: {
    AppFooter,
    SoftInput,
    SoftButton,
  },
  data() {
    return {
      user: {
        id: "",
        pass: "",
      },
      CLIENT_ID: "2QFr6GZqkaJBGXB59bYS",
      redirectURL: "http://localhost/naver/auth",
      //  FIXME state 값 random string 으로 변경
      state: "hLiDdL2uhPtsftcU",
      naverLoginURL:
        "https://nid.naver.com/oauth2.0/authorize?response_type=code",
      kakaoLoginURL: "https://kauth.kakao.com/oauth/authorize",
    };
  },
  computed: {
    ...mapState(userStore, ["isLogin", "isLoginError", "userInfo"]),
    isRemember: {
      get() {
        return this.$store.state.userStore.isRemember;
      },
      set(value) {
        this.SET_IS_REMEMBER(value);
      },
    },
  },
  created() {
    this.toggleEveryDisplay();
    this.toggleHideConfig();
    this.user.id = localStorage.getItem("user_id");
    body.classList.remove("bg-gray-100");

    // setup naverLoginURL
    this.naverLoginURL += "&client_id=" + this.CLIENT_ID;
    this.naverLoginURL +=
      "&redirect_uri=" + encodeURIComponent(this.redirectURL);
    this.naverLoginURL += "&state=" + this.state;

    // setup kakaoLoginURL
    this.kakaoLoginURL += "?client_id=" + "af455fc306697ff0091a108bd8a75281";
    this.kakaoLoginURL +=
      "&redirect_uri=" + encodeURIComponent("http://localhost/kakao/auth");
    this.kakaoLoginURL += "&response_type=code";
  },
  beforeUnmount() {
    this.toggleEveryDisplay();
    this.toggleHideConfig();
    body.classList.add("bg-gray-100");
  },
  methods: {
    ...mapMutations(["toggleEveryDisplay", "toggleHideConfig"]),
    ...mapMutations(userStore, ["SET_IS_REMEMBER", "SET_REMEMBER_ID"]),
    ...mapActions(userStore, ["userConfirm", "getUserInfo"]),
    searchChangeFunc(eid, evalue) {
      this.user[eid] = evalue;
    },
    async confirm() {
      await this.userConfirm(this.user);
      let token = sessionStorage.getItem("access-token");
      console.log("1. confirm() token >> " + token);
      if (this.isLogin) {
        await this.getUserInfo(token);
        console.log("4. confirm() userInfo :: ", this.userInfo);
        this.$router.push({ name: "/" });
      }
    },
  },
};
</script>
