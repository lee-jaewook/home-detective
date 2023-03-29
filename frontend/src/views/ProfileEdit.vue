<template>
  <div
    class="pt-5 m-3 page-header align-items-start min-vh-50 pb-11 border-radius-lg"
    :style="{
      backgroundImage: 'url(' + require('@/assets/img/curved-images/seoul-apt.jpg') + ')',
    }"
  >
    <span class="mask bg-gradient-dark opacity-6"></span>
  </div>
  <div class="container">
    <div class="row mt-lg-n11 mt-md-n11 mt-n10 justify-content-center">
      <div class="mx-auto col-xl-5 col-lg-7 col-md-8">
        <div class="card z-index-0">
          <div class="pt-4 pb-0 text-center card-header">
            <h5>회원 정보 수정</h5>
          </div>
          <div class="card-body grid gap-6 mb-0 sm:grid-cols-2">
            <form role="form">
              <div class="row">
                <div class="col-6">
                  <label>ID</label>
                  <soft-input id="uid" type="text" placeholder="ID" :disabled="true" :value="uid.value" :success="uid.valid == 1" :error="uid.valid == 2" @searchchange="searchChangeFunc" />
                </div>
              </div>
              <div class="row">
                <div class="col-6">
                  <label>이름</label>
                  <soft-input id="name" type="text" aria-label="Nickname" :value="name.value" :success="name.valid == 1" :error="name.valid == 2" @searchchange="searchChangeFunc" @clickevent="init" />
                </div>
                <div class="col-6">
                  <label>핸드폰</label>
                  <soft-input
                    id="phone"
                    type="tel"
                    placeholder="-를 제외하고 입력해주세요."
                    aria-label="phone"
                    :value="phone.value"
                    :success="phone.valid == 1"
                    :error="phone.valid == 2"
                    @searchchange="searchChangeFunc"
                    @clickevent="init"
                    :maxlength="'13'"
                  />
                </div>
              </div>
              <div class="mb-3">
                <label>이메일</label>
                <soft-input
                  id="email"
                  type="email"
                  placeholder="example@domain.com"
                  aria-label="Email"
                  :value="email.value"
                  :success="email.valid == 1"
                  :error="email.valid == 2"
                  @searchchange="searchChangeFunc"
                  @clickevent="init"
                />
              </div>
              <div class="row">
                <label>우편번호</label>
                <div class="col-6">
                  <soft-input id="postCode" :disabled="true" type="text" aria-label="postCode" :value="postCode.value" @searchchange="searchChangeFunc" @click="addressSearch" />
                </div>
                <div class="col-6">
                  <div class="row d-flex justify-content-center">
                    <div class="col-7">
                      <soft-button color="light" full-width variant="gradient" class="w-25 mb-3" @click.prevent="addressSearch">우편번호 찾기</soft-button>
                    </div>
                  </div>
                </div>
              </div>
              <div class="mb-3">
                <label>주소</label>
                <soft-input id="address" :disabled="true" type="text" aria-label="address" :value="address.value" @searchchange="searchChangeFunc" />
              </div>
              <div class="mb-3">
                <label>상세주소</label>
                <soft-input id="detailAddress" type="text" aria-label="detailAddress" :value="detailAddress.value" @searchchange="searchChangeFunc" />
              </div>
              <div class="text-center mt-6 mx-6">
                <soft-button color="dark" full-width variant="gradient" class="my-1 mb-3" :disable="!validAll" @click.prevent="submit">수정</soft-button>
                <soft-button color="light" full-width variant="gradient" class="my-1 mb-0" @click.prevent="this.$router.push({ name: 'Profile' })">취소</soft-button>
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
import SoftInput from "@/components/SoftInput.vue";
import SoftButton from "@/components/SoftButton.vue";

import { mapState, mapActions, mapMutations } from "vuex";

