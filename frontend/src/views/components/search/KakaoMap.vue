<template>
  <div id="map"></div>
</template>

<script>
import { mapState, mapActions, mapMutations, mapGetters } from "vuex";
import userIcon from "../../../assets/img/markers/user.png";
import likeIcon from "../../../assets/img/markers/like.png";
import homeIcon from "../../../assets/img/markers/building.png";
import schoolIcon from "../../../assets/img/markers/school.png";
import stationIcon from "../../../assets/img/markers/station.png";
import hospitalIcon from "../../../assets/img/markers/hospital.png";
const aptStore = "aptStore";
const userStore = "userStore";
const nearInfoStore = "nearInfoStore";

export default {
  name: "KakaoMap",
  data() {
    return {
      map: {},
      markers: [], // 이전 마커들 저장
      likedMarkers: [], // 좋아요 마커
      schoolMarkers: [], // 학교 마커
      stationMarkers: [], // 지하철역 마커
      hospitalMarkers: [], // 병원 마커
      curUserAddress: {},
      categoryTypes: [], // 카테고리 목록
    };
  },

  computed: {
    ...mapGetters(userStore, ["checkUserInfo"]),
    ...mapGetters(nearInfoStore, [
      "curSchoolList",
      "isPressedSchool",
      "isPressedStation",
      "isPressedHospital",
    ]),
    ...mapState(aptStore, ["apt", "isShowSearchResult", "isPressedLikedBtn"]),
    ...mapGetters(aptStore, [
      "curApt",
      "curAptList",
      "searchAddrInfo",
      "searchType",
      "isPressedBtn",
      "likedList",
      "curUserLocation",
    ]),
  },
  watch: {
    curApt() {
      console.log("apt 변경");
      if (window.kakao == undefined || window.kakao.maps == undefined) {
        console.log("window", window);
        this.handleSetKakao();
        this.INIT_MAP_STATE(true);
        return;
      }
      this.displayOneMarker();
    },
    curAptList() {
      if (window.kakao == undefined || window.kakao.maps == undefined) {
        this.handleSetKakao();
        this.INIT_MAP_STATE(true);
        return;
      }
      this.displayMarkers();
    },
    isPressedSchool() {
      if (this.isPressedSchool) {
        this.categoryTypes.push("SC4");
        this.searchPlaces();
      } else {
        // 타입 삭제
        this.categoryTypes.splice(this.categoryTypes.indexOf("SC4"), 1);
        console.log(this.categoryTypes);
        // 학교 마커 모두 제거
        if (this.schoolMarkers.length > 0) {
          this.schoolMarkers.forEach((marker) => marker.setMap(null));
          this.schoolMarkers = [];
        }
      }
    },
    isPressedStation() {
      if (this.isPressedStation) {
        this.categoryTypes.push("SW8");
        this.searchPlaces();
      } else {
        // 타입 삭제
        this.categoryTypes.splice(this.categoryTypes.indexOf("SW8"), 1);
        console.log(this.categoryTypes);
        // 역 마커 모두 제거
        if (this.stationMarkers.length > 0) {
          this.stationMarkers.forEach((marker) => marker.setMap(null));
          this.stationMarkers = [];
        }
      }
    },
    isPressedHospital() {
      if (this.isPressedHospital) {
        this.categoryTypes.push("HP8");
        this.searchPlaces();
      } else {
        // 타입 삭제
        this.categoryTypes.splice(this.categoryTypes.indexOf("HP8"), 1);
        console.log(this.categoryTypes);
        // 병원 마커 모두 제거
        if (this.hospitalMarkers.length > 0) {
          this.hospitalMarkers.forEach((marker) => marker.setMap(null));
          this.hospitalMarkers = [];
        }
      }
    },
    isPressedBtn() {
      if (this.isPressedBtn) this.displayLikedMarkers();
      else {
        // 좋아요 마커 모두 제거
        if (this.likedMarkers.length > 0) {
          this.likedMarkers.forEach((marker) => marker.setMap(null));
          this.likedMarkers = [];
        }
      }
    },
  },

  methods: {
    ...mapActions(aptStore, ["getAptDetail", "getDealInfo", "getLikedApt"]),
    ...mapActions(nearInfoStore, ["getNearSchoolList"]),
    ...mapMutations(aptStore, [
      "SET_SHOW_SEARCH_RESULT",
      "SET_SEARCH_RESULT_TYPE",
      "SET_APT_DETAIL",
      "IS_PRESSED_LIKED_BTN",
      "INIT_MAP_STATE",
    ]),
    ...mapMutations(nearInfoStore, [
      "SET_SIDO_NAME",
      "SET_GUGUN_NAME",
      "IS_PRESSED_SCHOOL_BTN",
      "IS_PRESSED_STATION_BTN",
      "IS_PRESSED_HOSPITAL_BTN",
    ]),

    initMap() {
      // 사용자 현재 위치 정보를 얻어왔다면, 해당 정보로 지도 위치 변경
      if (this.curUserLocation) {
        const container = document.getElementById("map");
        const options = {
          center: new kakao.maps.LatLng(
            this.curUserLocation.lat,
            this.curUserLocation.lng
          ),
          level: 8,
        };

        let userMarker = new kakao.maps.Marker({
          position: new kakao.maps.LatLng(
            this.curUserLocation.lat,
            this.curUserLocation.lng
          ),
          title: "현재 여기 있어요!", // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
          image: new kakao.maps.MarkerImage(
            userIcon,
            new kakao.maps.Size(35, 38)
          ),
        });

        this.map = new kakao.maps.Map(container, options);
        this.map.setLevel(1);
        userMarker.setMap(this.map);

        // 커스텀 오버레이 내용
        const content =
          '<div class="overlay-custom">' +
          "    <span>이 곳에 계신가요?</span>" +
          "</div>";

        // 커스텀 오버레이 좌표
        let position = new kakao.maps.LatLng(
          this.curUserLocation.lat,
          this.curUserLocation.lng
        );

        // 커스텀 오버레이를 생성
        let customOverlay = new kakao.maps.CustomOverlay({
          position: position,
          content: content,
          yAnchor: 2,
        });
        customOverlay.setMap(this.map);

        // 지도 컨트롤러 추가
        let mapTypeControl = new kakao.maps.MapTypeControl();
        let zoomControl = new kakao.maps.ZoomControl();
        this.map.addControl(
          mapTypeControl,
          kakao.maps.ControlPosition.TOPRIGHT
        );
        this.map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
        console.log(this.searchType, "타입 ");

        if (this.curApt && Object.keys(this.curApt).length !== 0) {
          this.displayOneMarker();
        }
        if (this.curAptList.length !== 0) {
          this.SET_SEARCH_RESULT_TYPE("addr");
          this.SET_SHOW_SEARCH_RESULT(true);
          this.displayMarkers();
        }
      }
    },

    // 아파트 이름으로 검색했을 때 또는 마커 클릭시
    displayOneMarker() {
      // 한건에 대한 정보 제공
      if (this.searchType === "apt") {
        if (this.markers.length > 0) {
          this.markers.forEach((marker) => marker.setMap(null));
        }
      }
      const imageSize = new kakao.maps.Size(32, 36);
      let position = new kakao.maps.LatLng(this.curApt.lat, this.curApt.lng);
      let marker = new kakao.maps.Marker({
        position: position,
        image: new kakao.maps.MarkerImage(homeIcon, imageSize),
      });
      // 관심 목록에 있는지 확인
      if (this.likedList.length > 0) {
        const tmp = this.likedList.filter(
          (likedApt) => likedApt.aptCode === this.curApt.aptCode
        );

        if (tmp.length !== 0) {
          let marker = new kakao.maps.Marker({
            position: position,
            image: new kakao.maps.MarkerImage(likeIcon, imageSize),
          });
          this.markers.push(marker);
          marker.setMap(this.map);
        } else {
          this.markers.push(marker);
        }
      } else {
        this.markers.push(marker);
      }

      marker.setMap(this.map);
      this.map.setLevel(1); // 구 단위로 보이게
      this.handleGetMarkerInfo(position);
      let moveLatLon = new kakao.maps.LatLng(this.curApt.lat, this.curApt.lng);
      this.map.setCenter(moveLatLon);
    },

    // 지역으로 검색했을 때
    displayMarkers() {
      // 이전 마커가 존재한다면 제거
      if (this.markers.length > 0) {
        this.markers.forEach((marker) => marker.setMap(null));
        this.markers = [];
      }
      const imageSize = new kakao.maps.Size(32, 36);
      // 새로 받아온 목록 기반으로 마커 생성
      for (let i = 0; i < this.curAptList.length; i++) {
        let marker = new kakao.maps.Marker({
          position: new kakao.maps.LatLng(
            this.curAptList[i].lat,
            this.curAptList[i].lng
          ),
          title: this.curAptList[i].apartmentName, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
          image: new kakao.maps.MarkerImage(homeIcon, imageSize),
        });
        marker.setMap(this.map);
        this.markers.push(marker);
      }
      this.setCenter();
      this.map.setLevel(6); // 구 단위로 보이게

      // 마커 클릭 이벤트 등록
      this.handleSetMarkerEvent(this.markers, this.curAptList);

      // 클러스터 설정
      // let clusterer = new kakao.maps.MarkerClusterer({
      //   map: this.map,
      //   averageCenter: true,
      //   minLevel: 3,
      // });
      // clusterer.addMarkers(this.markers);
    },

    displayLikedMarkers() {
      const imageSize = new kakao.maps.Size(32, 36);

      // 새로 받아온 목록 기반으로 마커 생성
      for (let i = 0; i < this.likedList.length; i++) {
        let marker = new kakao.maps.Marker({
          position: new kakao.maps.LatLng(
            this.likedList[i].lat,
            this.likedList[i].lng
          ),
          title: this.likedList[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
          image: new kakao.maps.MarkerImage(likeIcon, imageSize),
        });
        marker.setMap(this.map);
        this.likedMarkers.push(marker);

        // 마커 클릭 이벤트 등록
        this.handleSetMarkerEvent(this.likedMarkers, this.likedList);

        let moveLatLon = new kakao.maps.LatLng(
          this.likedList[i].lat,
          this.likedList[i].lng
        );
        this.map.setCenter(moveLatLon);
        this.map.setLevel(1);
      }
    },

    handleSetMarkerEvent(markers, aptList) {
      if (markers.length === 0) return;

      markers.map((marker, index) => {
        kakao.maps.event.addListener(marker, "click", () => {
          this.SET_SEARCH_RESULT_TYPE("addr-one");
          this.getAptDetail(aptList[index].aptCode); // 현재 선택된 아파트 상세 정보

          if (this.checkUserInfo.id !== null) {
            this.getLikedApt({
              aptCode: aptList[index].aptCode,
              userId: this.checkUserInfo.id,
            });
          }
          this.getDealInfo({ pageNo: 1, aptCode: aptList[index].aptCode }); // 거래 정보 요청
        });
      });
    },

    handleGetMarkerInfo(position) {
      // 로드뷰 생성
      const roadviewContainer = document.getElementById("search-road-view");
      let roadview = new kakao.maps.Roadview(roadviewContainer); //로드뷰 객체
      let roadviewClient = new kakao.maps.RoadviewClient(); // 로드뷰 helper객체

      // 특정 위치의 좌표와 가까운 로드뷰의 panoId를 추출하여 로드뷰를 띄운다.
      roadviewClient.getNearestPanoId(position, 50, function (panoId) {
        roadview.setPanoId(panoId, position); //panoId와 중심좌표를 통해 로드뷰 실행
      });

      // show resultComponent & 카메라 이동
      this.SET_SHOW_SEARCH_RESULT(true);
    },
    setCenter() {
      let moveLatLon = new kakao.maps.LatLng(
        this.searchAddrInfo.lat,
        this.searchAddrInfo.lng
      );
      this.map.setCenter(moveLatLon);
    },

    // 위경도 값을 주소로 변환
    changeCoordinateToAddress() {
      let geocoder = new kakao.maps.services.Geocoder();
      const { lat, lng } = this.curUserLocation;
      let callbackPromise = new Promise((resolve, reject) => {
        geocoder.coord2Address(lng, lat, function (result, status) {
          if (status === kakao.maps.services.Status.OK) {
            console.log("성공: ", result[0].address);
            resolve(result[0].address);
          } else return reject("error");
        });
      });
      return callbackPromise;
    },

    // 카테고리 설정
    searchPlaces() {
      kakao.maps.event.addListener(this.map, "idle", this.searchPlaces);

      // 지도 이벤트 추가
      let ps = new kakao.maps.services.Places(this.map);

      //test
      for (let i = 0; i < this.categoryTypes.length; i++) {
        ps.categorySearch(this.categoryTypes[i], this.placeSearchCB, {
          useMapBounds: true,
        });
      }
    },
    placeSearchCB(data, status) {
      if (status === kakao.maps.services.Status.OK) {
        console.log("place success!", data);
        this.displayPlaces(data);
      } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
        console.log("zero result !", status);
      } else if (status === kakao.maps.services.Status.ERROR) {
        console.log("error", status);
      }
    },
    displayPlaces(places) {
      console.log(places);
      let img;
      const imageSize = new kakao.maps.Size(32, 36);
      if (places[0].category_group_code === "SC4") {
        img = new kakao.maps.MarkerImage(schoolIcon, imageSize);
        for (let i = 0; i < places.length; i++) {
          let marker = new kakao.maps.Marker({
            position: new kakao.maps.LatLng(places[i].y, places[i].x),
            title: places[i].place_name, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
            image: img,
          });
          marker.setMap(this.map);
          this.schoolMarkers.push(marker);
        }
      } else if (places[0].category_group_code === "SW8") {
        img = new kakao.maps.MarkerImage(stationIcon, imageSize);
        for (let i = 0; i < places.length; i++) {
          let marker = new kakao.maps.Marker({
            position: new kakao.maps.LatLng(places[i].y, places[i].x),
            title: places[i].place_name, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
            image: img,
          });
          marker.setMap(this.map);
          this.stationMarkers.push(marker);
        }
      } else if (places[0].category_group_code === "HP8") {
        img = new kakao.maps.MarkerImage(hospitalIcon, imageSize);
        for (let i = 0; i < places.length; i++) {
          let marker = new kakao.maps.Marker({
            position: new kakao.maps.LatLng(places[i].y, places[i].x),
            title: places[i].place_name, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
            image: img,
          });
          marker.setMap(this.map);
          this.hospitalMarkers.push(marker);
        }
      }
    },
    handleSetKakao() {
      console.log("set Map ");
      if (this.curUserLocation != null) {
        console.log("사용자 찾기");
        if (!window.kakao || !window.kakao.maps) {
          console.log("스크립트 실행 중");
          const script = document.createElement("script");
          script.type = "text/javascript";
          script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=155540895e4eb28829531976ee0393d3&libraries=services,clusterer`;

          /* global kakao */
          script.addEventListener("load", () => {
            kakao.maps.load(() => {
              this.initMap();
            });
          });
          document.head.appendChild(script);
        } else {
          console.log("init중");
          this.initMap();
        }
      }
    },
  },
  created() {
    if (this.isPressedBtn) {
      this.IS_PRESSED_LIKED_BTN(false);
    }
    if (this.isPressedSchool) {
      this.IS_PRESSED_SCHOOL_BTN(false);
    }
    if (this.isPressedStation) {
      this.IS_PRESSED_STATION_BTN(false);
    }
    if (this.isPressedHospital) {
      this.IS_PRESSED_HOSPITAL_BTN(false);
    }
  },

  mounted() {
    console.log("mounted");
    this.handleSetKakao();
  },
};
</script>

<style>
#map {
  width: 100%;
  height: calc(80vh - 18px);
  border-radius: 0.75rem;
}
.overlay-custom {
  background-color: #fff;
  border-radius: 8px;
  padding: 10px;
  filter: drop-shadow(0px 4px 4px rgba(0, 0, 0, 0.25));
}
</style>
