# <img src="https://github.com/sook000/bfor/assets/148513154/68006e22-ce2c-47a1-b38d-b486bff92fbf" width="70" height="auto"> bfor 프로젝트

## &#128195; 프로젝트 개요
사용자 맞춤형 여행 계획 서비스입니다. 사용자가 직접 원하는 장소를 선택해서 여행 코스를 만들 수 있고, 여행 게시판, 자유게시판에서 자유롭게 소통할 수 있는 웹 사이트를 제작하였습니다. 

사용자가 찜한 장소를 기반으로 추천 장소를 제공하고, 여행 경로를 짤 때 최단 경로 코스를 제공함으로써 사용자의 만족감을 높이도록 하였습니다.



## 🕒 개발 기간 / 인원
- 기간: 2024.05.07~2024.05.23 
- 인원: 2명


## &#128296; 사용한 프레임워크
- Vue.js, Spring Boot, MyBatis, MySQL


## &#128214; 기능 소개


> ####  &#128421; 메인 화면
<img src="https://github.com/sook000/bfor/assets/148513154/86195399-d355-46e8-90db-1a9e5c85c26b" width="900">


### 주요 기능
1. 사용자 회원가입, 로그인, 마이페이지, 개인정보 수정 및 탈퇴
2. 자유게시판 글 작성/수정/삭제 가능, 댓글
3. 공지사항 관리자 글 작성/수정/삭제 가능
4. 여행 만들기 원하는 장소 검색, 찜 기능, 여행 경로에 추가, 최단 여행 경로 코스 제공
5. 여행 게시판 글 작성
6. 추천 페이지 추천 장소, 유사한 사용자, 좋아하는 카테고리 제시
7. 다른 유저의 마이페이지 열람 가능, 취향 유사도 제공



> #### 1. 사용자 회원가입, 로그인, 마이페이지, 개인정보 수정 및 탈퇴
- 아이디 중복 확인 기능
- 비밀번호 일치해야 함
- 비밀번호 DB에 저장 시 Bcrypt 암호화
- 로그인 시 JWT AccessToken, Refresh Token 사용
- 마이페이지에서 자신의 장소 찜 목록, 여행 게시물 목록, 작성한 글 확인 가능


> #### 2. 자유게시판 글 작성/수정/삭제 가능, 댓글
- 로그인 한 유저만 글 열람 가능
- 본인의 게시물에 대해 수정, 삭제 가능
- 댓글 기능


> #### 3. 공지사항 관리자 글 작성/수정/삭제 가능
- 모든 사용자 글 열람 가능
- 관리자(ID: admin)만 글 작성, 수정, 삭제 가능


> #### 4. 여행 만들기 원하는 장소 검색, 찜 기능, 여행 경로에 추가, 최단 여행 경로 코스 제공
<img src="https://github.com/sook000/bfor/assets/148513154/a71d780c-26a0-4ff3-8888-86168292a2be" width="900">


- 카카오맵 API 활용
- 원하는 카테고리 검색 또는 찜 목록에서 장소 선택 가능
- 원하는 장소 찜 기능
- 일반 거리: 장소 선택 순서대로 한 거리
- 최단 거리: 다익스트라를 이용해 최단 거리 제공(최단거리 순으로 장소 목록 함께 표시)


> #### 5. 여행 게시판 글 작성
- 카드 형식의 게시판 
- 무한 스크롤 기능


> #### 6. 추천 페이지 추천 장소, 유사한 사용자, 좋아하는 카테고리 제시
<img src="https://github.com/sook000/bfor/assets/148513154/f29318d9-d3a2-4681-84d4-f59096bb5d74" width="900">


- 로그인 한 사용자가 찜한 장소들 기반으로 추천 장소 제공 
  - 로그인 사용자의 여행장소 찜 리스트와 다른 사용자의 찜 리스트를 비교, 찜한 장소가 5개 이상 일치하면 겹치지 않는 장소들 추천
  - 로그인 사용자의 찜한 리스트 중 가장 좋아하는 카테고리의 여행 장소를 랜덤으로 5개 추천(첫 번째 방법에서 추천 장소 5개 미만일 때만 수행)
- 로그인 한 사용자의 가장 좋아하는 카테고리 표시
- 찜한 장소가 가장 많이 겹치는 사용자 표시


> #### 7. 다른 유저의 마이페이지 열람 가능, 취향 유사도 제공
<img src="https://github.com/sook000/bfor/assets/148513154/9767472d-a912-403f-aa43-901776a93457" width="900">


- 다른 유저의 마이페이지 열람 가능, 취향 유사도 제공
  - 찜한 장소가 일치하는 정도를 4단계로 나누어 상태바와 멘트로 표시


-----
##### 참고: 프로젝트 진행 노션
https://accurate-season-018.notion.site/bFor-1e367b969c864171a885ea72bef3d790



