import {
  getAllQnA,
  getAllAnswer,
  getOneQnA,
  registerQnA,
  deleteQnA,
  modifyQnA,
} from "../../util/api/qna";
const qnaStore = {
  namespaced: true,
  state: {
    qnaList: [], // 모든 QnA목록
    answerList: [], // 모든 답변 목록
    qna: {}, // QnA 1건
    msg: "", // 처리 결과 메세지
  },
  getters: {
    allQnA: function (state) {
      return state.qnaList;
    },
    allAnswer: function (state) {
      return state.answerList;
    },
    curQnA: function (state) {
      return state.qna;
    },
    curMsg: function (state) {
      return state.msg;
    },
  },
  mutations: {
    SET_MESSAGE(state, msg) {
      state.msg = msg;
    },
    GET_ALL_QNA(state, qnaList) {
      state.qnaList = qnaList;
    },
    GET_ALL_ANSWER(state, answerList) {
      state.answerList = answerList;
    },
    GET_ONE_QNA(state, qna) {
      state.qna = qna;
    },
  },
  actions: {
    getAllQnAList: ({ commit }, userId) => {
      getAllQnA(
        userId,
        ({ data }) => {
          commit("GET_ALL_QNA", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getAllAnswerList: ({ commit }, id) => {
      getAllAnswer(
        id,
        ({ data }) => {
          commit("GET_ALL_ANSWER", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getOneQna: ({ commit }, id) => {
      getOneQnA(
        id,
        ({ data }) => {
          commit("GET_ONE_QNA", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    registerQnA: ({ commit }, qnaData) => {
      registerQnA(
        qnaData,
        () => {
          commit("SET_MESSAGE", "등록되었습니다:)");
        },
        (error) => {
          console.log(error);
          commit("SET_MESSAGE", "[등록 실패]다시 시도해주세요:(");
        }
      );
    },
    deleteQnA: ({ commit }, id) => {
      deleteQnA(
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
    modifyQnA: ({ commit }, qnaData) => {
      modifyQnA(
        qnaData,
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
