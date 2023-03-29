<template>
  <div class="card mb-4 h-100">
    <div class="card-header pb-0">
      <h6>관심 아파트 목록</h6>
    </div>
    <div class="card-body px-0 pt-0 pb-2">
      <div class="table-responsive p-0">
        <table class="table align-items-center mb-0">
          <thead>
            <tr>
              <th
                class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7"
              >
                No.
              </th>
              <th
                class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2"
              >
                아파트명
              </th>
              <th
                class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7"
              >
                주소
              </th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="(apt, index) in likedList"
              :key="apt.aptCode"
              class="like-tr"
              @click="handleSearchApt(apt.aptCode)"
            >
              <td>
                <div class="d-flex px-3 py-1">
                  <div class="d-flex flex-column justify-content-center">
                    <h6 class="mb-0 text-sm">{{ index + 1 }}</h6>
                  </div>
                </div>
              </td>
              <td>
                <p class="text-xs font-weight-bold mb-0">
                  {{ apt.apartmentName }}
                </p>
              </td>
              <td class="align-middle text-center">
                <span class="text-secondary text-xs font-weight-bold">
                  {{ apt.sidoName }} {{ apt.gugunName }} {{ apt.dongName }}
                  {{ apt.jibun }}
                </span>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters, mapMutations } from "vuex";
const aptStore = "aptStore";
const userStore = "userStore";
export default {
  name: "like-table",
  data() {
    return {};
  },
  created() {
    console.log(this.checkUserInfo);
    this.getLikedAptList(this.checkUserInfo.id);
  },
  computed: {
    ...mapGetters(aptStore, ["likedList"]),
    ...mapGetters(userStore, ["checkUserInfo"]),
  },
  methods: {
    ...mapActions(aptStore, ["getAptDetail", "getDealInfo"]),
    ...mapMutations(aptStore, [
      "SET_SEARCH_RESULT_TYPE",
      "SET_SEARCH_WORD",
      "SET_SHOW_SEARCH_RESULT",
    ]),

    ...mapActions(aptStore, ["getLikedAptList"]),
    handleSearchApt(aptCode) {
      this.getAptDetail(aptCode); // 정보 요청
      this.getDealInfo({ pageNo: 1, aptCode: aptCode }); // 거래 정보 요청
      this.SET_SEARCH_RESULT_TYPE("apt"); // 결과 타입 설정
      this.SET_SEARCH_WORD(this.searchWord); // 검색어 저장
      this.SET_SHOW_SEARCH_RESULT(true); // 결과 컴포넌트 보이게 설정
      this.$router.push("/search"); // 검색 페이지로 이동
    },
  },
};
</script>
