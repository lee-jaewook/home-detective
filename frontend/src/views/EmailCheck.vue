<template>
  <div
    class="pt-5 m-3 page-header align-items-start min-vh-50 pb-11 border-radius-lg"
    :style="{
      backgroundImage: 'url(' + require('@/assets/img/curved-images/curved6.jpg') + ')',
    }"
  >
    <span class="mask bg-gradient-dark opacity-6"></span>
  </div>
  <div class="container">
    <div class="row mt-lg-n12 mt-md-n12 mt-n10 justify-content-center">
      <div class="mx-auto col-xl-5 col-lg-7 col-md-8">
        <div class="card z-index-0">
          <div class="pt-4 text-center card-header">
            <h5>{{ userEmail }}에</h5>
            <h5>전송된 코드를 입력하세요.</h5>
          </div>
          <div class="card-body grid gap-6 mb-3 sm:grid-cols-2">
            <form role="form">
              <div class="form-group mx-3">
                <input id="code" type="password" class="form-control" name="code" style="transition: none !important" autocomplete="off" />
              </div>
              <div class="text-center mt-4 mx-7">
                <soft-button color="dark" full-width variant="gradient" class="my-4 mb-2" @click.prevent="signUp">제출</soft-button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
  <app-footer />
</template>

<script>
import AppFooter from "@/examples/PageLayout/Footer.vue";
import SoftButton from "@/components/SoftButton.vue";

import { mapState, mapMutations } from "vuex";

const userStore = "userStore";
export default {
  name: "EmailCheck",

  data() {
    return {};
  },
  components: {
    AppFooter,
    SoftButton,
  },
  created() {
    this.toggleEveryDisplay();
    this.toggleHideConfig();
  },
  beforeUnmount() {
    this.toggleEveryDisplay();
    this.toggleHideConfig();
  },
  computed: {
    ...mapState(userStore, ["userInfo"]),
    // validAll() {
    //   return this.uid.valid == 1 && this.email.valid == 1 && this.password.valid == 1 && this.confirmPassword.valid == 1 && this.name.valid == 1 && this.checked;
    // },
    userEmail() {
      var star = "***";
      return this.userInfo.email.substring(0, 3) + star + "@" + star + this.userInfo.email.slice(-4);
    },
  },
  methods: {
    ...mapMutations(["toggleEveryDisplay", "toggleHideConfig"]),
  },
};
</script>
