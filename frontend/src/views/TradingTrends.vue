<template>
  <div class="pt-7 pb-4 container-fluid">
    <div class="row">
      <div class="col-xl-3 col-sm-6 mb-xl-0 mb-4"></div>
    </div>
    <div class="row">
      <div class="col-lg-7 mb-lg-0 mb-4">
        <div class="card">
          <div class="card-body p-3">
            <div class="row">
              <div class="col-lg-6">
                <div class="d-flex flex-column h-100">
                  <p class="mb-1 pt-2 text-bold">뉴스 > 사회</p>
                  <h5 class="font-weight-bolder">
                    서울 거래절벽 어디까지..역대 최저 거래량 기록
                  </h5>
                  <p class="mb-5">
                    서울의 아파트 '거래 절벽'이 역대 최악의 수준을 보이고 있다.
                    30일 서울부동산정보광장 통계에 따르면...
                  </p>
                  <a
                    target="_blank"
                    class="text-body text-sm font-weight-bold mb-0 icon-move-right mt-auto"
                    href="https://www.dnews.co.kr/uhtml/view.jsp?idxno=202203301344481330033&section=S1N1"
                  >
                    Read More
                    <i
                      class="fas fa-arrow-right text-sm ms-1"
                      aria-hidden="true"
                    ></i>
                  </a>
                </div>
              </div>
              <div class="col-lg-6 ms-auto text-center mt-5 mt-lg-0">
                <div class="border-radius-lg h-100" id="news-img"></div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="col-lg-5">
        <div class="card h-100 p-3">
          <div
            class="overflow-hidden position-relative border-radius-lg bg-cover h-100"
            style="
              background-image: url('https://demos.creative-tim.com/soft-ui-dashboard/assets/img/ivancik.jpg');
            "
          >
            <span class="mask bg-gradient-dark"></span>
            <div class="card-body position-relative z-index-1 p-3 h-100">
              <div class="d-flex flex-column h-100">
                <h5 class="text-white font-weight-bolder mb-3 pt-1">
                  인기 거래 지역 TOP5
                </h5>
                <div
                  class="rank-wrapper d-flex"
                  v-for="loc in popularLocations"
                  :key="loc.id"
                >
                  <label class="rank-name">{{ loc.name }} </label>
                  <div class="progress col-lg-5" style="height: 4px">
                    <div
                      class="progress-bar progress-bar-striped progress-bar-animated"
                      role="progressbar"
                      aria-label="top1"
                      :aria-valuenow="loc.rate"
                      aria-valuemin="0"
                      aria-valuemax="100"
                      :style="{ width: `${loc.rate}%`, height: '8px' }"
                    ></div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="mt-4 row">
      <div class="mb-4 col-lg-5 mb-lg-0">
        <div class="card z-index-2">
          <div class="p-3 card-body">
            <mini-statistics-card
              title="올해 거래 금액"
              value="$53,000"
              :percentage="{
                value: '+505%',
                color: 'text-success',
              }"
              :icon="{
                component: 'ni ni-money-coins',
                background: iconBackground,
              }"
              direction-reverse
            />
            <mini-statistics-card
              title="올해 거래 건수"
              value="2,300"
              :percentage="{
                value: '+3%',
                color: 'text-success',
              }"
              :icon="{
                component: ' ni ni-world',
                background: iconBackground,
              }"
              direction-reverse
            />
            <mini-statistics-card
              title="New Clients"
              value="+3,462"
              :percentage="{
                value: '-2%',
                color: 'text-danger',
              }"
              :icon="{
                component: 'ni ni-paper-diploma',
                background: iconBackground,
              }"
              direction-reverse
            />
            <mini-statistics-card
              title="Sales"
              value="$103,430"
              :percentage="{
                value: '+5%',
                color: 'text-success',
              }"
              :icon="{
                component: 'ni ni-cart',
                background: iconBackground,
              }"
              direction-reverse
            />
          </div>
        </div>
      </div>
      <div class="col-lg-7">
        <!-- line chart -->
        <div class="card z-index-2">
          <gradient-line-chart
            id="chart-line"
            title="월별 거래 건수"
            description="<i class='fa fa-arrow-up text-success'></i>
      <span class='font-weight-bold'>4% more</span> in 2021"
            :chart="{
              labels: [
                'Apr',
                'May',
                'Jun',
                'Jul',
                'Aug',
                'Sep',
                'Oct',
                'Nov',
                'Dec',
              ],
              datasets: [
                {
                  label: 'Mobile Apps',
                  data: [50, 40, 300, 220, 500, 250, 400, 230, 500],
                },
                {
                  label: 'Websites',
                  data: [30, 90, 40, 140, 290, 290, 340, 230, 400],
                },
              ],
            }"
          />
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import MiniStatisticsCard from "@/examples/Cards/MiniStatisticsCard.vue";
import GradientLineChart from "@/examples/Charts/GradientLineChart.vue";
import US from "../assets/img/icons/flags/US.png";
import DE from "../assets/img/icons/flags/DE.png";
import GB from "../assets/img/icons/flags/GB.png";
import BR from "../assets/img/icons/flags/BR.png";
import {
  faHandPointer,
  faUsers,
  faCreditCard,
  faScrewdriverWrench,
} from "@fortawesome/free-solid-svg-icons";
export default {
  name: "TradingTrends",
  data() {
    return {
      iconBackground: "bg-gradient-success",
      faCreditCard,
      faScrewdriverWrench,
      faUsers,
      faHandPointer,
      popularLocations: [
        {
          id: 1,
          name: "서울특별시 강남구",
          rate: 62,
        },
        {
          id: 2,
          name: "서울특별시 성북구",
          rate: 46,
        },
        {
          id: 3,
          name: "경기도 평택시",
          rate: 42,
        },
        {
          id: 4,
          name: "경기도 용인시",
          rate: 32,
        },
        {
          id: 5,
          name: "부산광역시 중구",
          rate: 18,
        },
      ],
      sales: {
        us: {
          country: "United States",
          sales: 2500,
          value: "$230,900",
          bounce: "29.9%",
          flag: US,
        },
        germany: {
          country: "Germany",
          sales: "3.900",
          value: "$440,000",
          bounce: "40.22%",
          flag: DE,
        },
        britain: {
          country: "Great Britain",
          sales: "1.400",
          value: "$190,700",
          bounce: "23.44%",
          flag: GB,
        },
        brasil: {
          country: "Brasil",
          sales: "562",
          value: "$143,960",
          bounce: "32.14%",
          flag: BR,
        },
      },
    };
  },
  components: {
    MiniStatisticsCard,
    GradientLineChart,
  },
};
</script>
<style scoped>
#news-img {
  background-image: url("../assets/img/img-news.jpg");
  background-size: cover;
  background-repeat: no-repeat;
}

.rank-wrapper {
  align-items: center;
  margin-bottom: 14px;
}
.rank-wrapper:last-child {
  margin-bottom: 0px;
}

.rank-name {
  color: #fff;
  line-height: 0.75rem;
  margin: 0px;
  width: 100px;
}
</style>
