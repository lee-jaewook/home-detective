<template>
  <nav style="--bs-breadcrumb-divider: '>'" aria-label="breadcrumb">
    <ol class="breadcrumb" style="background-color: transparent">
      <li class="breadcrumb-item">관리자 Q&A</li>
      <li class="breadcrumb-item active" aria-current="page">등록</li>
    </ol>
  </nav>
  <div class="qna-container">
    <SoftTextarea
      id="content"
      text="답변"
      :readOnly="readOnly"
      :value="content"
      @input="handleOnChange"
      placeholder="답변 내용을 입력하세요.(500자 내외)"
    ></SoftTextarea>

    <div class="d-flex" style="justify-content: center">
      <SoftButton
        color="dark"
        variant="gradient"
        style="margin-right: 12px"
        @click="handleOnClickRegisterBtn"
      >
        등록
      </SoftButton>
      <SoftButton color="light" variant="gradient" @click="moveToList"
        >취소
      </SoftButton>
    </div>
  </div>
</template>

<script>
import SoftTextarea from "@/components/SoftTextarea.vue";
import SoftButton from "@/components/SoftButton.vue";
import { mapGetters, mapActions, mapMutations } from "vuex";
const userStore = "userStore";
const qnaStore = "qnaStore";
export default {
  name: "QnAdminWrite",
  components: {
    SoftTextarea,
    SoftButton,
  },

  data() {
    return {
      content: "",
      readOnly: false,
    };
  },

  computed: {
    ...mapGetters(userStore, ["checkUserInfo"]),
    ...mapGetters(qnaStore, ["curMsg"]),
  },
  methods: {
    ...mapActions(qnaStore, ["registerQnA"]),
    ...mapMutations(qnaStore, ["SET_MESSAGE"]),
    handleOnChange(e) {
      const { value } = e.target;
      this.content = value;
    },

    handleOnClickRegisterBtn() {
      if (!this.content) {
        alert("내용을 입력해주세요!");
        return;
      }

      // API 호출
      const nQnA = {
        content: this.content,
        title: "",
        userId: "admin",
        parentId: this.$route.params.num,
      };
      console.log(nQnA);
      this.registerQnA(nQnA);
      this.SET_MESSAGE("등록되었습니다:)");

      if (this.curMsg.length !== 0) {
        alert(this.curMsg);
      }
      this.moveToList();
    },

    moveToList() {
      this.$router.push({ name: "QnAAdmin" });
    },
  },
};
</script>

<style scoped>
.qna-container {
  height: 100%;
  min-height: 200px;
  display: flex;
  flex-direction: column;
  background-color: #fff;
  border-radius: 0.75rem;
  box-shadow: 0 2px 12px 0 rgb(0 0 0 / 16%);
  padding: 16px 32px;
}

a:hover {
  color: #333;
}
</style>
