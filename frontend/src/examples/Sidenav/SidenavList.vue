<template>
  <div style="padding-bottom: 20px" class="w-auto h-auto collapse navbar-collapse" id="sidenav-collapse-main">
    <ul class="navbar-nav">
      <li class="mt-3 nav-item">
        <h6 class="text-xs ps-4 text-uppercase font-weight-bolder opacity-6" :class="this.$store.state.isRTL ? 'me-4' : 'ms-2'">카테고리</h6>
      </li>
      <!-- <li class="nav-item">
        <sidenav-collapse navText="부동산" :key="0" @click="handleSearchNews(navTextData[0].text, 0)">
          <template #icon>
            <building></building>
          </template>
        </sidenav-collapse>
      </li> -->
      <li :class="[selected === index ? 'active' : '']" class="nav-item" v-for="(item, index) in navTextData" :key="index" @click="handleSearchNews(item.searchWord, index)">
        <sidenav-collapse :navText="item.text" :searchWord="item.searchWord">
          <template #icon>
            <building v-if="item.text === '부동산'"></building>
            <house v-else-if="item.text === '주거'"></house>
            <store v-else-if="item.text === '상가'"></store>
            <chart v-else-if="item.text === '투자'"></chart>
            <glass v-else-if="item.text === '경매'"></glass>
            <couch v-else-if="item.text === '인테리어'"></couch>
          </template>
        </sidenav-collapse>
      </li>
    </ul>
  </div>
</template>
<script>
import { mapActions, mapMutations } from "vuex";
import SidenavCollapse from "./SidenavCollapse.vue";
import building from "../../components/Icon/news/building.vue";
import house from "../../components/Icon/news/house.vue";
import store from "../../components/Icon/news/store.vue";
import chart from "../../components/Icon/news/chart.vue";
import glass from "../../components/Icon/news/glass.vue";
import couch from "../../components/Icon/news/couch.vue";

const newsStore = "newsStore";
export default {
  name: "SidenavList",
  components: {
    SidenavCollapse,
    building,
    house,
    store,
    chart,
    glass,
    couch,
  },
  data() {
    return {
      isActive: "active",
      navTextData: [],
      selected: 0,
      building: "building",
    };
  },
  methods: {
    ...mapActions(newsStore, ["searchNews"]),
    ...mapMutations(newsStore, ["SET_SEARCH_WORD"]),
    getRoute() {
      const routeArr = this.$route.path.split("/");
      return routeArr[1];
    },
    handleSearchNews(word, index) {
      this.selected = index;
      this.SET_SEARCH_WORD(word);
      this.searchNews(word);
    },
  },
  created() {
    this.navTextData = [
      { text: "부동산", searchWord: "아파트 부동산" },
      { text: "주거", searchWord: "아파트 주거" },
      { text: "상가", searchWord: "부동산 상가" },
      { text: "투자", searchWord: "부동산 투자" },
      { text: "경매", searchWord: "부동산 경매" },
      { text: "인테리어", searchWord: "아파트 인테리어" },
    ];
  },
};
</script>
<style scoped>
ul li {
  cursor: pointer;
}
.navbar-nav {
  margin: 0 auto;
  width: 80%;
}
.active {
  background-color: #fff;
  border-radius: 0.7rem;
  box-shadow: 0 2px 12px 0 rgb(0 0 0 / 16%);
}
</style>
