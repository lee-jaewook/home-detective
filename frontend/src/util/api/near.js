import { apiInstance } from "./index.js";

const api = apiInstance();

const getNearSchoolList = async (sidoName, gugunName, success, fail) => {
  await api
    .get(`/near/school/${sidoName}/${gugunName}`)
    .then(success)
    .catch(fail);
};

const getNearStationList = async (sidoName, gugunName, success, fail) => {
  await api
    .get(`/near/station/${sidoName}/${gugunName}`)
    .then(success)
    .catch(fail);
};

export { getNearSchoolList, getNearStationList };
