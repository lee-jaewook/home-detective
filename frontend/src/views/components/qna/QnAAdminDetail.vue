<template>
  <nav style="--bs-breadcrumb-divider: '>'" aria-label="breadcrumb">
    <ol class="breadcrumb" style="background-color: transparent">
      <li class="breadcrumb-item">관리자 Q&A</li>
      <li class="breadcrumb-item active" aria-current="page">상세 조회</li>
    </ol>
  </nav>
  <div class="qna-container">
    <div class="qna-question-wrapper">
      <soft-badge
        v-if="curQnA.countReply !== '0'"
        color="secondary"
        variant="gradient"
        size="sm"
        >답변 완료</soft-badge
      >
      <soft-badge v-else color="info" variant="gradient" size="sm"
        >진행중</soft-badge
      >
      <h5>{{ curQnA.title }}</h5>
      <span style="color: #344767; opacity: 0.6; font-size: 0.89rem">{{
        curQnA.regTime
      }}</span>
    </div>
    <div class="qna-question-contents">
      <SoftTextarea
        id="content"
        text="내용"
        :value="curQnA.content"
        :readOnly="readOnly"
      ></SoftTextarea>

      <div class="d-flex" style="justify-content: center">
        <SoftButton
          v-if="curQnA.countReply !== '0'"
          color="dark"
          variant="gradient"
          style="margin-right: 12px"
          @click="moveToModify"
        >
          답변 수정
        </SoftButton>
        <SoftButton
          v-else
          color="dark"
          variant="gradient"
          style="margin-right: 12px"
          @click="moveToWrite"
        >
          답변 등록
        </SoftButton>
        <SoftButton color="light" variant="gradient" @click="moveToList"
          >목록
        </SoftButton>
      </div>
    </div>
  </div>
  <!-- 관리자 -->
  <div
    class="qna-container"
    id="answer-contents"
    v-for="answer in allAnswer"
    :key="answer.id"
  >
    <div class="qna-question-wrapper">
      <span style="color: #344767; opacity: 0.6; font-size: 0.89rem">답변</span>
      <span style="color: #344767; opacity: 0.6; font-size: 0.89rem">{{
        answer.regTime
      }}</span>
    </div>

    <div class="qna-question-contents">
      {{ answer.content }}
    </div>
  </div>
</template>

<script>
import SoftTextarea from "@/components/SoftTextarea.vue";
import SoftBadge from "@/components/SoftBadge.vue";
import SoftButton from "@/components/SoftButton.vue";
import { mapGetters, mapActions, mapMutations } from "vuex";
const qnaStore = "qnaStore";
const userStore = "userStore";
export default {
  name: "QnAAdminDetail",
  components: {
    SoftBadge,
    SoftTextarea,
    SoftButton,
  },
  data() {
    return {
      readOnly: true,
      answer: [],
    };
  },
  computed: {
    ...mapGetters(qnaStore, ["curQnA", "curMsg", "allAnswer"]),
    ...mapGetters(userStore, ["checkUserInfo"]),
  },
  methods: {
    ...mapActions(qnaStore, ["getOneQna", "deleteQnA", "getAllAnswerList"]),
    ...mapMutations(qnaStore, ["SET_MESSAGE", "GET_ALL_ANSWER"]),

    moveToModify() {
      this.$router.push({
        name: "QnAdminModify",
        params: { num: this.$route.params.num },
      });
    },
    moveToWrite() {
      this.$router.push({
        name: "QnAdminWrite",
        params: { num: this.$route.params.num },
      });
    },
    handleOnClickDeleteBtn() {
      this.deleteQnA(this.curQnA.id);
      this.SET_MESSAGE("삭제되었습니다:)");
      alert(this.curMsg);
      this.moveToList();
    },
    moveToList() {
      this.$router.push({ name: "QnAAdmin" });
    },
  },

  created() {
    this.getOneQna(this.$route.params.num);
    console.log("답변:", this.allAnswer);
  },
};
</script>

<style scoped>
.qna-container {
  height: 100%;
  min-height: 120px;
  display: flex;
  flex-direction: column;
  background-color: #fff;
  border-radius: 0.75rem;
  box-shadow: 0 2px 12px 0 rgb(0 0 0 / 16%);
  padding-bottom: 16px;
}

.qna-question-wrapper {
  margin: 0px 32px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 60px;
}

.qna-question-contents {
  margin: 0px 32px;
}

#answer-contents {
  margin-top: 16px !important;
  font-size: 0.875rem;
}
</style>
