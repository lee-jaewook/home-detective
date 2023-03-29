<template>
  <div id="container">
    <SearchBar></SearchBar>
    <Transition name="slide-fade">
      <SearchResult v-show="isShowSearchResult"></SearchResult>
    </Transition>
    <KakaoMap></KakaoMap>
  </div>
</template>

<script>
import KakaoMap from "@/views/components/search/KakaoMap.vue";
import SearchBar from "@/views/components/search/SearchBar.vue";
import SearchResult from "@/views/components/search/SearchResult.vue";
import { mapState, mapMutations } from "vuex";

const aptStore = "aptStore";
export default {
  name: "Search",

  data() {
    return {};
  },
  created() {
    this.SET_SHOW_SEARCH_RESULT(false); // 초기 화면 설정
    console.log("search!", this.curUserLocation);
    this.INIT_MAP();
  },
  components: {
    KakaoMap,
    SearchBar,
    SearchResult,
  },
  computed: {
    ...mapState(aptStore, ["isShowSearchResult"]),
  },
  methods: {
    ...mapMutations(aptStore, [
      "SET_SHOW_SEARCH_RESULT",
      "INIT_MAP",
      "SET_USER_LOCATION",
    ]),
    showSearchResult() {
      console.log("setSearchResult()...");
      this.isShowSearchResult = true;
    },
  },
};
</script>

<style scoped>
#container {
  position: relative;
  margin-top: 96px !important;
  background-color: #fff;
  margin: 0 16px;
  border-radius: 0.75rem;
  box-shadow: 0 2px 12px 0 rgb(0 0 0 / 16%);
}

.slide-fade-enter-active {
  transition: all 0.3s ease-out;
}

.slide-fade-leave-active {
  transition: all 0.8s cubic-bezier(1, 0.5, 0.8, 1);
}

.slide-fade-enter-from,
.slide-fade-leave-to {
  transform: translateX(20px);
  opacity: 0;
}
</style>
