<template>
  <nav style="--bs-breadcrumb-divider: '>'" aria-label="breadcrumb">
    <ol class="breadcrumb" style="background-color: transparent">
      <li class="breadcrumb-item">공지사항</li>
      <li class="breadcrumb-item active" aria-current="page">수정</li>
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

    <QuillEditor
      theme="snow"
      @input="handleOnChangeContent"
      v-model:content="content"
    />

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
import "@vueup/vue-quill/dist/vue-quill.snow.css";
import { QuillEditor } from "@vueup/vue-quill";
import SoftButton from "@/components/SoftButton.vue";
import SoftInput from "@/components/SoftInput.vue";
import { mapGetters, mapActions } from "vuex";
const noticeStore = "noticeStore";
const userStore = "userStore";
export default {
  name: "QnAModify",
  components: {
    SoftButton,
    SoftInput,
    QuillEditor,
  },

  data() {
    return {
      title: "",
      content: "",
      readOnly: false,
    };
  },
  computed: {
    ...mapGetters(noticeStore, ["curNotice", "curMsg"]),
    ...mapGetters(userStore, ["checkUserInfo"]),
  },

  created() {
    if (this.$route.params.num !== this.curNotice.id) {
      this.getOneNotice(this.$route.params.num);
    }
    this.title = this.curNotice.title;
    QuillEditor.props.contentType.default = "html";
    this.content = this.curNotice.content;
  },
  methods: {
    ...mapActions(noticeStore, ["getOneNotice", "modifyNotice"]),
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

      const nNotice = {
        id: this.curNotice.id,
        content: this.content,
        title: this.title,
      };
      console.log(nNotice);
      this.modifyNotice(nNotice);
      this.moveToDetail();
    },

    moveToDetail() {
      this.$router.push({
        name: "NoticeDetail",
        params: { num: this.$route.params.num },
      });
    },
    moveToList() {
      this.$router.push({ name: "NoticeList" });
    },
  },
};
</script>

<style scoped>
.qna-container {
  width: 80%;
  margin: 0 auto;
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
