<template>
  <nav style="--bs-breadcrumb-divider: '>'" aria-label="breadcrumb">
    <ol class="breadcrumb" style="background-color: transparent">
      <li class="breadcrumb-item">나의 Q&A</li>
      <li class="breadcrumb-item active" aria-current="page">등록</li>
    </ol>
  </nav>
  <div class="qna-container">
    <label>제목</label>
    <SoftInput
      id="title"
      placeholder="제목을 입력하세요."
      :value="title"
      @input="handleOnChange"
    />

    <SoftTextarea
      id="content"
      text="내용"
      :readOnly="readOnly"
      :value="content"
      @input="handleOnChange"
      placeholder="내용을 입력하세요.(500자 내외)"
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
import SoftInput from "@/components/SoftInput.vue";
import { mapGetters, mapActions, mapMutations } from "vuex";
const userStore = "userStore";
const qnaStore = "qnaStore";
export default {
  name: "QnAWrite",
  components: {
    SoftTextarea,
    SoftButton,
    SoftInput,
  },

  data() {
    return {
      title: "",
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
      const { id, value } = e.target;
      id === "title" ? (this.title = value) : (this.content = value);
    },

    handleOnClickRegisterBtn() {
      if (!this.title || !this.content) {
        alert("내용을 입력해주세요!");
        return;
      }

      // API 호출
      const nQnA = {
        content: this.content,
        title: this.title,
        userId: this.checkUserInfo.id,
      };
      this.registerQnA(nQnA);
      this.SET_MESSAGE("등록되었습니다:)");

      if (this.curMsg.length !== 0) {
        alert(this.curMsg);
      }
      this.moveToList();
    },

    moveToList() {
      this.$router.push({ name: "QnAList" });
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
