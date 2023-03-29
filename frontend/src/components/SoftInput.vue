<template>
  <div class="form-group">
    <div :class="hasIcon(icon)">
      <span v-if="iconDir === 'left'" class="input-group-text">
        <i :class="getIcon(icon)"></i>
      </span>
      <input
        :id="id"
        :type="type"
        class="form-control"
        :class="getClasses(size, success, error)"
        :name="name"
        :value="value"
        :placeholder="placeholder"
        :isRequired="isRequired"
        @input="searchChangeFunc($event)"
        style="transition: none !important"
        autocomplete="off"
        :disabled="disabled"
        :maxlength="maxlength"
        @blur="blurevent($event)"
        @click="clickevent($event)"
      />
      <button
        v-if="iconDir === 'right'"
        class="input-group-text"
        @click="clickFunc()"
      >
        <i :class="getIcon(icon)"></i>
      </button>
    </div>
  </div>
</template>

<script>
export default {
  name: "SoftInput",
  props: {
    size: {
      type: String,
      default: "default",
    },
    maxlength: {
      type: String,
      default: "",
    },
    success: {
      type: Boolean,
      default: false,
    },
    error: {
      type: Boolean,
      default: false,
    },
    icon: {
      type: String,
      default: "",
    },
    iconDir: {
      type: String,
      default: "",
    },
    name: {
      type: String,
      default: "",
    },
    id: {
      type: String,
      default: "",
    },
    value: {
      type: String,
      default: "",
    },
    placeholder: {
      type: String,
      default: "Type here...",
    },
    type: {
      type: String,
      default: "text",
    },
    isRequired: {
      type: Boolean,
      default: false,
    },
    disabled: {
      type: Boolean,
      default: false,
    },
  },
  methods: {
    getClasses: (size, success, error) => {
      let sizeValue, isValidValue;

      sizeValue = size ? `form-control-${size}` : null;

      if (error) {
        isValidValue = "is-invalid";
      } else if (success) {
        isValidValue = "is-valid";
      } else {
        isValidValue = "";
      }
      return `${sizeValue} ${isValidValue}`;
    },
    getIcon: (icon) => (icon ? icon : null),
    hasIcon: (icon) => (icon ? "input-group" : null),
    searchChangeFunc(event) {
      // console.log(event.target.value);
      // console.log(event.target.id);
      this.$emit("searchchange", event.target.id, event.target.value);
    },
    blurevent(event) {
      this.$emit("blurevent", event.target.id, event.target.value);
    },
    clickevent(event) {
      this.$emit("clickevent", event.target.id);
    },
    clickFunc() {
      console.log("hihi");
      this.$emit("clickfunc");
    },
  },
};
</script>
