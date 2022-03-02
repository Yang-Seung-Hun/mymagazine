# mymagazine

기능구현

1. 회원가입
   - 스프링 스큐리티를 사용하여 회원가입 구현
   
2. 로그인/로그아웃
   - 스프링 시큐리티를 사용하여 로그인/아웃 구현 ==> jwt로 바꾸고 로그아웃 제외
   
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
|회원가입|POST|/api/signup|username,name,password,password_check(JSON)|HttpStatus 및 메시지|
|로그인|POST|api/signin|username, password(JSON)|HttpStatus 및 메시지|
|게시글조회|GET|/api/posts||(postId, name, contents, imt_url ,like_cnt(좋아요 수), create_date, modified_date, like_ok)(JSON)|
|게시글추가|POST|/api/posts/{id}|contents,imageurl(JSON)|(postId, name, contents, img_url ,like_cnt(좋아요 수), create_date, modified_date, like_ok)(JSON)|
|게시글수정|PATCH|/api/posts/{id}|contents,img_url(JSON)|(postId, name, contents, img_url ,like_cnt(좋아요 수), create_date, modified_date, like_ok)(JSON)|
|게시글삭제|DELETE|/api/posts/{id}|||
|좋아요|POST|/api/favorite/{id}||post_Id, img_url, contents, create,modfied date,user_id, username, name, ncikname,like_cnt, like_ok|

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
문제해결

1. 모든 테이블을 단방향으로 설계하여 N+1문제는 발생하지 않은 것 같습니다.
   
   다만 단방향이다 보니 필요한 데이터가 필요할떄마다 전체 데이터를 가져와 걸러야한다는 문제 생김

2. 프론트와 붙였을때 cors 문제 발생
  
      -> 로그인/회원가입 파트 붙일때 cors 발생하여 @CrossOrigin으로 해결
  
      -> 게시글 파트 붙일때는 같은 방법으로 해결 안됨
     
         => Webconfig 클래스를 만들어 다양한 설정을 줌
     
         => 위 클래스를 적용하기 위해 스프링 스큐리티 설정에 .cors()를 붙여줌
      
3. cors해결하고 난 후 토큰 인증에서 알수 없는 에러가 남(올바른 토큰을 던져도 서버에서 검증을 못함)
      
      -> 프론트에서 받은 토큰(jwt)을 header에 넣어 던져 줄때 key를 'X-AUTH-TOKEN'으로 하지 않았음
      
            -> 헤더에서 토큰을 검증할때 쓰이는 로직에서 키 값을 'X-AUTH-TOKEN'으로 사용
  
      -> rds에 전에 쓰던 데이터들이 남아 있어 오류 발생(테이블 명이 겹쳤음)

4. 프론트에서 좋아요 요청시 header에 token값이 null로 날아옴

      -> 데이터 전송할 때 post요청시 데이터 한개라도 있어야함
      
      -> 더미데이터 같이 보내니까 해결됨
      
      -> 다음부터는 get으로 요청하는게 좋은 방법일것 같음
      



























  
