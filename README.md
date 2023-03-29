# 🧡 제주인(JEJUINN) - 제주도 게스트 하우스 스텝 매칭 
<p align="center">
  <img src="https://user-images.githubusercontent.com/55774589/219983823-acb2f176-568f-4b94-bcc2-7cef1f26a6f0.png" />
</p>


## 🔗 JEJUINN 링크(WEB) : [https://jejuinn.com/](https://jejuinn.com/)

## 🎬 소개 영상 보기 : [UCC 링크](https://www.youtube.com/watch?v=qr8vFpzkLVY)

## 📅 프로젝트 진행 기간

2023.01.09~2023.02.17

## 🧡 JEJUINN - 배경

- 개인의 경험
    - 지역적 특성으로 인한 구직 과정의 불편함
    - 지원서 확인 여부를 알 수 없는 답답함
    - 게스트하우스에 대한 정보 부족(리뷰, 사장에 대한 정보 부족)
- 코로나 및 국내 정세로 인한 제주도 여행객 증가
- 설문을 통해 게스트하우스 사장님들의 서비스 필요성 확인

## 🧡 JEJUINN - 개요

- 게스트하우스 스태프로 한달 살기를 준비하는 사람들에게 머물 곳을 제공하는 서비스

## 🧡 주요 기능

---

- 게스트하우스
    - 입도일, 게스트하우스 스타일 등 지원자의 입장에서 필터 검색 기능을 제공합니다.
    - 한 눈에 게스트하우스의 위치, 정보, 사진을 확인할 수 있습니다.
    - 유저들은 게시글에 좋아요를 누르고, 덧글을 작성할 수 있습니다.
    <br/>
- 채용공고
    - 게스트하우스 스타일, 위치 등을 통해서 채용공고를 확인할 수 있습니다.
    - 홍보 사진, 복지, 직무 정보 등  다양한 정보를 제공하여 게스트하우스 선택에 도움을 줍니다.
    - 사장님들은 채용공고를 반복해서 작성하는 것이 아닌 채용 직무 만을 바꿈으로써 반복되는 업무를 줄일 수 있습니다.
    <br/>
- 관광지(스탭의 픽)
    - 스태프 경험이 있는 사람들의 리뷰를 통해 숨은 명소, 검증된 명소를 찾아볼 수 있습니다.
    - 위치 기반 검색, 지도 등을 통해 지역별 명소를 한 눈에 볼 수 있습니다.
    - 리뷰 등록 시 사진을 필수로 하여 신뢰도 있는 리뷰들을 제공받을 수 있습니다.
    - 리뷰 좋아요를 통해 작성자는 감귤당도(평점)을 올려 추후 합격에 도움을 받을 수 있고, 유저는 좋아요 한 관광지 목록을 통해 여행 계획 시 도움을 얻을 수 있습니다.
    <br/>
- 스태프 자동 추천 및 지원자 채용
    - AI 모델을 기반으로 지원서와 채용공고 내용을 비교하여 게스트하우스에 가장 적합한 3명의 지원자을 추천합니다.
    - 자동 추천 On/Off 기능을 통해 원하는 지원자만이 게스트하우스 사장님에게 자동 추천됩니다.
    - 지원자 또는 추천자의 스태프 이력, 이력서 등을 통해 원하는 지원자에게 채용 안내 문자를 보냅니다.
    - 원하는 지원자에게 이메일을 보내 화상 면접을 사용할 수 있도록 합니다.
    </br>
- 지원서 및 스태프 관리
    - 인증된 사용자만이 지원이 가능하도록 하여 게스트하우스 사장님들에게 신뢰성을 제공합니다.
    - 지원한 게스트하우스에 대하여 지원서 열람 여부를 제공하여 합격 유무 확인에 도움을 줍니다.
    - 자신의 게스트하우스에 대한 스태프 목록을 제공하여 스태프 관리에 도움을 줍니다.

## ✔ 주요 기술

---

**Backend - Spring**

