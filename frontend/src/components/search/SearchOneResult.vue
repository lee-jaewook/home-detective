<template>
  <div id="search-road-view"></div>
  <div style="background-color: #ededed">
    <div id="search-content-wrapper">
      <div>
        <p style="color: #333; font-weight: bold; margin-bottom: 0px">
          {{ curApt.apartmentName }}
        </p>
        <label class="content-text"
          >{{ curApt.sidoName }} {{ curApt.gugunName }}
          {{ curApt.dongName }}</label
        >
      </div>
      <SearchLikeToggle :aptCode="curApt.aptCode"></SearchLikeToggle>
    </div>
    <div class="content-table-wrapper">
      <!-- 매매정보 컴포넌트 분리 할 것-->
      <label class="content-title">매매정보 </label>
      <table class="table">
        <thead>
          <tr>
            <th scope="col">거래일시</th>
            <th scope="col">거래금액</th>
            <th scope="col">면적(㎡)</th>
            <th scope="col">층수</th>
          </tr>
        </thead>
        <tbody style="text-align: center">
          <tr v-for="info in curPageAptDealInfo" :key="info.num">
            <td scope="row">
              {{ info.dealYear }}-{{ info.dealMonth }}-{{ info.dealDay }}
            </td>
            <td>{{ info.dealAmount }}</td>
            <td>{{ info.area }}</td>
            <td>{{ info.floor }}</td>
          </tr>
        </tbody>
      </table>
      <nav
        aria-label="Page navigation example"
        style="display: flex; justify-content: center"
      >
        <ul class="pagination pagination-sm">
          <li class="page-item">
            <a class="page-link" href="#" aria-label="Previous">
              <span aria-hidden="true">&laquo;</span>
            </a>
          </li>
          <li
            v-for="cnt in Number(total)"
            class="page-item"
            :class="[curPage === cnt ? 'active' : '']"
            :key="cnt"
            @click="getNextPageInfo(cnt, curApt.aptCode)"
          >
            <a class="page-link" href="#">{{ cnt }}</a>
          </li>
          <li class="page-item">
            <a class="page-link" href="#" aria-label="Next">
              <span aria-hidden="true">&raquo;</span>
            </a>
          </li>
        </ul>
      </nav>
    </div>
    <!-- 주변 정보 -->
    <!-- <div class="content-table-wrapper">
      <label class="content-title">주변 정보 </label>
    </div> -->
    <!-- 관련 뉴스 -->
    <div class="content-table-wrapper">
      <label class="content-title">관련 부동산 뉴스</label>
      <div
        v-for="(news, index) in curNewsList"
        class="list-group card-news"
        :key="index"
      >
        <a
          :href="news.link"
          target="_blank"
          class="list-group-item list-group-item-action"
        >
          <div class="d-flex w-100 justify-content-between">
            <p class="content-title" v-html="news.title"></p>
          </div>
          <p
            class="mb-1"
            v-html="news.description"
            style="font-size: 0.75rem"
          ></p>
          <small class="text-muted" style="font-size: 0.75rem">{{
            news.pubDate
          }}</small>
        </a>
      </div>
    </div>
  </div>
</template>

<script>
import SearchLikeToggle from "@/views/components/search/SearchLikeToggle.vue";
import { mapGetters, mapState, mapActions, mapMutations } from "vuex";
const aptStore = "aptStore";
const newsStore = "newsStore";
const userStore = "userStore";
export default {
  name: "SearchOneResult",
  components: {
    SearchLikeToggle,
  },
  data() {
    return {
      tradingInfo: [],
      curPage: 1,
    };
  },
  computed: {
    ...mapState(aptStore, ["total", "pageNo"]),
    ...mapGetters(aptStore, ["curApt", "curPageAptDealInfo", "searchType"]),
    ...mapGetters(userStore, ["checkUserInfo"]),
    ...mapGetters(newsStore, ["curNewsList"]),
  },
  watch: {
    curApt() {
      this.searchNews(this.curApt.gugunName + " 아파트");

      if (this.checkUserInfo !== null) {
        const requestBody = {
          aptCode: this.curApt.aptCode,
          userId: this.checkUserInfo.id,
        };
        this.getLikedApt(requestBody);
      }
    },
  },
  methods: {
    ...mapActions(aptStore, ["getDealInfo", "getAptDetail", "getLikedApt"]),
    ...mapActions(newsStore, ["searchNews"]),
    ...mapMutations(aptStore, ["SET_SEARCH_RESULT_TYPE"]),
    getNextPageInfo(cnt, aptCode) {
      this.getDealInfo({ pageNo: cnt, aptCode });
      this.curPage = cnt;
    },
  },
  created() {},
};
</script>

<style>
#search-road-view {
  width: 100%;
  height: 180px;
  background-color: #fff;
}
#search-content-wrapper {
  position: relative;
  padding: 12px;
  background-color: #fff;
  border-bottom: 1px solid #cacabf;
}
.content-text {
  margin: 0px;
}

.content-table-wrapper {
  padding: 12px;
  background-color: #fff;
  margin-top: 8px;
}
table {
  font-size: 0.8rem;
}

tr:hover {
  background-color: #f8f9fa;
  cursor: pointer;
}

.content-title {
  font-size: 0.8rem;
  font-weight: bold;
}

.card-news {
  margin-bottom: 4px !important;
}
</style>
