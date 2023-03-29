<template>
  <NewsNavbar></NewsNavbar>
  <div style="margin-top: 0x" id="news-page" v-if="isLoading">
    <LoadingBox></LoadingBox>
  </div>
  <div style="margin-top: 96px" id="news-page" v-if="!isLoading">
    <div style="width: 80%; display: flex; justify-content: space-between; margin-bottom: 12px">
      <h5>최신 뉴스</h5>
    </div>
    <div id="news-container" style="display: flex">
      <div class="news-item col-lg-4 content-wrapper" style="width: 100%; position: relative" v-for="(news, index) in curNewsList" :key="index">
        <a class="card-body" id="card-wrapper" :href="news.link" target="_blank">
          <div class="col-lg-12" style="margin-right: 12px; margin-bottom: 10px">
            <img :src="news.img" style="width: 100%; height: 140px; border-radius: 0.75rem; opacity: 0.8" />
          </div>
          <div>
            <div class="col-lg-12">
              <div class="d-flex flex-column h-100">
                <h5 class="font-weight-bolder" style="font-size: 0.9rem" v-html="news.title"></h5>
                <p class="mb-5" v-html="news.description" style="font-size: 0.79rem"></p>
                <div class="news-date" style="display: flex; justify-content: space-between">
                  <span style="font-size: 0.7rem">{{ news.pubDate }}</span>
                </div>
              </div>
            </div>
          </div>
        </a>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions, mapGetters } from "vuex";
import NewsNavbar from "../examples/Sidenav/index.vue";
import LoadingBox from "../components/loading/3dbox.vue";
const newsStore = "newsStore";

export default {
  name: "News",
  components: {
    NewsNavbar,
    LoadingBox,
  },
  data() {
    return {
      word: "",
    };
  },
  methods: {
    ...mapActions(newsStore, ["searchNews"]),
    handleSearchNews() {
      if (!this.word) {
        alert("검색어를 입력해주세요!");
        return;
      }
      this.searchNews(this.word);
    },
    async init() {
      console.log("hey");
      await this.searchNews("부동산");
      console.log("hey2");
    },
  },
  computed: {
    ...mapState(newsStore, ["isLoading"]),
    ...mapGetters(newsStore, ["curNewsList", "curWord"]),
  },
  created() {
    this.init();
  },
};
</script>

<style scoped>
#news-page {
  width: 80%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin: 0 auto;
}
#news-container {
  display: grid !important;
  grid-template-columns: 1fr 1fr 1fr;
  width: 80%;
  column-gap: 16px;
}
.news-item {
  margin-bottom: 12px;
}
#card-wrapper {
  display: flex;
  flex-direction: column;
  color: #344767;
}
#search-news-input {
  width: 80% !important;
  margin-bottom: 36px;
}
.content-wrapper {
  border: 1px solid transparent;
  border-radius: 1rem;
  background-color: #fff;
  box-shadow: 0 2px 12px 0 rgb(0 0 0 / 16%);
}
.content-wrapper:hover {
  background-color: #fff;
  border: 1px solid #ddd;
}
.news-date {
  position: absolute;
  bottom: 10px;
}
</style>
