<template>
  <div
    :id="id"
    :data-order="['data-order']"
    :class="['checkbox-wrapper']"
    @click="clickCheckbox()"
    :checked="isChecked"
  >
    <i
      :class="setClassName"
      :style="[
        isChecked ? { color: '#596cff' } : { color: '#b8c3c7' },
        { textAlign: 'center' },
      ]"
    ></i>
    <label
      v-text="name"
      :style="[isChecked ? { color: '#596cff' } : { color: '#b8c3c7' }]"
    ></label>
  </div>
</template>

<script>
import { mapActions, mapGetters, mapMutations, mapState } from "vuex";
const aptStore = "aptStore";
const userStore = "userStore";
const nearInfoStore = "nearInfoStore";

export default {
  name: "SearchCheckbox",
  data() {
    return {
      isChecked: this.checked,
    };
  },
  props: {
    id: {
      type: String,
      default: "",
    },
    name: {
      type: String,
      default: "",
    },
    iconName: {
      type: String,
      default: "",
    },
    checked: {
      type: Boolean,
      default: false,
    },
    "data-order": {
      type: String,
      default: "",
    },
  },
  computed: {
    ...mapGetters(userStore, ["checkUserInfo"]),
    ...mapGetters(nearInfoStore, ["curSidoName", "curGugunName"]),
    ...mapState(aptStore, ["apt", "isShowSearchResult", "isPressedLikedBtn"]),

    setClassName() {
      return `fa fa-solid ${this.iconName}`;
    },
  },
  methods: {
    ...mapActions(aptStore, ["getLikedAptList"]),
    ...mapActions(nearInfoStore, ["getNearSchoolList"]),
    ...mapMutations(nearInfoStore, [
      "IS_PRESSED_SCHOOL_BTN",
      "IS_PRESSED_STATION_BTN",
      "IS_PRESSED_HOSPITAL_BTN",
    ]),
    ...mapMutations(aptStore, [
      "IS_PRESSED_LIKED_BTN",
      "SET_SHOW_SEARCH_RESULT",
      "SET_SEARCH_RESULT_TYPE",
    ]),
    clickCheckbox() {
      this.isChecked = !this.isChecked;

      if (this.name === "관심매물") {
        if (!this.checkUserInfo) {
          alert("로그인이 필요한 서비스입니다:)");
          this.isChecked = false;
          return;
        }

        if (!this.isChecked) {
          this.SET_SHOW_SEARCH_RESULT(false);
          return;
        }
        this.getLikedAptList(this.checkUserInfo.id);
        this.IS_PRESSED_LIKED_BTN(this.isChecked);
        this.SET_SEARCH_RESULT_TYPE("like");
        this.SET_SHOW_SEARCH_RESULT(true);
      } else if (this.name === "교육시설") {
        console.log("교육시설! ", this.isChecked);
        if (this.isChecked) {
          this.IS_PRESSED_SCHOOL_BTN(true);
        } else {
          this.IS_PRESSED_SCHOOL_BTN(false);
        }
      } else if (this.name === "대중교통") {
        if (this.isChecked) {
          console.log("대중교통 눌림!");
          this.IS_PRESSED_STATION_BTN(true);
        } else {
          this.IS_PRESSED_STATION_BTN(false);
        }
      } else if (this.name === "병원") {
        if (this.isChecked) {
          console.log("병원 눌림!");
          this.IS_PRESSED_HOSPITAL_BTN(true);
        } else {
          this.IS_PRESSED_HOSPITAL_BTN(false);
        }
      }
    },
  },
};
</script>

<style>
.checkbox-wrapper {
  cursor: pointer;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.checkbox-wrapper label {
  margin: 0px;
  margin-top: 4px;
  font-size: 0.75rem;
  color: #b8c3c7;
}
.isChecked {
  color: red;
}
</style>
