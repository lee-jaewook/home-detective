<template>
  <nav style="--bs-breadcrumb-divider: '>'" aria-label="breadcrumb">
    <ol class="breadcrumb" style="background-color: transparent">
      <li class="breadcrumb-item">공지사항</li>
      <li class="breadcrumb-item active" aria-current="page">상세 조회</li>
    </ol>
  </nav>
  <div class="notice-container">
    <div class="notice-header-wrapper">
      <h5>{{ curNotice.title }}</h5>
      <span>
        <label
          class="reg-date-label"
          style="margin-right: 10px; font-weight: normal"
          >등록일 {{ curNotice.regTime }}</label
        >
        <label class="reg-date-label" style="font-weight: normal"
          >조회 {{ curNotice.viewCount }}</label
        >
      </span>
    </div>

    <div id="content" v-html="curNotice.content"></div>

    <div v-if="isAdmin" class="d-flex" style="justify-content: center">
      <SoftButton
        color="dark"
        variant="gradient"
        style="margin-right: 12px"
        @click="moveToModify"
      >
        수정
      </SoftButton>
      <SoftButton
        color="danger"
        variant="gradient"
        style="margin-right: 12px"
        @click="handleOnClickDeleteBtn"
      >
        삭제
      </SoftButton>
      <SoftButton color="light" variant="gradient" @click="moveToList"
        >목록
      </SoftButton>
    </div>
    <div v-else class="d-flex" style="justify-content: center">
      <SoftButton color="light" variant="gradient" @click="moveToList"
        >목록
      </SoftButton>
    </div>
  </div>
</template>

<script>
import SoftButton from "@/components/SoftButton.vue";
import { mapState, mapActions, mapGetters, mapMutations } from "vuex";
const noticeStore = "noticeStore";
const userStore = "userStore";
export default {
  name: "NoticeDetail",
  data() {
    return {
      readOnly: true,
    };
  },
  components: {
    SoftButton,
  },
  computed: {
    ...mapState(userStore, ["isAdmin"]),
    ...mapGetters(noticeStore, ["curNotice", "curMsg"]),
    ...mapGetters(userStore, ["checkUserInfo"]),
  },
  methods: {
    ...mapActions(noticeStore, ["getOneNotice", "deleteNotice"]),
    ...mapMutations(noticeStore, ["SET_MESSAGE"]),
    moveToModify() {
      this.$router.push({
        name: "NoticeModify",
        params: { num: this.$route.params.num },
      });
    },
    handleOnClickDeleteBtn() {
      this.deleteNotice(this.curNotice.id);
      this.SET_MESSAGE("삭제되었습니다:)");
      alert(this.curMsg);
      this.moveToList();
    },
    moveToDetails(num) {
      this.$router.push({ name: "NoticeDetail", params: { num: num } });
    },
    moveToList() {
      this.$router.push({ name: "NoticeList" });
    },
  },
  created() {
    // api 호출
    this.getOneNotice(this.$route.params.num);
  },
};
</script>

<style scoped>
#content {
  width: 100%;
  min-height: 200px;
  padding: 0.5rem 0.75rem;
  font-size: 0.875rem;
  font-weight: 400;
  line-height: 1.4rem;
  color: #495057;
  border-radius: 0.5rem;
  margin-bottom: 12px;
  transition: box-shadow 0.15s ease, border-color 0.15s ease;
}
.notice-container {
  width: 80%;
  height: 100%;
  min-height: 500px;
  display: flex;
  flex-direction: column;
  border-radius: 0.75rem;
  padding: 32px;
  margin: 0 auto;
}

.notice-header-wrapper {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-bottom: 12px;
  border-bottom: 2px solid #e9ecef;
  margin-bottom: 32px;
}
.reg-date-label {
  font-size: 0.89rem;
  opacity: 0.6;
}
</style>
