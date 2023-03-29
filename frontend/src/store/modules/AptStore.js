import {
  aptDetail,
  aptNameList,
  addressList,
  aptListInArea,
  aptDealInfo,
  likedAptList,
  isLikedApt,
  registerLikeApt,
  deleteLikeApt,
} from "../../util/api/apt";
const houseStore = {
  namespaced: true,
  state: {
    userLocation: {},

    init: false,
    word: "", // 검색 할 아파트명
    searchAddr: {}, // 검색 할 지역 정보 저장
    aptList: [],
    keywordList: [], // 검색시, 자동완성 될 키워드 목록
    addressList: [], // 지역 이름 검색시, 자동완성 목록

    isShowSearchResult: false,
    searchResultType: "", // 리스트인지, 상세인지 구분

    /* 아파트 단일 조회*/
    apt: null,
    aptDealInfo: [], // 아파트 거래 목록
    total: 0,
    pageNo: 1,
    /* 관심 지역 관련 상태 */
    likedAptList: [], // 사용자 관심 지역 목록
    isPressedLikedBtn: false,
    isLiked: "", // success or fail
  },
  getters: {
    initState: function (state) {
      console.log("getter init", state.init);
      return state.init;
    },
    curSearchWord: function (state) {
      return state.word;
    },
    curUserLocation: function (state) {
      return state.userLocation;
    },
    curApt: function (state) {
      return state.apt;
    },
    curPageAptDealInfo: function (state) {
      return state.aptDealInfo;
    },
    curAptList: function (state) {
      return state.aptList;
    },
    searchType: function (state) {
      console.log("getter type", state.searchResultType);
      return state.searchResultType;
    },
    searchAddrInfo: function (state) {
      return state.searchAddr;
    },
    likedList: function (state) {
      return state.likedAptList;
    },
    isPressedBtn: function (state) {
      return state.isPressedLikedBtn;
    },
    isLikedApt: function (state) {
      return state.isLiked;
    },
  },
  mutations: {
    INIT_MAP(state) {
      console.log("상태 변경중 ");
      state.word = "";
      state.apt = {};
      state.aptList = [];
      state.searchResultType = "";
    },
    INIT_MAP_STATE(state, flag) {
      console.log("INIT_MAP_STATE: ", flag);
      state.init = flag;
    },
    INIT_MAP_MARKERS(state) {
      state.markers = [];
    },
    SET_SEARCH_WORD(state, word) {
      state.word = word;
    },
    SET_USER_LOCATION(state, userLocation) {
      state.userLocation = userLocation;
    },
    SET_MAP_MARKERS(state, markers) {
      state.markers = markers;
    },
    SET_SEARCH_RESULT_TYPE(state, type) {
      state.searchResultType = type;
      console.log("SET_SEARCH_RESULT_TYPE", state.searchResultType);
    },
    SET_AREA_APT_LIST(state, aptList) {
      state.aptList = aptList;
    },
    SET_APT_DETAIL(state, apt) {
      state.apt = apt;
    },
    SET_APT_DEAL(state, data) {
      const { deals, info } = data;
      state.aptDealInfo = deals;
      state.total = Math.ceil(info.totalCount / 5);
      state.pageNo = info.pageNo;
    },
    SET_KEYWORD_LIST(state, keywordList) {
      state.keywordList = keywordList;
    },
    SET_ADDRESS_LIST(state, addressList) {
      state.addressList = addressList;
    },
    SET_SHOW_SEARCH_RESULT(state, flag) {
      state.isShowSearchResult = flag;
    },
    SET_SEARCH_ADDRESS(state, searchAddr) {
      state.searchAddr = searchAddr;
    },
    IS_PRESSED_LIKED_BTN(state, isPressedLikedBtn) {
      state.isPressedLikedBtn = isPressedLikedBtn;
    },
    GET_LIKED_APT_LIST(state, likedAptList) {
      state.likedAptList = likedAptList;
    },
    GET_LIKED(state, isLiked) {
      state.isLiked = isLiked;
    },
  },
  actions: {
    getAptListInArea: ({ commit }, areaInfo) => {
      aptListInArea(
        areaInfo,
        ({ data }) => {
          commit("SET_AREA_APT_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getAptDetail: ({ commit }, aptCode) => {
      // 아파트 상세 정보
      aptDetail(
        aptCode,
        ({ data }) => {
          commit("SET_APT_DETAIL", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getDealInfo: ({ commit }, params) => {
      console.log("param?", params);

      aptDealInfo(
        params,
        ({ data }) => {
          commit("SET_APT_DEAL", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    searchByAptName: ({ commit }, word) => {
      aptNameList(
        word,
        ({ data }) => {
          commit("SET_KEYWORD_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    searchByAddress: ({ commit }, word) => {
      addressList(
        word,
        ({ data }) => {
          commit("SET_ADDRESS_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    // 관심지역 목록 불러오기
    getLikedAptList: ({ commit }, userId) => {
      likedAptList(
        userId,
        ({ data }) => {
          commit("GET_LIKED_APT_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    // 관심아파트 여부 가져오기
    getLikedApt: ({ commit }, likedInfo) => {
      isLikedApt(
        likedInfo,
        ({ data }) => {
          commit("GET_LIKED", data);
        },
        (error) => {
          commit("GET_LIKED", error.response.data);
        }
      );
    },
    // 관심지역 등록하기
    registerLikedApt: ({ commit }, likedApt) => {
      registerLikeApt(
        likedApt,
        () => {
          commit("GET_LIKED", "success");
        },
        (error) => {
          console.log(error);
        }
      );
    },
    deleteLikedApt: ({ commit }, likedApt) => {
      deleteLikeApt(
        likedApt,
        () => {
          console.log("AptStore");
          commit("GET_LIKED", "fail");
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};
export default houseStore;
