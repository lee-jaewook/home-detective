<template>
  <div
    class="min-height-600 pt-5 mb-4 page-header align-items-start min-vh-50 pb-11 border-radius-lg"
    :style="{
      backgroundImage: 'url(' + require('@/assets/img/curved-images/background-main.jpg') + ')',
    }"
  >
    <!-- <span class="mask bg-gradient-dark opacity-6"></span> -->
    <div class="container">
      <div class="row justify-content-center">
        <div class="mx-0 text-center col-lg-10">
          <h1 class="mt-7 mb-2">Find it. Tour it. Own it.</h1>
        </div>
      </div>
      <div class="row justify-content-center">
        <div class="mx-0 text-center col-lg-8">
          <!-- <h2 class="mt-7 text-danger">search bar 넣을 위치....</h2> -->
          <home-search-bar class="mt-7"></home-search-bar>
          <!-- <p class="text-white text-lead">Use these awesome forms to login or create new account in your project for free.</p> -->
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import HomeSearchBar from "./HomeSearchBar.vue";
export default {
  components: { HomeSearchBar },
};
</script>

<style>
/* #home-slide {
  height: 600px;
}
h1 {
  position: absolute;
  top: 20%;
  left: 50%;
  transform: translate(-50%, 0);
}
#search-bar {
  position: absolute;
  top: 100%;
  left: 50%;
  transform: translate(-50%, -50%);
} */
</style>
