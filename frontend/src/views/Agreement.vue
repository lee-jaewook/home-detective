<template>
  <dbox></dbox>
</template>

<script>
// import pencil from "../components/loading/pencil.vue";
import dbox from "../components/loading/3dbox.vue";
import { mapMutations, mapActions } from "vuex";
// import jwtDecode from "jwt-decode";

const userStore = "userStore";
export default {
  name: "SignIn",
  created() {
    this.init();
  },
  components: {
    // pencil,
    dbox,
  },
  methods: {
    ...mapMutations(userStore, ["SET_IS_LOGIN", "SET_IS_LOGIN_ERROR", "SET_IS_VALID_TOKEN", "SET_IS_SOCIAL_LOGIN"]),
    ...mapActions(userStore, ["getUserInfo"]),
    async init() {
      let accessToken = this.$route.query.access_token;
      let refreshToken = this.$route.query.refresh_token;
      // let decodeToken = jwtDecode(accessToken);
      // console.log(decodeToken);
      // console.log(decodeToken["id"]);

      this.SET_IS_LOGIN(true);
      this.SET_IS_LOGIN_ERROR(false);
      this.SET_IS_VALID_TOKEN(true);
      this.SET_IS_LOGIN(true);
      this.SET_IS_SOCIAL_LOGIN(true);

      sessionStorage.setItem("access-token", accessToken);
      sessionStorage.setItem("refresh-token", refreshToken);

      await this.getUserInfo(accessToken);

      this.$router.push({ name: "/" });
    },
  },
};
</script>
