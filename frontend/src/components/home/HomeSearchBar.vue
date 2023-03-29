<template>
  <div class="row" style="position: relative">
    <soft-input
      id="word"
      type="text"
      :value="searchWord"
      placeholder="아파트명, 주소를 입력하세요."
      icon="fa fa-search"
      iconDir="right"
      size="lg"
      @input="getSearchKeyword"
      @searchchange="searchchange"
      @clickfunc="clickfunc"
    />
    <ul
      v-if="isTyping"
      id="search-keywords"
      class="form-group"
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
      <div
        :style="
          addressList.length == 0 || keywordList.length == 0
            ? { height: '0px' }
            : { height: '12px' }
        "
      />
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
import SoftInput from "@/components/SoftInput";
import { mapState, mapActions, mapMutations } from "vuex";
const aptStore = "aptStore";
export default {
  data() {
    return {
      isTyping: false,
      searchWord: "",
      searchAptCode: "",
    };
  },
  computed: {
    ...mapState(aptStore, ["keywordList", "addressList"]),
  },
  components: {
    SoftInput,
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
      "SET_SEARCH_WORD",
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
        this.SET_SEARCH_RESULT_TYPE("apt");
        // 이전 마커 제거
      } else {
        this.searchWord = keyword.sidoName + keyword.gugunName;
        this.SET_SEARCH_RESULT_TYPE("addr");
        this.SET_SEARCH_ADDRESS(keyword); // 검색한 지역 좌표 저장 필요
        this.getAptListInArea(keyword);
        this.SET_SEARCH_RESULT_TYPE("addr");
      }

      // 검색어 저장
      this.SET_SEARCH_WORD(this.searchWord);
      // 결과 컴포넌트 보이게 설정
      this.SET_SHOW_SEARCH_RESULT(true);

      this.$router.push("/search");
    },
    clickfunc() {
      console.log(this.word);
      // 검색하러 가기
    },
    searchchange(eid, evalue) {
      console.log(eid);
      this.$data[eid] = evalue;
    },
  },
};
</script>

<style scoped>
#search-keywords {
  position: absolute;
  top: 48px;
  left: 50%;
  width: 96% !important;
  transform: translateX(-50%);
  list-style: none;
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
  text-align: start;
}

ul li:hover {
  background-color: #ddd;
}
</style>
