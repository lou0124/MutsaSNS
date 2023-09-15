# Project_2_OhChangMin

SNS API를 제공 하는 서비스 입니다.

## 개발 환경

Spring Boot `3.1.2`

java `17`

h2 `2.1.214`

<br/>

## 주요 기능

### 회원 관련 기능
- 회원 가입을 할 수있습니다.
- 로그인을 하여 jwt토큰을 발급 받을 수 있습니다.
- 유저의 프로필 사진을 업데이트 할 수 있습니다.
- 다른 유저를 조회 할 수 있습니다.

### 피드 관련 기능
- 피드 등록, 삭제를 할 수 있습니다.
- 피드에 이미지를 등록, 삭제를 할 수 있습니다.
- 피드의 목록, 단건 조회가 가능합니다.
- 팔로워, 친구의 피드를 조회할 수 있습니다.

### 댓글 관련 기능
- 피드에 대한 댓글을 작성, 삭제, 수정을 할 수 있습니다.
- 피드에 좋아요를 할 수 있습니다.

### 팔로우, 친구 관련 기능
- 다른 유저를 팔로우 할 수 있습니다.
- 다른 유저와 친구 관계를 맺을 수 있습니다.

<br/>

## postman_collection

[API 문서](https://documenter.getpostman.com/view/26447331/2s9YC5yYPy)

[postman_collection](src%2Fmain%2Fresources%2Fstatic%2Fdata%2F%EB%A9%8B%EC%82%ACsns.postman_collection.json)

<br/>

### 실행 방법
1. 해당 깃 레포지토리를 clone 합니다.
2. applicaion.yml에서 file.dir에 이미지를 저장할 경로를 지정해야합니다.
3. gradle 빌드 후 실행

<br/>

### 개발 기간

2023.8.3 ~ 2023.8.8
