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
          <div class="pt-5 text-center card-header" style="height: 100px">
            <h5>계속하려면 비밀번호를 입력해주세요.</h5>
            <p v-if="isNot" class="mx-auto mb-2 text-sm text-danger">
              비밀번호를 잘못 입력했습니다. <br />
              입력하신 내용을 다시 확인해주세요.
            </p>
          </div>
          <div class="card-body grid gap-6 mt-4 mb-3 sm:grid-cols-2">
            <form role="form">
              <div class="form-group mx-3">
                <input id="code" v-model="inputcode" type="password" class="form-control" name="code" style="transition: none !important" autocomplete="off" />
              </div>
              <div class="text-center mt-4 mx-7">
                <soft-button color="dark" full-width variant="gradient" class="my-4 mb-2" @click.prevent="check">제출</soft-button>
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
import { checkPassword } from "@/util/api/user";
import { mapState, mapMutations } from "vuex";

const userStore = "userStore";
export default {
  name: "EmailCheck",

  data() {
    return {
      inputcode: "",
      isNot: false,
      isCorrect: false,
    };
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
    // userEmail() {
    //   var star = "***";
    //   return this.userInfo.email.substring(0, 3) + star + "@" + star + this.userInfo.email.slice(-4);
    // },
  },
  methods: {
    ...mapMutations(["toggleEveryDisplay", "toggleHideConfig"]),
    check() {
      console.log("엥에에?? .  ", this.inputcode);
      checkPassword(
        this.userInfo.id,
        this.inputcode,
        ({ data }) => {
          if (data === "success") {
            this.isCorrect = true;
            this.isNot = false;
            this.$router.push({ name: "Profile Edit" });
          } else {
            console.log("비밀번호 틀림~");
            this.isNot = true;
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};
</script>
