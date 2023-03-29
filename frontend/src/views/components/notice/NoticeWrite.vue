<template>
  <nav style="--bs-breadcrumb-divider: '>'" aria-label="breadcrumb">
    <ol class="breadcrumb" style="background-color: transparent">
      <li class="breadcrumb-item">공지사항</li>
      <li class="breadcrumb-item active" aria-current="page">등록</li>
    </ol>
  </nav>

  <div class="qna-container">
    <label>제목</label>
    <SoftInput
      id="title"
      placeholder="제목을 입력하세요."
      :value="title"
      @input="handleOnChangeTitle"
    />

    <QuillEditor
      theme="snow"
      v-model:content="content"
      @input="handleOnChangeContent"
    />

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
import "@vueup/vue-quill/dist/vue-quill.snow.css";
import { QuillEditor } from "@vueup/vue-quill";
import SoftButton from "@/components/SoftButton.vue";
import SoftInput from "@/components/SoftInput.vue";
import { mapGetters, mapActions, mapMutations } from "vuex";
const userStore = "userStore";
const noticeStore = "noticeStore";
export default {
  name: "NoticeWrite",
  components: {
    SoftButton,
    SoftInput,
    QuillEditor,
  },

  data() {
    return {
      title: "",
      content: "",
      contentHTML: "",
      readOnly: false,
    };
  },
  created() {
    QuillEditor.props.contentType.default = "html";
  },
  computed: {
    ...mapGetters(userStore, ["checkUserInfo"]),
    ...mapGetters(noticeStore, ["curMsg"]),
  },
  methods: {
    ...mapActions(noticeStore, ["registerNotice"]),
    ...mapMutations(noticeStore, ["SET_MESSAGE"]),
    handleOnChangeTitle(e) {
      const { value } = e.target;
      this.title = value;
    },

    handleOnChangeContent(e) {
      this.content = e.target.innerHTML;
      //this.contentHTML = e.target.innerHTML;
    },
    handleOnClickRegisterBtn() {
      if (!this.title || !this.content) {
        alert("내용을 입력해주세요!");
        return;
      }

      console.log("this.content?? ", this.content);
      console.log("this.contentHTML?? ", this.contentHTML);

      QuillEditor.props.contentType.default = "html";
      this.contentHTML = this.content;

      // API 호출
      const notice = {
        content: this.contentHTML,
        title: this.title,
        userId: this.checkUserInfo.id,
      };
      console.log(notice);
      this.registerNotice(notice);
      this.SET_MESSAGE("등록되었습니다:)");

      if (this.curMsg.length !== 0) {
        alert(this.curMsg);
      }
      this.moveToList();
    },

    moveToList() {
      this.$router.push({ name: "NoticeList" });
    },
  },
};
</script>

<style>
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
svg {
  width: inherit;
  margin: 0px !important;
}

.ql-toolbar {
  border-top-left-radius: 0.75rem;
  border-top-right-radius: 0.75rem;
}

.ql-container {
  height: 200px;
  overflow-y: scroll;
  border-bottom-left-radius: 0.75rem;
  border-bottom-right-radius: 0.75rem;
  margin-bottom: 12px;
}

.ql-picker-label svg {
  width: 18px !important;
  top: 0px !important;
}
</style>
