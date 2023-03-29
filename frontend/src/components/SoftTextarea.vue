<template>
  <div class="form-group">
    <label :for="id"> <slot />{{ text }} </label>
    <textarea
      :id="id"
      class="form-control"
      rows="8"
      :value="value"
      :placeholder="placeholder"
      :readonly="readOnly"
      style="resize: none"
    ></textarea>
  </div>
</template>

<script>
export default {
  name: "SoftTextarea",
  props: {
    text: {
      type: String,
      required: true,
    },
    id: {
      type: String,
      required: true,
    },
    placeholder: {
      type: String,
      default: "Your text here...",
    },
    value: {
      type: String,
      default: "",
    },
    readOnly: {
      type: Boolean,
      default: false,
    },
  },
};
</script>
