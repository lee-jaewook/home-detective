<template>
  <div v-if="searchType === 'addr'" class="search-result-container">
    <!-- 아파트 목록 컴포넌트 분리 할 것-->
    <table class="table">
      <tbody>
        <tr
          class="search-tr"
          v-for="apt in curAptList"
          :key="apt.aptCode"
          :lat="apt.lat"
          :lng="apt.lng"
          @click="searchByAptName(apt.aptCode)"
        >
          <td>
            <div style="padding-left: 16px">
              <h6>{{ apt.apartmentName }}</h6>
              {{ apt.sidoName }} {{ apt.gugunName }} {{ apt.dongName }}
              {{ apt.jibun }}
            </div>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
  <div v-else-if="searchType === 'like'" class="search-result-container">
    <!-- 컴포넌트 분리 할 것-->
    <table v-if="likedList.length !== 0" class="table">
      <tbody>
        <tr
          class="search-tr"
          v-for="apt in likedList"
          :key="apt.aptCode"
          :lat="apt.lat"
          :lng="apt.lng"
          @click="searchByAptName(apt.aptCode)"
        >
          <td>
            <div style="padding-left: 16px">
              <h6>{{ apt.apartmentName }}</h6>
              {{ apt.sidoName }} {{ apt.gugunName }} {{ apt.dongName }}
              {{ apt.jibun }}
            </div>
          </td>
        </tr>
      </tbody>
    </table>
    <div
      v-else
      style="
        height: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
      "
    >
      <h6 style="color: #344767">아직 등록된 관심 목록이 없어요!</h6>
    </div>
  </div>
  <div v-else class="search-result-container">
    <SearchOneResult></SearchOneResult>
  </div>
</template>

<script>
import SearchOneResult from "../../../components/search/SearchOneResult.vue";
import { mapGetters, mapState, mapActions, mapMutations } from "vuex";
const aptStore = "aptStore";

export default {
  name: "SearchResult",
  computed: {
    ...mapState(aptStore, ["total", "pageNo"]),
    ...mapGetters(aptStore, [
      "curApt",
      "curPageAptDealInfo",
      "searchType",
      "curAptList",
      "likedList",
    ]),
  },
  components: {
    SearchOneResult,
  },
  data() {
    return {
      tradingInfo: [],
      curPage: 1,
    };
  },

  created() {
    console.log("resultType", this.searchType);
  },
  methods: {
    ...mapActions(aptStore, ["getDealInfo", "getLikedApt", "getAptDetail"]),
    ...mapMutations(aptStore, ["SET_SEARCH_RESULT_TYPE"]),

    // 테이블 tr을 눌렀을 때
    searchByAptName(aptCode) {
      this.getAptDetail(aptCode);
      this.getDealInfo({ pageNo: 1, aptCode: aptCode });
      this.SET_SEARCH_RESULT_TYPE("addr-one");
    },
  },
};
</script>

<style scoped>
.search-result-container {
  position: absolute;
  top: 160px;
  left: 20px;
  z-index: 9;
  width: 394px;
  height: 390px;
  min-height: calc(100% - 170px);
  background-color: #fff;
  border-radius: 1rem;
  overflow-y: scroll;
  box-shadow: 0 2px 12px 0 rgb(0 0 0 / 16%);
}

table {
  font-size: 0.8rem;
}

tr:hover {
  background-color: #f8f9fa;
  cursor: pointer;
}

li a {
  font-size: 0.75rem !important;
}

.page-item.active .page-link {
  background-color: #596cff !important;
  border-color: #596cff !important;
}
</style>
