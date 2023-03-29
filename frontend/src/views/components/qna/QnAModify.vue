<template>
  <nav style="--bs-breadcrumb-divider: '>'" aria-label="breadcrumb">
    <ol class="breadcrumb" style="background-color: transparent">
      <li class="breadcrumb-item">나의 Q&A</li>
      <li class="breadcrumb-item active" aria-current="page">수정</li>
    </ol>
  </nav>
  <div class="qna-container">
    <label>제목</label>
    <SoftInput
      id="title"
      placeholder="제목을 입력하세요."
      :value="curQnA.title"
      @input="handleOnChange"
    />

    <SoftTextarea
      id="content"
      text="내용"
      :readOnly="readOnly"
      :value="curQnA.content"
      @input="handleOnChange"
      placeholder="내용을 입력하세요.(500자 내외)"
    ></SoftTextarea>

    <div class="d-flex" style="justify-content: center">
      <SoftButton
        color="dark"
        variant="gradient"
        style="margin-right: 12px"
        @click="saveQnA"
      >
        저장
      </SoftButton>
      <SoftButton color="light" variant="gradient" @click="moveToDetail"
        >취소
      </SoftButton>
    </div>
  </div>
</template>

<script>
import SoftTextarea from "@/components/SoftTextarea.vue";
import SoftButton from "@/components/SoftButton.vue";
import SoftInput from "@/components/SoftInput.vue";
import { mapGetters, mapActions } from "vuex";
const qnaStore = "qnaStore";
const userStore = "userStore";
export default {
  name: "QnAModify",
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
    ...mapGetters(qnaStore, ["curQnA", "curMsg"]),
    ...mapGetters(userStore, ["checkUserInfo"]),
  },

  created() {
    if (this.$route.params.num !== this.curQnA.id) {
      this.getOneQna(this.$route.params.num);
    }
    this.title = this.curQnA.title;
    this.content = this.curQnA.content;
  },
  methods: {
    ...mapActions(qnaStore, ["getOneQna", "modifyQnA"]),
    handleOnChange(e) {
      const { id, value } = e.target;
      id === "title" ? (this.title = value) : (this.content = value);
    },

    saveQnA() {
      // 수정된 정보 저장
      if (!this.title || !this.content) {
        alert("내용을 입력해주세요!");
        return;
      }

      const nQnA = {
        id: this.curQnA.id,
        content: this.content,
        title: this.title,
        userId: this.checkUserInfo.id,
      };
      this.modifyQnA(nQnA);
      this.moveToDetail();
    },

    moveToDetail() {
      this.$router.push({
        name: "QnADetail",
        params: { num: this.$route.params.num },
      });
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
