# mymagazine

기능구현

1. 회원가입
   - 스프링 스큐리티를 사용하여 회원가입 구현
   
2. 로그인/로그아웃
   - 스프링 시큐리티를 사용하여 로그인/아웃 구현
   
3. 게시글 작성
   - 로그인 한 사용자만 게시글 작성 가능
  
4. 게시글 삭제/수정
   - 해당 게시글 작성한 사용자는 게시글을 삭제/수정 할 수 있음
 
5. 좋아요 추가/삭제
   - 로그인 한 사용자는 한 게시물 당 한번에 좋아요를 누를 수 있음
   - 게시글에 좋아요를 누른 사용자는 좋아요를 다시 눌러 취소 할 수 있음
  
5. 게시글 조회
   - 로그인 하지 않은 회원도 전체 게시글을 조회 할 수 있음
   - 로그인 하지 않은 회원에게는 모든 게시물의 좋아요는 표시되어 있지 않음

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
사용 기술 및 개발 환경

1. 언어 : 자바

2. 프레임워크 : 스프링 부트, 스프링 시큐리티

3. 데이터베이스 : mysql

4. 데이터베이스 관리 : jpa

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
DB 테이블 설계

![image](https://user-images.githubusercontent.com/81571069/155049065-9c4da670-af6c-486f-be17-5b2a3a240459.png)

* Post table의 img_url은 post객체의 title필도가 대체함
* 모든 연관관계는 단방향으로 설계함

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
api 설계

|내용|Method|api|request|response|
|-------|-----|-----------|------------------------------------|-------------------------------
|회원가입|POST|/api/signup|username,name,password(JSON)|HttpStatus 및 메시지|
|로그인|POST|api/signin|username, password(form형식)|HttpStatus 및 메시지|
|로그아웃|GET|/logout|없음|HttpStatus 및 메시지|
|게시글조회|GET|/api/posts||(postId, name, contents, title ,like_cnt(좋아요 수), create_date, modified_date, like_ok)(JSON)|
|게시글추가|POST|/api/posts/{id}|content,title(JSON)|(postId, name, contents, title ,like_cnt(좋아요 수), create_date, modified_date, like_ok)(JSON)|
|게시글수정|PATCH|/api/posts/{id}|content,title(JSON)|(postId, name, contents, title ,like_cnt(좋아요 수), create_date, modified_date, like_ok)(JSON)|
|게시글삭제|DELETE|/api/posts/{id}|||
|좋아요|POST|/api/favorite/{id}||post_Id, title, content, create,modfied date,user_id, username, name, ncikname,like_cnt, like_ok|





























  