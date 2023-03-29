<template>
  <div class="nav-link">
    <div
      class="text-center bg-white shadow icon icon-shape icon-sm border-radius-md d-flex align-items-center justify-content-center"
      :class="this.$store.state.isRTL ? ' ms-2' : 'me-2'"
    >
      <!-- 여기요 -->
      <slot name="icon"></slot>
    </div>
    <span
      class="nav-link-text"
      :class="this.$store.state.isRTL ? ' me-1' : 'ms-1'"
      >{{ navText }}</span
    >
  </div>
</template>
<script>
export default {
  name: "sidenav-collapse",
  props: {
    to: {
      type: [Object, String],
      required: false,
    },
    navText: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      isExpanded: false,
    };
  },
};
</script>
<style scoped>
svg {
  cursor: pointer;
  overflow: visible;
  width: 60px !important;
  margin: 20px !important;
}
</style>