- IntelliJ IDE
- Springboot 2.7.7
- Spring Data JPA
- Spring Security
- Jwt token
- Spring Validation
- Spring Web
- QueryDSL
- WebSocket
- MySQL
- Amazon Simple Storage Service(S3)
- Amazon Relation Database Service(RDS)
- GCP 3.0.0
- Swagger 3.0.0

**Backend - Flask**

- Flask
- PyTorch

**Frontend**

- Visual Studio Code IDE
- React 18.2.0
- MaterialUi 5.0.0-alpha.117
- Redux-toolkit 1.9.1
- Redux-persist 6.0.0”
- openvidu-browser 2.25.0

**CI/CD**

- AWS EC2
- Docker
- Jenkins
- NGINX
- SSL

## ✔ 프로젝트 파일 구조

---

### Back

```
Backend
├─api
│  ├─controller
│  ├─dto
│  │  ├─request
│  │  │  ├─email
│  │  │  ├─guesthouse
│  │  │  ├─recommender
│  │  │  ├─recruitment
│  │  │  ├─resumeinfo
│  │  │  ├─staff
│  │  │  └─user
│  │  ├─response
│  │  │  ├─comment
│  │  │  ├─guesthouse
│  │  │  ├─recommender
│  │  │  ├─recruitment
│  │  │  ├─resumeinfo
│  │  │  ├─staff
│  │  │  ├─travelplace
│  │  │  └─user
│  │  ├─search
│  │  ├─SMS
│  │  └─wrapper
│  └─service
│      ├─s3
│      └─social
├─config
│  └─jwt
├─db
│  ├─entity
│  ├─enums
│  └─repository
├─exception
└─util
    └─geotrans
```
### Front
```
Frontend
├─api
├─assets
├─components
│  ├─articleCreateComponent
│  ├─boxLoginSignup
│  ├─commentComponent
│  ├─findPassword
│  ├─guestHouseDetail
│  ├─guestHouseList
│  ├─imageSlider
│  ├─loginForm
│  ├─main
│  ├─mapApi
│  ├─myGuestHouseCreate
│  ├─myPage
│  │  ├─main
│  │  ├─myGuestHouse
│  │  └─myResume
│  ├─naverAuth
│  ├─popupPostCode
│  ├─sendSMS
│  ├─signUp
│  ├─speedDial
│  ├─staffPickComponent
│  ├─staffPickCreateComponent
│  ├─staffPickDetailComponent
│  ├─staffPickUpdateComponent
│  ├─videoInterview
│  ├─whiteBox
│  └─work
├─hooks
├─routes
│  ├─guestHouse
│  ├─guestHouseCreate
│  ├─guestHouseDetail
│  ├─logIn
│  ├─main
│  ├─myPage
│  ├─naverAuth
│  ├─signUp
│  ├─staffPick
│  ├─staffPickCreate
│  ├─staffPickDetail
│  ├─staffPickUpdate
│  ├─videoInterview
│  └─work
└─store
```


## ✔ 협업 툴

---

- Git
- Notion
- JIRA
- MatterMost
- Webex

## ✔ 협업 환경

---

- Gitlab
    - 코드의 버전을 관리
    - 이슈 발행, 해결을 위한 토론
    - MR시, 팀원이 코드리뷰를 진행하고 피드백 게시
    <br/>
- JIRA
    - 일주일을 기준으로 스프린트 실행
    - 팀원마다 주당 40의 Story Point를 설정
    - TODO ->In-Progress -> Done 순으로 작업
    <br/>
- 회의
    - 매일 아침, 저녁 스크럼 회의 진행
    - 매주 월요일 스프린트 회의 진행
    - 각 부문별로 책임자를 임명해 신속한 대응
    <br/>
- Notion
    - 회의가 있을때마다 회의록을 기록하여 보관
    - 기술확보 시, 다른 팀원들도 추후 따라할 수 있도록 보기 쉽게 작업 순서대로 정리
    - 컨벤션 정리
    - 간트차트 관리
    - 스토리보드, 스퀀스다이어그램, 기능명세서 등 모두가 공유해야 하는 문서 관리
    <br/>
## ✔ 팀원 역할 분배

