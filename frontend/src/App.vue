<template>
  <div class="container top-0 position-sticky z-index-sticky">
    <div class="row">
      <div class="col-12">
        <navbar
          is-blur="blur blur-rounded my-3 py-2 start-0 end-0 mx-4 shadow"
          btn-background="bg-gradient-success"
          :dark-mode="true"
        />
      </div>
    </div>
  </div>
  <main
    class="main-content position-relative max-height-vh-100 h-100 border-radius-lg"
  >
    <router-view />
    <app-footer v-show="this.$store.state.showFooter" />
  </main>
</template>
<script>
import Navbar from "@/examples/PageLayout/Navbar.vue";
import AppFooter from "@/examples/Footer.vue";
import { mapMutations, mapGetters } from "vuex";
const aptStore = "aptStore";
export default {
  name: "App",
  components: {
    Navbar,
    AppFooter,
  },
  methods: {
    ...mapMutations(["toggleConfigurator", "navbarMinimize"]),
    ...mapMutations(aptStore, [
      "INIT_MAP",
      "SET_USER_LOCATION",
      "INIT_MAP_STATE",
    ]),
    ...mapGetters(aptStore, ["initState"]),
  },

  created() {
    console.log("app created");
    this.INIT_MAP();
    this.INIT_MAP_STATE(true);
    this.$getLocation()
      .then((coordinates) => {
        console.log("get!", coordinates);
        this.SET_USER_LOCATION(coordinates);
      })
      .catch(() => {
        this.SET_USER_LOCATION({
          lat: 37.5014,
          lng: 127.0397,
        });
      });
  },
  computed: {
    navClasses() {
      return {
        "position-sticky blur shadow-blur mt-4 left-auto top-1 z-index-sticky": this
          .$store.state.isNavFixed,
        "position-absolute px-4 mx-0 w-100 z-index-2": this.$store.state
          .isAbsolute,
        "px-0 mx-4 mt-4": !this.$store.state.isAbsolute,
      };
    },
  },
  beforeMount() {
    this.$store.state.isTransparent = "bg-transparent";
  },
};
</script>
