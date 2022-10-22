jp-backend

`JWT Token` 

* Server Side
  * 처음 발급할 때 access token과 refresh token 2개 발급 
  * redis 블랙리스트에 해당 토큰의 존재여부만 빠르게 확인(로그아웃 했을수도 있으니)
  * access token은 30분, refresh token은 일주일
  * access token은 username을 payload에 가지고 필요할 때마다 파싱해서 쓸 수 있게 함
  * refresh token은 redis에 key: username, value: token 형태로 가지고 있음
  * Client가 refresh token과 만료된 access token을 가지고 재발급 요청을 보내면 1. 만료된 토큰에서 username을 얻고(refresh token에 user정보를 담지 않기 위해서) 2. username을 key로 redis에 있는지 확인하고 3. 유효기간도 확인
  * 위에서 통과를 못하면 다시 로그인시켜서 refresh+access token을 처음부터 발급받게 함
  * Refresh token은 payload에 아무 정보도 저장하지 않고 그저 유저가 가지고 있던 것과 redis에 저장된것이 같은지만 확인한다. 물론 변조되지 않았는지와 만료되지 않았는지는 확인한다.
* Client Side
  * access token은 일반 쿠키로 가지고 있다.
  * refresh token은 일반적인 쿠키보다는 안전한 곳에 가지고 있을 것(그게 어디냐 도대체..)
  * 가지고 있는 access token을 보내기전에 expiration date를 확인해 30초 이하로 남았으면 즉시 refresh token과 access token을 함께 보내서 갱신 요청하기(유저 너는 몰라도돼)
  * Refresh 성공하면 기존 token은 무조건 날린다.
* 로그아웃 정책
  * 클라이언트에서는 access token과 refresh token을 모두 삭제한다.
  *서버는 access token과 refresh token을 모두 redis 블랙리스트에 올려 요청이 들어와도 주체적인 거부(?)를 한다.