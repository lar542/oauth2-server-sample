# Spring Boot로 간단한 OAuth2 시스템 만들기
## OAuth2
* 웹 또는 앱에서 가장 많이 접하는 OAuth2 인증
* 페이스북, 구글, 카카오, 네이버 인증은 대부분 OAuth2 인증을 지원한다.
* 개발자들은 클라이언트 형태로 위와 같은 인증 서비스를 사용해 시스템과 연동하지만 직접 OAuth2 인증 서버를 구축하는 형태는 잘 없다.
* 어느 정도 규모있는 회나마 공공기관에서 OAuth2 인증 시스템을 구현하게 되면 연동하여 개발하기가 훨씬 수월하고 관리하기 쉬워진다.
* 다른 인증시스템과 달리 권한도 가지고 있다.
* Spring Security의 서브 프로젝트인 Spring Security OAuth를 이용하여 OAuth 인증 시스템(클라이언트와 서버)을 구축할 수 있는 프레임워크를 제공하고 있다.
* Spring Boot에서 최소한의 설정으로 OAuth2 시스템을 개발할 수 있도록 지원하고 있다.

## 개발 예정
1. 간단한 API 서버
2. API를 보호하는 OAuth2 인증 서버
3. 테스트할 수 있는 OAuth2 클라이언트

* API 서버와 OAuth2 인증 서버가 같이 있는 서버 → 두 개의 서버로 나누기

## 의존성
* Spring Security : OAuth2 서버를 만들 때 필요
* Spring Data JPA : DB에 JPA로 접근
* Rest Repositories : Spring Data Rest를 Spring Boot에 맞도록 쉽게 사용할 수 있는 형태. 
REST API 서버를 쉽게 만들어 준다.(Web을 포함하지 않아도 스프링 MVC를 사용할 수 있는 의존성 제공)
* H2 : 가벼운 내장형 DB
* Lombok
* pom.xml에 OAuth2 의존성 추가


출처 : [Spring Boot로 만드는 OAuth2 시스템](https://brunch.co.kr/@sbcoba/1)