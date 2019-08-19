package com.example.webservice.domain.member;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * OAuth2 인증을 받기 위한 간단한 API 서버를 만든다.
 * API 서버에 접근하기 위한 인증과 권한은 OAuth2를 사용한다.
 * Spring Data JPA 레포지토리
 */
@RepositoryRestResource //@Controller를 만들지 않고 이 어노테이션만 붙여도 내부적으로 REST API가 만들어진다.
public interface MemberRepository extends PagingAndSortingRepository<Member, Long> {

}
