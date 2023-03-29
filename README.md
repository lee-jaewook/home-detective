# 🧡 홈탐정 (home- detective) - 아파트 실거래 조회 서비스

## 📅 프로젝트 진행 기간

2022.11.17~2022.11.27

## 🧡 홈탐정 - 배경

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
  - 홍보 사진, 복지, 직무 정보 등 다양한 정보를 제공하여 게스트하우스 선택에 도움을 줍니다.
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
