<template>
  <nav style="--bs-breadcrumb-divider: '>'" aria-label="breadcrumb">
    <ol class="breadcrumb" style="background-color: transparent">
      <li class="breadcrumb-item">관리자 Q&A</li>
    </ol>
  </nav>
  <div class="card mb-4 h-100">
    <div class="card-body px-0 pt-0 pb-2">
      <div v-if="allQnA.length !== 0" class="table-responsive p-0">
        <table class="table align-items-center mb-0">
          <thead style="text-align: center">
            <tr>
              <th
                class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7"
              >
                No.
              </th>
              <th
                class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2"
              >
                회원
              </th>
              <th
                class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2"
              >
                제목
              </th>
              <th
                class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7"
              >
                답변 상태
              </th>
              <th
                class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7"
              >
                등록일
              </th>
            </tr>
          </thead>
          <tbody style="text-align: center">
            <tr
              v-for="(qna, index) in allQnA"
              :key="qna.id"
              @click="moveToDetails(qna.id)"
            >
              <td>
                <h6 class="mb-0 text-sm">{{ index + 1 }}</h6>
              </td>
              <td>
                <p class="text-xs font-weight-bold mb-0">{{ qna.userId }}</p>
              </td>
              <td>
                <p class="text-xs font-weight-bold mb-0">{{ qna.title }}</p>
              </td>
              <td class="align-middle text-center text-sm">
                <soft-badge
                  v-if="qna.countReply !== '0'"
                  color="secondary"
                  variant="gradient"
                  size="sm"
                  >답변 완료</soft-badge
                >
                <soft-badge v-else color="info" variant="gradient" size="sm"
                  >진행중</soft-badge
                >
              </td>
              <td class="align-middle text-center">
                <span class="text-secondary text-xs font-weight-bold">{{
                  qna.regTime
                }}</span>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <div v-else id="qna-no-data">진행중인 QnA가 없습니다:)</div>
    </div>
  </div>
</template>

<script>
import SoftBadge from "@/components/SoftBadge.vue";
import { mapActions, mapGetters, mapMutations } from "vuex";
const qnaStore = "qnaStore";
const userStore = "userStore";
export default {
  name: "qna-table",
  data() {
    return {};
  },
  computed: {
    ...mapGetters(qnaStore, ["allQnA", "allAnswer"]),
    ...mapGetters(userStore, ["checkUserInfo"]),
  },
  created() {
    if (this.checkUserInfo !== null) {
      this.getAllQnAList(this.checkUserInfo.id);
      console.log(this.allQnA);
    }
  },
  methods: {
    ...mapActions(qnaStore, ["getAllQnAList", "getAllAnswerList"]),
    ...mapMutations(qnaStore, ["SET_MESSAGE", "GET_ALL_ANSWER"]),

    moveToDetails(num) {
      this.GET_ALL_ANSWER([]);
      this.getAllAnswerList(num);
      this.$router.push({ name: "QnAAdminDetail", params: { num: num } });
    },
    moveToWrite() {
      this.$router.push({ name: "QnAdminWrite" });
    },
  },
  components: {
    SoftBadge,
  },
};
</script>

<style scoped>
tbody tr {
  cursor: pointer;
}
tbody tr:hover {
  background-color: #f8f9fa;
}

#qna-no-data {
  text-align: center;
  line-height: 100px;
  height: 100px;
  color: #67748e;
}

#write-btn {
  color: #344767;
  font-size: 0.9rem;
  font-weight: 400;
}

#add-btn {
  position: absolute;
  top: 6px;
  right: 164px;
  font-weight: bold;
}
</style>
