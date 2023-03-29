import { getNearSchoolList, getNearStationList } from "../../util/api/near";

const nearInfoStore = {
  namespaced: true,
  state: {
    gugunName: "",
    sidoName: "",
    listOfSchool: [],
    listOfStation: [],
    isPressedSchool: false,
    isPressedStation: false,
    isPressedHospital: false,
  },
  getters: {
    curSchoolList: function (state) {
      return state.listOfSchool;
    },
    curStationList: function (state) {
      return state.listOfStation;
    },
    isPressedSchool: function (state) {
      return state.isPressedSchool;
    },
    isPressedStation: function (state) {
      return state.isPressedStation;
    },
    isPressedHospital: function (state) {
      return state.isPressedHospital;
    },
    curSidoName: function (state) {
      return state.sidoName;
    },
    curGugunName: function (state) {
      return state.gugunName;
    },
  },
  mutations: {
    GET_NEAR_SCHOOLS(state, schools) {
      state.listOfSchool = schools;
    },
    GET_NEAR_STATIONS(state, stations) {
      state.listOfStation = stations;
    },
    IS_PRESSED_SCHOOL_BTN(state, isPressed) {
      state.isPressedSchool = isPressed;
    },
    IS_PRESSED_STATION_BTN(state, isPressed) {
      state.isPressedStation = isPressed;
    },
    IS_PRESSED_HOSPITAL_BTN(state, isPressed) {
      state.isPressedHospital = isPressed;
    },
    SET_SIDO_NAME(state, sidoName) {
      state.sidoName = sidoName;
    },
    SET_GUGUN_NAME(state, gugunName) {
      state.gugunName = gugunName;
    },
  },
  actions: {
    getNearSchoolList: ({ commit }, searchAddr) => {
      const { sido, gugun } = searchAddr;
      console.log(sido, gugun);
      getNearSchoolList(
        sido,
        gugun,
        ({ data }) => {
          commit("GET_NEAR_SCHOOLS", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getNearStation: ({ commit }, sido, gugun) => {
      getNearStationList(
        sido,
        gugun,
        ({ data }) => {
          commit("GET_NEAR_STATIONS", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default nearInfoStore;
