import { searchNews } from "../../util/api/news";
const newsStore = {
  namespaced: true,
  state: {
    word: "", // 초기 설정
    newsList: [],
    isLoading: false,
  },
  getters: {
    curNewsList: function (state) {
      return state.newsList;
    },
    curWord: function (state) {
      return state.word;
    },
  },
  mutations: {
    GET_NEWS_LIST(state, newsList) {
      state.newsList = newsList;
    },
    SET_SEARCH_WORD(state, word) {
      state.word = word;
    },
    SET_IS_LOADING(state, isLoading) {
      state.isLoading = isLoading;
    },
  },
  actions: {
    searchNews: ({ commit }, word) => {
      commit("SET_IS_LOADING", true);
      searchNews(
        word,
        ({ data }) => {
          console.log(word, " ===> ", data);
          commit("GET_NEWS_LIST", data);
          commit("SET_IS_LOADING", false);
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default newsStore;
