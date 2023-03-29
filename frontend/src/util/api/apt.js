import { apiInstance } from "./index.js";

const api = apiInstance();

/* 검색(아파트 이름) API */
const aptNameList = async (word, success, fail) => {
  await api.get(`/apt/find/aptname/${word}`).then(success).catch(fail);
};

/* 검색(지역 이름) API */
const addressList = async (word, success, fail) => {
  await api.get(`/apt/find/address/${word}`).then(success).catch(fail);
};

/*  특정 지역에 속한 아파트 목록API */
const aptListInArea = async (addr, success, fail) => {
  await api.post(`/apt/find/address`, addr).then(success).catch(fail);
};

/* 아파트 상세 정보 API */
const aptDetail = async (aptCode, success, fail) => {
  await api.get(`/apt/${aptCode}`).then(success).catch(fail);
};

/* 특정 아파트 과거 거래 내역 API */
const aptDealInfo = async (query, success, fail) => {
  await api.get(`/apt/deal?aptCode=${query.aptCode}&pageNo=${query.pageNo}`).then(success).catch(fail);
};

/* 사용자 관심 아파트 목록 */
const likedAptList = async (query, success, fail) => {
  await api.get(`/apt/like?userid=${query}`).then(success).catch(fail);
};

const isLikedApt = async (requestBody, success, fail) => {
  await api.post(`/apt/isliked`, requestBody).then(success).catch(fail);
};

/* 사용자 관심 아파트 등록 */
const registerLikeApt = async (requestBody, success, fail) => {
  await api.post(`/apt/like`, requestBody).then(success).catch(fail);
};

/* 사용자 관심 아파트 삭제 */
const deleteLikeApt = async (requestBody, success, fail) => {
  await api.post(`/apt/like/delete`, requestBody).then(success).catch(fail);
};

/* Top 5 거래 지역 */
const getTopGugunList = async (success, fail) => {
  await api.get(`/apt/deal/count/gugun`).then(success).catch(fail);
};

/* 월별 부동산 거래건수 */
const getDealInfoByMonth = async (success, fail) => {
  await api.get(`/apt/deal/count/month`).then(success).catch(fail);
};

/* 부동산 검색 */
const getNewsInfoInMain = async (word, success, fail) => {
  await api.get(`/naver/news/${word}`).then(success).catch(fail);
};

export { aptDetail, aptNameList, addressList, aptListInArea, aptDealInfo, likedAptList, isLikedApt, registerLikeApt, deleteLikeApt, getTopGugunList, getDealInfoByMonth, getNewsInfoInMain };