export default {
  name: "SignupBasic",
  data() {
    return {
      checked: false,
      uid: {
        value: "",
        valid: 0,
      },
      name: {
        value: "",
        valid: 0,
      },
      email: {
        value: "",
        valid: 0,
      },
      phone: {
        value: "",
        valid: 0,
      },
      address: {
        value: "",
        valid: 0,
      },
      postCode: {
        value: "",
        valid: 0,
      },
      detailAddress: {
        value: "",
        valid: 0,
      },
    };
  },
  components: {
    AppFooter,
    SoftInput,
    SoftButton,
  },
  created() {
    this.toggleEveryDisplay();
    this.toggleHideConfig();
    this.uid.value = this.userInfo.id;
    this.name.value = this.userInfo.name;
    this.email.value = this.userInfo.email;
    this.phone.value = this.userInfo.phone;
    this.postCode.value = this.userInfo.postCode;
    this.address.value = this.userInfo.address;
    this.detailAddress.value = this.userInfo.detailAddress;
  },
  beforeUnmount() {
    this.toggleEveryDisplay();
    this.toggleHideConfig();
  },
  computed: {
    validAll() {
      return this.name.valid != 2 && this.phone.valid != 2 && this.email.valid != 2;
      // return this.email.valid == 1 && this.password.valid == 1 && this.confirmPassword.valid == 1 && this.name.valid == 1 && this.checked;
    },
    ...mapState("userStore", ["isLogin", "userInfo"]),
  },
  methods: {
    ...mapMutations(["toggleEveryDisplay", "toggleHideConfig"]),
    ...mapActions("userStore", ["userUpdateInfo", "getUserInfo"]),
    init(eid) {
      if (this.$data[eid].valid == 0) {
        this.$data[eid].value = "";
      }
    },
    addressSearch() {
      new window.daum.Postcode({
        oncomplete: (data) => {
          console.log(data);
          var roadAddr = data.roadAddress; // 도로명 주소 변수
          var extraRoadAddr = ""; // 참고 항목 변수

          // 법정동명이 있을 경우 추가한다. (법정리는 제외)
          // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
          if (data.bname !== "" && /[동|로|가]$/g.test(data.bname)) {
            extraRoadAddr += data.bname;
          }
          // 건물명이 있고, 공동주택일 경우 추가한다.
          if (data.buildingName !== "" && data.apartment === "Y") {
            extraRoadAddr += extraRoadAddr !== "" ? ", " + data.buildingName : data.buildingName;
          }
          // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
          if (extraRoadAddr !== "") {
            extraRoadAddr = " (" + extraRoadAddr + ")";
          }
          this.postCode.value = data.zonecode;
          this.address.value = roadAddr;
          this.detailAddress.value = "";
        },
      }).open();
    },
    submit() {
      let user = {
        id: this.uid.value,
        name: this.name.value,
        phone: this.phone.value,
        email: this.email.value,
        postCode: this.postCode.value,
        address: this.address.value,
        detailAddress: this.detailAddress.value,
        social: "HOME",
      };
      console.log("**** 요청 값 *****");
      console.log(user);
      this.userUpdateInfo(user);

      let token = sessionStorage.getItem("access-token");

      if (this.isLogin) {
        this.getUserInfo(token);
        alert("수정되었습니다.");
        this.$router.push({ name: "Profile" });
      }
    },
    checkboxclick(checked) {
      this.checked = checked;
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
      console.log(eid, " = ", this.$data[eid].value);

      if (eid == "uid") {
        if (evalue.length >= 6) {
          this.uid.valid = 1;
        } else if (evalue.length > 0) {
          this.uid.valid = 2;
        } else {
          this.uid.valid = 0;
        }
      } else if (eid == "email") {
        // eslint-disable-next-line
        var regExp = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
        var test = regExp.test(evalue);
        if (evalue.length == 0) {
          this.$data[eid].valid = 0;
        } else if (test) {
          this.$data[eid].valid = 1;
        } else {
          this.$data[eid].valid = 2;
        }
      } else if (eid == "name") {
        if (evalue.length >= 3) {
          this.$data[eid].valid = 1;
        } else if (evalue.length > 0) {
          this.$data[eid].valid = 2;
        } else {
          this.$data[eid].valid = 0;
        }
      } else if (eid == "phone") {
        var tmp = this.autoHypeon(evalue);
        this.$data[eid].value = tmp;
        if (tmp.length >= 13) {
          this.$data[eid].valid = 1;
        } else if (tmp.length > 0) {
          this.$data[eid].valid = 2;
        } else {
          this.$data[eid].valid = 0;
        }
      }
    },
    autoHypeon(str) {
      str = str.replace(/[^0-9]/g, "");
      var tmp = "";
      if (str.length < 4) {
        return str;
      } else if (str.length < 7) {
        tmp += str.substr(0, 3);
        tmp += "-";
        tmp += str.substr(3);
        return tmp;
      } else if (str.length < 11) {
        tmp += str.substr(0, 3);
        tmp += "-";
        tmp += str.substr(3, 3);
        tmp += "-";
        tmp += str.substr(6);
        return tmp;
      } else {
        console.log("???");
        tmp += str.substr(0, 3);
        tmp += "-";
        tmp += str.substr(3, 4);
        tmp += "-";
        tmp += str.substr(7);
        return tmp;
      }
    },
  },
};
</script>
