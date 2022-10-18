# 간단한 배달앱

MVVM 아키텍처를 기반으로 Compose 를 사용해 간단한 배달앱의 기능을 구현해 보는 것이 목표 입니다.

### [기술 명세서]

언어 : kotlin 구조 : MVVM (model, view, view model)

### 실행 화면

1. 첫번째
   ![실행화면](./readmedata/appRun01_scaleDown.gif)

2. 두번째
   ![실행화면](./readmedata/appRun01_scaleDown.gif)

3. 세법째
   ![실행화면](./readmedata/appRun01_scaleDown.gif)

ACC :

- hilt (DI, dependency injection)
- navigation
- viewModel

프레임 워크 & 라이브러리 :

- retrofit
- coil
- compose
- google map
- tmap
- Gson
- MDC (Material Design Sources)
- room (Database)
- firebase Auth
- cameraX

DB:

- firebaseStorage
- firebaseStore

### [현재완료된 기능 목록]

1. 구글 맵 상에 중앙 좌표값 가져오기
2. 바텀 네비게이션 설정
3. 권한 퍼미션 설정
4. 현재 위치 가져오기 & 현 위치에 주변 가게 정보 가져오기

### [앞으로 추가할 기능]

- [x] 배달 후기 작성
- [x] 찜 기능
- [x] 사진 활영
- [x] 로그인, 로그아웃 (room 활용)
- [x] 알림과 딥링크
- [x] 장바구니
- [x] 주문하기 및 알람 딥링크 설정
- [ ] UI 디자인 개선