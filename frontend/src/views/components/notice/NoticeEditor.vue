<template>
  <QuillEditor
    theme="snow"
    v-model:content="content"
    @input="handleOnChange($event)"
  />
</template>

<script>
import "@vueup/vue-quill/dist/vue-quill.snow.css";
import { QuillEditor } from "@vueup/vue-quill";
export default {
  name: "notice-editor",
  components: {
    QuillEditor,
  },
  data() {
    return {
      content: "",
    };
  },
  methods: {
    handleOnChange(e) {
      console.log(e.target.innerHTML);
    },
  },
};
</script>

<style scoped>
svg {
  width: 100% !important;
  margin: 0px;
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
</style>
