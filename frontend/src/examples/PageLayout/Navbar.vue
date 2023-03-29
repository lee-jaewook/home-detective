<template>
  <!-- Navbar -->
  <nav
    class="top-0 navbar navbar-expand-lg position-absolute z-index-3"
    :class="isBlur ? isBlur : 'shadow-none my-2 navbar-transparent w-100'"
  >
    <div class="container">
      <router-link
        class="navbar-brand font-weight-bolder ms-lg-0 ms-3"
        to="/"
        v-bind="$attrs"
        :class="isBlur ? 'text-dark' : 'text-white'"
        style="font-size: 16px"
      >
        <img
          src="../../assets/img/logo-ct.png"
          width="20"
          style="margin-right: 2px"
        />
        홈탐정
      </router-link>
      <button
        class="navbar-toggler shadow-none ms-2"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navigation"
        aria-controls="navigation"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon mt-2">
          <span class="navbar-toggler-bar bar1"></span>
          <span class="navbar-toggler-bar bar2"></span>
          <span class="navbar-toggler-bar bar3"></span>
        </span>
      </button>
      <div class="collapse navbar-collapse" id="navigation">
        <ul class="navbar-nav mx-auto">
          <li class="nav-item">
            <router-link
              class="nav-link d-flex align-items-center me-2 active"
              aria-current="page"
              to="/search"
            >
              <i
                class="ni ni-square-pin opacity-6 me-1"
                aria-hidden="true"
                :class="isBlur ? 'text-dark' : 'text-white'"
              ></i>
              실거래 검색
            </router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link me-2" to="/news">
              <i
                class="fa fa-regular fa-newspaper opacity-6 me-1"
                aria-hidden="true"
                :class="isBlur ? 'text-dark' : 'text-white'"
              ></i>
              부동산 뉴스
            </router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link me-2" to="/notice">
              <i
                class="fas fa-sharp fa-solid fa-bullhorn opacity-6 me-1"
                aria-hidden="true"
                :class="isBlur ? 'text-dark' : 'text-white'"
              ></i>
              공지사항
            </router-link>
          </li>
          <!-- <li class="nav-item">
            <router-link class="nav-link me-2" to="/profile">
              <i class="fas fa-user-circle opacity-6 me-1" aria-hidden="true" :class="isBlur ? 'text-dark' : 'text-white'"></i>
              마이페이지
            </router-link>
          </li> -->
        </ul>
        <ul class="navbar-nav d-lg-block d-none">
          <li class="nav-item">
            <router-link
              v-if="!userInfo"
              to="/sign-in"
              class="btn btn-sm btn-round mb-0 me-1"
              :class="isBlur ? 'bg-gradient-dark' : 'bg-gradient-success'"
              >로그인
            </router-link>
            <router-link
              v-if="userInfo"
              to="/profile"
              class="btn btn-sm btn-round mb-0 me-1"
              :class="isBlur ? 'bg-gradient-dark' : 'bg-gradient-success'"
            >
              {{ userInfo.name }}님
            </router-link>
          </li>
        </ul>
      </div>
    </div>
  </nav>
  <!-- End Navbar -->
</template>

<script>
import { mapState, mapGetters } from "vuex";
import downArrWhite from "@/assets/img/down-arrow-white.svg";
import downArrBlack from "@/assets/img/down-arrow-dark.svg";

const userStore = "userStore";
export default {
  name: "navbar",
  data() {
    return {
      downArrWhite,
      downArrBlack,
    };
  },
  props: {
    btnBackground: String,
    isBlur: String,
    darkMode: {
      type: Boolean,
      default: false,
    },
  },
  computed: {
    ...mapState(userStore, ["userInfo"]),
    ...mapGetters(userStore, ["checkisLogin"]),
    darkModes() {
      return {
        "text-dark": this.darkMode,
      };
    },
  },
};
</script>
