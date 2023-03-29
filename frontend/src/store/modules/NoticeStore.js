import {
  getAllNotice,
  getOneNotice,
  registerNotice,
  modifyNotice,
  deleteNotice,
} from "../../util/api/notice";
const qnaStore = {
  namespaced: true,
  state: {
    noticeList: [], // 모든 공지사항 목록
    notice: {}, // 공지사항 1건
    msg: "", // 처리 결과 메세지
  },
  getters: {
    allNotice: function (state) {
      return state.noticeList;
    },
    curNotice: function (state) {
      return state.notice;
    },
    curMsg: function (state) {
      return state.msg;
    },
  },
  mutations: {
    SET_MESSAGE(state, msg) {
      state.msg = msg;
    },
    GET_ALL_NOTICE(state, noticeList) {
      state.noticeList = noticeList;
    },

    GET_ONE_NOTICE(state, notice) {
      state.notice = notice;
    },
  },
  actions: {
    getAllNoticeList: ({ commit }) => {
      getAllNotice(
        ({ data }) => {
          commit("GET_ALL_NOTICE", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getOneNotice: ({ commit }, id) => {
      getOneNotice(
        id,
        ({ data }) => {
          commit("GET_ONE_NOTICE", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    registerNotice: ({ commit }, noticeData) => {
      registerNotice(
        noticeData,
        () => {
          commit("SET_MESSAGE", "등록되었습니다:)");
        },
        (error) => {
          console.log(error);
          commit("SET_MESSAGE", "[등록 실패]다시 시도해주세요:(");
        }
      );
    },
    deleteNotice: ({ commit }, id) => {
      console.log(`deleteNotice ${id}`);
      deleteNotice(
        id,
        () => {
          commit("SET_MESSAGE", "삭제되었습니다:)");
        },
        (error) => {
          console.log(error);
          commit("SET_MESSAGE", "[삭제 실패]다시 시도해주세요:(");
        }
      );
    },
    modifyNotice: ({ commit }, noticeData) => {
      modifyNotice(
        noticeData,
        () => {
          commit("SET_MESSAGE", "수정되었습니다:)");
        },
        (error) => {
          console.log(error);
          commit("SET_MESSAGE", "[수정 실패]다시 시도해주세요:(");
        }
      );
    },
  },
};

export default qnaStore;
