<template>
  <div>
    <input
      id="search-input"
      class="form-control"
      type="text"
      placeholder="지역/아파트명 검색"
      autocomplete="off"
      :value="searchWord"
      @input="getSearchKeyword"
    />

    <ul
      v-if="isTyping"
      id="search-keywords"
      :style="
        addressList.length == 0 || keywordList.length == 0
          ? { border: 'none' }
          : { border: '1px solid #ddd' }
      "
    >
      <li
        v-for="(addr, index) in addressList"
        :key="index"
        :value="addr.sidoName"
        :lat="addr.lat"
        :lng="addr.lng"
        @click="getSearchResult('addr', addr)"
      >
        <i class="fa fa-solid fa-map-pin"></i>
        {{ addr.sidoName }} {{ addr.gugunName }}
      </li>
      <div style="height: 12px" />
      <li
        v-for="keyword in keywordList"
        :key="keyword.aptCode"
        :value="keyword.apartmentName"
        :lat="keyword.lat"
        :lng="keyword.lng"
        @click="getSearchResult('apt', keyword)"
      >
        <i class="fa fa-solid fa-building"></i>

        {{ keyword.apartmentName }}
      </li>
    </ul>
  </div>
</template>

<script>
import { mapState, mapActions, mapMutations, mapGetters } from "vuex";
const aptStore = "aptStore";
const userStore = "userStore";
export default {
  name: "SearchInput",
  data() {
    return {
      isTyping: false,
      searchWord: "",
      searchAptCode: "",
    };
  },
  created() {
    this.searchWord = this.curSearchWord;
  },
  computed: {
    ...mapState(aptStore, ["keywordList", "addressList"]),
    ...mapGetters(userStore, ["checkUserInfo"]),
    ...mapGetters(aptStore, ["curSearchWord"]),
  },
  methods: {
    ...mapActions(aptStore, [
      "searchByAptName",
      "searchByAddress",
      "getAptDetail",
      "getDealInfo",
      "getAptListInArea",
      "getLikedApt",
    ]),
    ...mapMutations(aptStore, [
      "SET_SHOW_SEARCH_RESULT",
      "SET_SEARCH_RESULT_TYPE",
      "SET_SEARCH_ADDRESS",
      "INIT_MAP_MARKERS",
    ]),

    getSearchKeyword(e) {
      const { value } = e.target;
      if (value.length === 0) {
        this.isTyping = false;
        return;
      }
      this.searchWord = value;
      this.searchByAptName(value);
      this.searchByAddress(value);
      this.isTyping = true;
    },

    getSearchResult(type, keyword) {
      this.isTyping = false;
      if (type === "apt") {
        this.searchWord = keyword.apartmentName;
        this.getAptDetail(keyword.aptCode); // 정보 요청
        this.getDealInfo({ pageNo: 1, aptCode: keyword.aptCode }); // 거래 정보 요청

        if (this.checkUserInfo) {
          this.getLikedApt({
            aptCode: keyword.aptCode,
            userId: this.checkUserInfo.id, // user 변경
          });
        }

        this.SET_SEARCH_RESULT_TYPE("apt");
        // 이전 마커 제거
      } else {
        this.searchWord = keyword.sidoName + keyword.gugunName;
        this.SET_SEARCH_ADDRESS(keyword); // 검색한 지역 좌표 저장 필요
        this.getAptListInArea(keyword);
        this.SET_SEARCH_RESULT_TYPE("addr");
      }
      // 결과 컴포넌트 보이게 설정
      this.SET_SHOW_SEARCH_RESULT(true);
    },
  },
};
</script>

<style scoped>
#search-keywords {
  list-style: none;
  width: 100%;
  max-height: 200px;
  overflow-y: scroll;
  background-color: rgba(255, 255, 255, 0.9);
  border: 1px solid #ddd;
  border-radius: 0.48rem;
  z-index: 10;
  margin-top: 2px;
  padding-left: 0px;
}
#search-input:focus {
  border-color: #ddd !important;
  box-shadow: none !important;
}
ul li {
  padding: 2px 12px;
}

ul li:hover {
  background-color: #ddd;
}
</style>
