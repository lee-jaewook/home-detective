<template>
  <div class="form-check">
    <input :id="id" class="form-check-input" type="checkbox" :name="name" :checked="checked" @click="checkboxclick($event)" />
    <label :for="id" class="custom-control-label" :class="$attrs.class">
      <slot />
    </label>
  </div>
</template>

<script>
export default {
  name: "SoftCheckbox",
  props: {
    name: {
      type: String,
      default: "",
    },
    id: {
      type: String,
      default: "",
    },
    checked: {
      type: Boolean,
      default: false,
    },
  },
  methods: {
    checkboxclick(event) {
      // console.log(event.target.value);
      // console.log(event.target.id);
      this.$emit("checkboxclick", event.target.checked);
    },
  },
};
</script>
