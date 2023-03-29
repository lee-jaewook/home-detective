<template>
  <div id="search-container">
    <div class="search-bar">
      <!-- 모드 선택 (일반 검색, 관심 지역)-->
      <div class="search-checkbox-wrapper">
        <SearchCheckbox
          v-for="(item, index) in checkboxList"
          :key="index"
          :name="item.name"
          :iconName="item.iconName"
        ></SearchCheckbox>
      </div>

      <!-- 검색 input -->
      <SearchInput></SearchInput>
    </div>
  </div>
</template>

<script>
import SearchInput from "@/components/search/SearchInput.vue";
import SearchCheckbox from "../../../components/search/SearchCheckbox.vue";
export default {
  name: "SearchBar",
  components: {
    SearchInput,
    SearchCheckbox,
  },
  data() {
    return {
      selectedWord: "지역, 아파트 이름으로 검색",
      checkboxList: [
        {
          iconName: "fa-star",
          name: "관심매물",
        },
        {
          iconName: "fa-school",
          name: "교육시설",
          "data-order": "0",
        },
        {
          iconName: "fa-car",
          name: "대중교통",
          "data-order": "1",
        },
        {
          iconName: "fa-hospital",
          name: "병원",
          "data-order": "2",
        },
      ],
    };
  },
  methods: {},
};
</script>

<style scoped>
#search-container {
  position: absolute;
  top: 20px;
  left: 20px;
  z-index: 10;
  /* broswer width
     width: 28%; 
     min-width: 394px;
  */
  width: 394px;
  background-color: none;
}
.search-bar {
  width: 100%;
  height: 130px;
  background-color: #fff;
  padding: 10px;
  border-radius: 1rem;
  box-shadow: 0 2px 12px 0 rgb(0 0 0 / 16%);
}

.search-checkbox-wrapper {
  display: flex;
  width: 80%;
  height: 60px;
  justify-content: space-between;
  align-items: center;
  margin: 0px auto;
}
</style>
