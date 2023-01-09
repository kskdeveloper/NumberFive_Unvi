# 🏫 NumberFive_Unvi (Team Project)
Spring Boot 학사 관리 프로그램

### 🖋 프로젝트 소개
대학교 포털(학생정보시스템)을 참고하여 만든 학사관리 프로그램입니다.

### 🖋 프로젝트 목적 
- 실제 학사 관리 시스템을 기반으로 가상의 학사 홈페이지를 제작
- 국비 훈련 과정에서 배운 탄탄한 기본기와 다양한 CRUD 기능 학습 경험을 바탕으로 신기술을 찾아 접목.
- 프로젝트 내구성을 향상 및 재사용성 고려하여 제작 

### 📅 개발 기간 
* 22.10.26 - 22.12.09

### 🎓 팀원 소개
|<img src="https://user-images.githubusercontent.com/116548583/207245197-c605741b-b776-4ab6-92db-e05b3a31a0a0.png" width="65" height="55">|<img src="https://user-images.githubusercontent.com/116548583/207242549-9131e051-b994-43e0-b62c-eba2174f8b32.png" width="65" height="55">|<img src="https://user-images.githubusercontent.com/116548583/207240591-8581ba3e-1951-4545-949d-5391a1678a3e.png" width="65" height="55">|
|:---|:---:|:---:|  
|<a href="https://github.com/kskdeveloper">권수경</a>|<a href="https://github.com/AmVinch">이유빈</a>|<a href="https://github.com/lmmil22">신지아</a>


### 📌 커밋 메시지
* 개발한 기술 -본인 이름 형식
![커밋 메시지](https://user-images.githubusercontent.com/116548583/207213747-99c249db-4264-4c5c-a312-9f190cff80a3.png)


### ⚙ 사용 기술
<img src="https://user-images.githubusercontent.com/116548583/207210191-7faa86f3-d5aa-472c-9299-65f41a87edc1.png"  width="800" height="370">


### 🛠 주요 기능

<details>
<summary> 👨‍🎓 학생 관련</summary>
<div markdown="1">       

* 로그인 (Security)
   + 비밀번호 찾기 ( 이메일을 사용한 임시 비밀번호 찾기)
* 게시판 글 작성
* 수강 신청
    + 강의 자료 다운
* 휴학, 복학, 전과, 복수 전공 신청
* 실시간 채팅
* 내 정보 수정 
    + 임시비밀번호로 로그인 후 비밀번호 변경
    + 이메일 , 주소 등 수정
* 시간표 조회
* 점수 조회
* 학적신청현황 조회 
</div>
</details>

<details>
<summary> 🙍‍♀️ 학사 관련</summary>
<div markdown="1">       

* 회원 등록
* 게시판
    + 게시판 카테고리 등록
    + 게시판 공지글 작성
* 휴학, 복학, 전과, 복수 전공 승인
    + 일괄 승인 , 개별 승인 
    + 카카오톡으로 결과 메시지 전송
* 학사 경고, 제적 
    + 학사 경고/제적 안내 메일 전달
* 차트
   + 학적관리 신청/승인 
   + 학사경고/제적 현황
* 학사 일정 등록


</div>
</details>

<details>
<summary> 👩‍🏫 교수 관련</summary>
<div markdown="1">       

* 강의 등록
* 강의 목록
    + 강의 수정 ,삭제
    + 강의 자료 조회
* 점수 등록
* 시간표 조회

</div>
</details>


### 📸 담당파트 구현 영상보기
[![Video Label](http://img.youtube.com/vi/DOrw-ASF4Ug/0.jpg)](https://youtu.be/DOrw-ASF4Ug)

### 🛠 담당 파트

<details>
<summary> 👨‍🎓 학생 관련</summary>
<div markdown="1">

* 휴학, 복학, 전과, 복수 전공 신청
* 학적신청현황 조회 

</div>
</details>

<details>
<summary> 🙍‍♀️ 학사 관련</summary>
<div markdown="1"> 

* 휴학, 복학, 전과, 복수 전공 승인
    + 일괄 승인 , 개별 승인 
    + 카카오톡으로 결과 메시지 전송
* 학사 경고, 제적 
    + 학사 경고/제적 안내 메일 전달
* 차트
   + 학적관리 신청/승인 
   + 학사경고/제적 현황
   
</div>
</details>

<details>
<summary> 👨 기타</summary>
<div markdown="1">  

* CSS
  + 홈화면 TOP메뉴 설정
  + 드롭다운
  + 슬라이드 배너
  + 게시판 탭 메뉴 및 스크롤바
  
</div>
</details>


### 어려웠던 기능
- Map 데이터

휴학/복학, 전과/복수전공 관리자 승인 페이지에서 조건검색 기능과 데이터를 paramMap으로 주고 받는 기능이 어려웠습니다.
한 페이지에 두 가지 데이터를 불러와야 했으며, 조건 검색 기능도 한 페이지에서 휴학 영역과 복학 영역에서 실행되어야 했기 때문에 쿼리 작성 자체가 복잡했습니다.

### 
### 아쉬운 기능
- 카카오톡 나에게 보내기 API

관리자 승인 후 카카오톡 메시지 발송 기능을 구현하고 싶어서 구글링을 통하여 자바로 구현한 자료를 탐색했지만, 자료를 찾기 어려웠습니다. 또한,  Gradle로 카카오톡 메시지 보내기를 구현한 것이 대부분이었고, 팀프로젝트가 Maven으로 진행 중이었기에 대안으로 공유하기를 선택했습니다. 
카카오톡 나에게 보내기의 약점은 한 사람에게만 보내기 가능하다는 점이라 학사경고 알림 메시지 발송은 메일 발송 방법을 선택했습니다.
