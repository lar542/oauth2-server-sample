package com.example;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.sql.DataSource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.Data;
import lombok.NoArgsConstructor;

@EnableResourceServer//API 서버를 OAuth2에서 인증받게 만드는 역할
@EnableAuthorizationServer//OAuth2 인증 서버를 활성화시킴
@SpringBootApplication
public class Application extends ResourceServerConfigurerAdapter{

	/**
	 * 세부적인 설정을 위해 ResourceServerConfigurerAdapter 클래스를 상속받고 configure 메소드를 오버라이딩
	 * 기본 옵션 : 모든 API는 인증이 필요한 형태로 설정되어 있음
	 * OAuth2 인증을 확인하기 위해 OAuth2 토큰 스토어를 지정해야 하고, 직접 설정하지 않으면 인메모리 형태로 지정된다.
	 * OAuth2 서버와 API 서버가 같은 곳에서 처리되는 형태면 기본적으로 인메모리 토큰 스토어를 서로 공유한다.
	 */
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().disable();
		http.authorizeRequests()
			.anyRequest().permitAll()
			.antMatchers("/authorization-code-test").access("#oauth2.hasScope('read')");
	}

	/**
	 * API를 조회시 출력될 테스트 데이터
	 * @param memberRepository
	 * @return
	 */
	@Bean
	public CommandLineRunner commandLineRunner(MemberRepository memberRepository) {
		return args -> {
			memberRepository.save(new Member("이철수", "chulsoo", "test111"));
			memberRepository.save(new Member("김정인", "jungin11", "test222"));
			memberRepository.save(new Member("류정우", "jwryu991", "test333"));
		};
	}
	
	@Bean
	public TokenStore JdbcTokenStore(DataSource dataSource) {
		return new JdbcTokenStore(dataSource);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}

/**
 * OAuth2 인증을 받기 위한 간단한 API 서버를 만든다.
 * API 서버에 접근하기 위한 인증과 권한은 OAuth2를 사용한다.
 * Spring Data JPA 레포지토리
 */
@RepositoryRestResource //@Controller를 만들지 않고 이 어노테이션만 붙여도 내부적으로 REST API가 만들어진다.
interface MemberRepository extends PagingAndSortingRepository<Member, Long> {}

/**
 * API에서 사용될 도메인 
 */
@Entity
@Data
@NoArgsConstructor
class Member implements Serializable{

	@Id @GeneratedValue
	private Long id;
	private String name;
	private String username;
	private String remark;
	
	public Member(String name, String username, String remark) {
		this.name = name;
		this.username = username;
		this.remark = remark;
	}
}

/**
 * 권한 코드 테스트를 위해 만든 컨트롤러
 */
@Controller
@RequestMapping("test")
class TestController {
	@RequestMapping("authorization-code")
	@ResponseBody
	public String authorizationCodeTest(@RequestParam("code") String code) {
		String curl = String.format("curl " +
				"-F \"grant_type=authorization_code\" " +
				"-F \"code=%s\" " +
				"-F \"scope=read\" " +
				"-F \"client_id=foo\" " +
				"-F \"client_secret=bar\" " +
				"-F \"redirect_uri=http://localhost:8080/test/authorization-code\" " +
				"\"http://foo:bar@localhost:8080/oauth/token\"", code);
		return curl;
	}
}