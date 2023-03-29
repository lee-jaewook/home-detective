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
          <div class="pt-6 text-center card-header" style="height: 100px">
            <h5>비밀번호 변경</h5>
            <p v-if="isNot" class="mx-auto mb-2 text-sm text-danger">비밀번호를 정확히 입력해 주세요.</p>
          </div>
          <div class="card-body grid gap-6 mt-4 mb-3 sm:grid-cols-2">
            <form role="form">
              <div class="form-group mx-3">
                <div class="mb-3">
                  <soft-input
                    id="oldpassword"
                    type="password"
                    placeholder="기존 비밀번호"
                    aria-label="oldpassword"
                    :value="oldpassword.value"
                    :success="oldpassword.valid == 1"
                    :error="oldpassword.valid == 2"
                    @searchchange="searchChangeFunc"
                  />
                </div>
                <div class="mb-3">
                  <soft-input
                    id="password"
                    type="password"
                    placeholder="새 비밀번호"
                    aria-label="Password"
                    :value="password.value"
                    :success="password.valid == 1"
                    :error="password.valid == 2"
                    @searchchange="searchChangeFunc"
                  />
                </div>
                <div class="mb-3">
                  <soft-input
                    id="confirmPassword"
                    type="password"
                    placeholder="새 비밀번호 확인"
                    aria-label="confirmPassword"
                    :value="confirmPassword.value"
                    :success="confirmPassword.valid == 1"
                    :error="confirmPassword.valid == 2"
                    @searchchange="searchChangeFunc"
                  />
                </div>
              </div>
              <div class="text-center mt-5 mx-7">
                <soft-button color="dark" full-width variant="gradient" class="my-1 mb-2" :disable="!validAll" @click.prevent="check">제출</soft-button>
                <soft-button color="light" full-width variant="gradient" class="my-1 mb-0" @click.prevent="">취소</soft-button>
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
import SoftInput from "@/components/SoftInput.vue";
import { changePassword } from "@/util/api/user";
import { mapState, mapMutations, mapActions } from "vuex";

const userStore = "userStore";
export default {
  name: "EmailCheck",

  data() {
    return {
      inputcode: "",
      isNot: false,
      isCorrect: false,
      oldpassword: {
        value: "",
        valid: 0,
      },
      password: {
        value: "",
        valid: 0,
      },
      confirmPassword: {
        value: "",
        valid: 0,
      },
    };
  },
  components: {
    AppFooter,
    SoftButton,
    SoftInput,
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
    validAll() {
      return this.password.valid == 1 && this.confirmPassword.valid == 1;
    },

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
    ...mapActions(userStore, ["userInfo"]),
    check() {
      console.log("호출함!!!!");
      console.log(this.oldpassword.value);
      changePassword(
        this.userInfo.id,
        this.oldpassword.value,
        this.password.value,
        ({ data }) => {
          if (data === "success") {
            this.isCorrect = true;
            this.isNot = false;
            alert("변경되었습니다.");
            this.$router.push({ name: "Profile" });
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
    blurevent(eid, evalue) {
      console.log(evalue);
      if (evalue === "" || this.$data[eid].valid != 1) {
        this.$data[eid].value = this.userInfo[eid];
        this.$data[eid].valid = 0;
      }
    },
    searchChangeFunc(eid, evalue) {
      this.$data[eid].value = evalue;
      // console.log(eid, " = ", this.$data[eid].value);

      if (eid == "password") {
        if (evalue.length >= 4) {
          this.$data[eid].valid = 1;
        } else if (evalue.length > 0) {
          this.$data[eid].valid = 2;
        } else {
          this.$data[eid].valid = 0;
        }
        if (this.confirmPassword.value != "") {
          // console.log("1 ???");
          if (this.password.value != this.confirmPassword.value) {
            // console.log("2 ???");
            this.confirmPassword.valid = 2;
          } else if (this.password.value == this.confirmPassword.value) {
            this.confirmPassword.valid = 1;
          }
        }
      } else if (eid == "confirmPassword") {
        if (evalue == this.password.value) {
          this.$data[eid].valid = 1;
        } else if (evalue.length > 0) {
          this.$data[eid].valid = 2;
        } else {
          this.$data[eid].valid = 0;
        }
      }
    },
  },
};
</script>
