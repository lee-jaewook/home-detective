import { apiInstance } from "./index.js";

const api = apiInstance();

const searchNews = async (word, success, fail) => {
  await api.get(`/naver/news/${word}`).then(success).catch(fail);
};

export { searchNews };