---
<p align="center">
  <img src="https://user-images.githubusercontent.com/55774589/219984009-d994c6df-38df-4cbf-99fa-1fc093424eba.PNG" />
</p>

## ✔ 프로젝트 결과물
### 🧡 JEJUINN - 서비스 화면
#### 메인화면
- 홈페이지 설명
<p align="center">
  <img src="https://user-images.githubusercontent.com/55774589/222451140-c11a5a80-ace1-49ab-b9b3-60b9fe8d3a2a.gif"/>
</p>

#### 게스트하우스
- 게스트 하우스 목록
![게하목록](https://user-images.githubusercontent.com/55774589/222455564-186b5352-7876-4864-bb6b-dcb63f516b1f.gif)
- 게스트 하우스 검색
![게하 검색](https://user-images.githubusercontent.com/55774589/222455559-3d1948f0-89f3-4faf-a030-921cbcab8bcb.gif)
- 게스트 하우스 상세 정보
![게하 상세](https://user-images.githubusercontent.com/55774589/222455549-6dbd9711-097e-4b7b-bb88-ecac737e5249.gif)
- 새로운 게스트 하우스 등록
![게하 작성](https://user-images.githubusercontent.com/55774589/222455737-311eba17-7c4c-4b67-b3b7-7a3ba7472246.gif)





#### 놀고먹기 (주변 관광지)
- 새로운 명소 등록
![staffPickSpotRegister](https://user-images.githubusercontent.com/55774589/222455044-70cd173c-8f00-47f1-b41e-667290d96341.gif)
- 리뷰 작성
![staffPickCreateReview](https://user-images.githubusercontent.com/55774589/222455056-dbac6078-0279-40dc-8269-fe7ff52c4035.gif)
- 리뷰 수정
![staffPickUpdateReview](https://user-images.githubusercontent.com/55774589/222455060-182c0681-e19f-47e0-be99-071253998ec1.gif)
- 리뷰 삭제
![DeleteReview](https://user-images.githubusercontent.com/55774589/222455065-2b9971f5-8a4a-4956-89c7-2f7ff83d8907.gif)
- 리뷰 댓글 & 좋아요
![staffPickCommentLike](https://user-images.githubusercontent.com/55774589/222455070-8a32628f-3cf3-4854-8cb5-d3c31f7abdc5.gif)


#### 일하기
- 직무 추가
![직무 추가](https://user-images.githubusercontent.com/55774589/222454873-5e685310-1f42-43b0-b658-807b3ea3a283.gif)
- 일하기 살세
![일하기 상세](https://user-images.githubusercontent.com/55774589/222454879-772e674a-6b0a-42b2-b99f-455dae9c70b7.gif)
- 필터를 통한 일하기 검색
![일하기 필터](https://user-images.githubusercontent.com/55774589/222454885-f7b8066d-41cc-4c50-8d0a-12bc936cf05e.gif)



#### 마이페이지
- 좋아요 한 관광지 보기
<p align="center">
  <img src="https://user-images.githubusercontent.com/55774589/222454125-8fa94cd9-797b-42f7-af99-2192baf9b0a2.gif"/>
</p>

- 지원서 작성
<p align="center">
  <img src="https://user-images.githubusercontent.com/55774589/222454094-37d7c2ed-41cf-4120-8043-cc10d0b2e268.gif"/>
</p>

- 지원서 수정
<p align="center">
  <img src="https://user-images.githubusercontent.com/55774589/222454346-99415235-e005-434d-8c07-638c43cd2fb1.gif"/>
</p>

- 자동추천 on/off
<p align="center">
  <img src="https://user-images.githubusercontent.com/55774589/222453550-8105dfe8-cda6-4910-a5c2-eaab484fa569.gif"/>
</p>

- 추천 직원 리스트 보기
<p align="center">
  <img src="https://user-images.githubusercontent.com/55774589/222452475-5f06e39d-8ada-4595-b453-dc01d47f1e3e.gif"/>
</p>
- 지원자에게 안내 문자 발송
<p align="center">
  <img src="https://user-images.githubusercontent.com/55774589/222452457-17620602-4572-4b59-9d5e-a3ada674a39f.gif"/>
</p>
