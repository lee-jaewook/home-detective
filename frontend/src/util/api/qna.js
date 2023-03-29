import { apiInstance } from "./index.js";

const api = apiInstance();

/* 모든 QnA목록 조회 */
const getAllQnA = async (userId, success, fail) => {
  await api.get(`/qna/${userId}/questions`).then(success).catch(fail);
};

/* 단일 QnA 조회 */
const getOneQnA = async (id, success, fail) => {
  await api.get(`/qna/${id}`).then(success).catch(fail);
};

/* QnA 1건에 대한 답글 조회 */
const getAllAnswer = async (id, success, fail) => {
  await api.get(`/qna/${id}/answers`).then(success).catch(fail);
};

/* QnA 등록 */
const registerQnA = async (requestBody, success, fail) => {
  await api.post(`/qna`, requestBody).then(success).catch(fail);
};

/* QnA 수정 */
const modifyQnA = async (requestBody, success, fail) => {
  await api.put(`/qna`, requestBody).then(success).catch(fail);
};

/* QnA 삭제 */
const deleteQnA = async (id, success, fail) => {
  await api.delete(`/qna/${id}`).then(success).catch(fail);
};

export {
  getAllQnA,
  getOneQnA,
  getAllAnswer,
  registerQnA,
  modifyQnA,
  deleteQnA,
};
