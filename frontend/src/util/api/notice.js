import { apiInstance } from "./index.js";

const api = apiInstance();

/* 모든 공지사항 조회 */
const getAllNotice = async (success, fail) => {
  await api.get(`/notice`).then(success).catch(fail);
};

/* 단일 공지사항 조회 */
const getOneNotice = async (id, success, fail) => {
  await api.get(`/notice/${id}`).then(success).catch(fail);
};

/* 공지사항 등록 */
const registerNotice = async (requestBody, success, fail) => {
  await api.post(`/notice`, requestBody).then(success).catch(fail);
};

/* 공지사항 수정 */
const modifyNotice = async (requestBody, success, fail) => {
  await api.put(`/notice`, requestBody).then(success).catch(fail);
};

/* 공지사항 삭제 */
const deleteNotice = async (id, success, fail) => {
  await api.delete(`/notice/${id}`).then(success).catch(fail);
};

export {
  getAllNotice,
  getOneNotice,
  registerNotice,
  modifyNotice,
  deleteNotice,
};
