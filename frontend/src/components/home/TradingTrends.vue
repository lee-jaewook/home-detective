<template>
  <div class="pb-4 px-0 container-fluid">
    <div class="row">
      <div class="col-xl-3 col-sm-6 mb-xl-0 mb-4"></div>
    </div>
    <div class="row">
      <div class="col-lg-8 mb-lg-0 mb-4">
        <div class="card">
          <div class="card-body p-3">
            <div class="row">
              <div class="col-lg-6">
                <div class="d-flex flex-column h-100">
                  <p class="mb-1 pt-2 text-bold">뉴스 > 사회</p>
                  <h5 class="font-weight-bolder" v-html="news.title"></h5>
                  <p class="mb-4 new-text" v-html="news.description"></p>
                  <a target="_blank" class="text-body text-sm font-weight-bold mb-0 icon-move-right mt-auto" :href="news.link">
                    Read More
                    <i class="fas fa-arrow-right text-sm ms-1" aria-hidden="true"></i>
                  </a>
                </div>
              </div>
              <div class="col-lg-6 ms-auto text-center mt-5 mt-lg-0">
                <div class="border-radius-lg h-100" id="news-img" :style="{ 'background-image': 'url(' + news.img + ')' }"></div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="col-lg-4">
        <div class="card h-100 p-3">
          <div class="overflow-hidden position-relative border-radius-lg bg-cover h-100">
            <span class="mask bg-dark bg-gradient"></span>
            <div class="card-body position-relative z-index-1 p-3 h-100">
              <div class="d-flex flex-column h-100">
                <h5 class="text-white font-weight-bolder mb-3 pt-1">인기 거래 지역 TOP5</h5>
                <div class="rank-wrapper d-flex" v-for="loc in popularLocations" :key="loc.id">
                  <label class="rank-name col-lg-2">{{ loc.name }} </label>
                  <div class="progress col-lg-8" style="height: 4px">
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
        <div class="z-index-2">
          <div class="card-body">
            <mini-statistics-card
              title="올해 거래 금액"
              value="1430조원"
              :percentage="{
                value: '+505%',
                color: 'text-success',
              }"
              :icon="{
                component: 'fa fa-solid fa-money-bill',
                background: iconBackground,
              }"
              direction-reverse
            />
            <mini-statistics-card
              title="올해 거래 건수"
              value="447,2301건"
              :percentage="{
                value: '+3%',
                color: 'text-success',
              }"
              :icon="{
                component: 'fa fa-solid fa-handshake',
                background: iconBackground,
              }"
              direction-reverse
            />
            <mini-statistics-card
              title="지난 달 대비 거래금액"
              :value="'+2102억'"
              :percentage="{
                value: '-2%',
                color: 'text-danger',
              }"
              :icon="{
                component: 'fa fa-solid fa-coins',
                background: iconBackground,
              }"
              direction-reverse
            />
            <mini-statistics-card
              title="지난 달 대비 거래건수"
              :value="'+1105건'"
              :percentage="{
                value: '+5%',
                color: 'text-success',
              }"
              :icon="{
                component: 'fa fa-solid fa-building',
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
            title="월별 거래량 & 거래 금액"
            description="<i class='fa fa-arrow-down text-success'></i>
      <span class='font-weight-bold'>4% less</span> in 2022"
            :chart="{
              labels: months,
              datasets: [
                {
                  label: '거래량(단위: 천건)',
                  data: deals,
                },
                {
                  label: '거래 금액(단위: 조원)',
                  data: amounts,
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
import US from "@/assets/img/icons/flags/US.png";
import DE from "@/assets/img/icons/flags/DE.png";
import GB from "@/assets/img/icons/flags/GB.png";
import BR from "@/assets/img/icons/flags/BR.png";
import { faHandPointer, faUsers, faCreditCard, faScrewdriverWrench } from "@fortawesome/free-solid-svg-icons";
import { getTopGugunList, getDealInfoByMonth, getNewsInfoInMain } from "../../util/api/apt.js";
export default {
  name: "TradingTrends",
  data() {
    return {
      news: {},
      deals: [],
      amounts: [],
      months: [],
      iconBackground: "bg-gradient-success",
      faCreditCard,
      faScrewdriverWrench,
      faUsers,
      faHandPointer,
      popularLocations: [],
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
  methods: {
    formatToPrice(value) {
      return `${Number(value).toFixed(0)}`;
    },
    setInit(data) {
      this.deals = [];
      this.amounts = [];
      this.months = [];
      data.forEach((item) => {
        // console.log(item.dealYear, item.dealMonth, item.deal, item.amount);
        this.deals.push(item.deal);
        this.amounts.push(item.amount);
        this.months.push(item.dealYear + " " + item.dealMonth);
      });
    },
  },
  created() {
    getTopGugunList(
      ({ data }) => {
        for (let index = 0; index < data.length; index++) {
          let tmp = {
            id: index + 1,
            name: data[index].sidoName + " " + data[index].gugunName,
            rate: data[index].deal,
          };
          this.popularLocations.push(tmp);
        }
      },
      (error) => {
        console.log(error);
      }
    );
    getDealInfoByMonth(
      ({ data }) => {
        this.setInit(data);
      },
      (error) => {
        console.log(error);
      }
    );
    getNewsInfoInMain(
      "서울 부동산 폭락",
      ({ data }) => {
        // console.log(data[0].img);
        // console.log(data[0].link);
        this.news = data[0];
      },
      (error) => {
        console.log(error);
      }
    );
  },
};
</script>
<style scoped>
#news-img {
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

.new-text {
  min-height: 50px;
  font-size: 12px;
}
</style>
