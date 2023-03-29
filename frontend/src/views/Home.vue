<template>
  <div class="pt-7 py-3 container">
    <home-slide></home-slide>
    <trading-trends></trading-trends>
    <!-- <home-search-bar></home-search-bar> -->
    <!-- <div
      class="mt-4 page-header min-height-600 border-radius-xl"
      :style="{
        backgroundImage: 'url(' + require('@/assets/img/curved-images/background-main.jpg') + ')',
        backgroundPositionY: '50%',
      }"
    >
      <span class="mask bg-gradient-to-r from-cyan-500 to-blue-500 opacity-6"></span>
      <div>
        <h1>hihi</h1>
      </div>
    </div> -->
  </div>
</template>

<script>
import HomeSlide from "../components/home/HomeSlide.vue";
import TradingTrends from "../components/home/TradingTrends.vue";
export default {
  name: "Home",
  components: {
    HomeSlide,
    TradingTrends,
  },
  methods: {},
};
</script>
