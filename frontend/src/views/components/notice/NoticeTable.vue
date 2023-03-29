<template>
  <nav style="--bs-breadcrumb-divider: '>'" aria-label="breadcrumb">
    <ol class="breadcrumb" style="background-color: transparent">
      <li class="breadcrumb-item">공지사항</li>
    </ol>
  </nav>
  <div v-show="isAdmin" id="add-btn" class="btn" @click="moveToWrite">
    <i class="fa fa-plus fa-flip-horizontal" style="margin-right: 2px"></i>
    등록
  </div>

  <div class="card mb-4 h-100">
    <div class="card-body px-0 pt-0 pb-2">
      <div v-if="allNotice.length !== 0" class="table-responsive p-0">
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
                제목
              </th>
              <th
                class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7"
              >
                등록일
              </th>
              <th
                class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7"
              >
                조회수
              </th>
            </tr>
          </thead>
          <tbody style="text-align: center">
            <tr
              v-for="(notice, index) in allNotice"
              :key="notice.id"
              @click="moveToDetails(notice.id)"
            >
              <td>
                <h6 class="mb-0 text-sm">{{ index + 1 }}</h6>
              </td>
              <td>
                <p class="text-xs font-weight-bold mb-0">
                  {{ notice.title }}

                  <span v-if="currentDate(notice.regTime)" class="notion-status"
                    >new</span
                  >
                </p>
              </td>
              <td class="align-middle text-center">
                <span class="text-secondary text-xs font-weight-bold">{{
                  notice.regTime
                }}</span>
              </td>
              <td>{{ notice.viewCount }}</td>
            </tr>
          </tbody>
        </table>
      </div>
      <div v-else id="notice-no-data">등록된 공지사항이 없습니다:)</div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters, mapState } from "vuex";
const noticeStore = "noticeStore";
const userStore = "userStore";
export default {
  name: "NoticeTable",
  data() {
    return {
      noticeList: [],
    };
  },

  computed: {
    ...mapGetters(noticeStore, ["allNotice"]),
    ...mapState(userStore, ["isAdmin"]),
  },

  methods: {
    ...mapActions(noticeStore, ["getAllNoticeList"]),
    currentDate(noticeDate) {
      const current = new Date();
      const date = `${current.getFullYear()}-${
        current.getMonth() + 1
      }-${current.getDate()}`;
      noticeDate = noticeDate.substring(0, 10);
      console.log(noticeDate);
      console.log(noticeDate === date);
      if (noticeDate === date) return true;
      else return false;
    },
    moveToDetails(num) {
      this.$router.push({ name: "NoticeDetail", params: { num: num } });
    },
    moveToWrite() {
      this.$router.push({ name: "NoticeWrite" });
    },
  },
  created() {
    // api 호출
    this.getAllNoticeList();
  },
  components: {},
};
</script>

<style scoped>
tbody tr {
  cursor: pointer;
}
tbody > tr:hover {
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

.notion-status {
  width: 40px;
  height: 24px;
  background-color: #596bff;
  border-radius: 8px;
  padding: 2px 4px;
  color: #fff;
  line-height: 24px;
  font-size: 0.75rem;
  margin-left: 2px;
}

#notice-no-data {
  height: 200px;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
