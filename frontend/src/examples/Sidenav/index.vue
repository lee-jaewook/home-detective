<template>
  <aside
    style="width: 200px"
    class="my-3 overflow-auto border-0 sidenav navbar-vertical navbar-expand-xs border-radius-xl"
    id="sidenav-main"
  >
    <sidenav-list />
  </aside>
</template>
<script>
import SidenavList from "./SidenavList.vue";

export default {
  name: "index",
  components: {
    SidenavList,
  },
  data() {
    return {};
  },
  props: {
    customClass: {
      type: String,
      default: "",
    },
  },
};
</script>
<style scoped>
#sidenav-main {
  top: 116px;
  left: 20px;
  width: inherit;
}
</style>
