package com.cafe24.config.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/*

Security Filter Chain
 
 1. ChannelProcessingFilter
 2. SecurityContextPersistenceFilter		( auto-config default [필수] )
 3. ConcurrentSessionFilter
 4. LogoutFilter							( auto-config default [필수] )
 5. UsernamePasswordAuthenticationFilter	( auto-config default [필수] )
 6. DefaultLoginPageGeneratingFilter		( auto-config default )
 7. CasAuthenticationFilter
 8. BasicAuthenticationFilter				( auto-config default [필수] )
 9. RequestCacheAwareFilter					( auto-config default )
10. SecurityContextHolderAwareRequestFilter	( auto-config default )
11. JaasApiIntegrationFilter
12. RememberMeAuthenticationFilter	=> 자동으로 설정되는 것은 아니지만 필요(로그인유지)
13. AnonymousAuthenticationFilter			( auto-config default )
14. SessionManagementFilter					( auto-config default )
15. ExceptionTranslationFilter				( auto-config default [필수] )
16. FilterSecurityInterceptor				( auto-config default [필수] )
												=> URL에 인증이 필요한지 확인해줌

 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsService userDetailsService;

	// 스프링 시큐리티 필터 연결작업
	// WebSecurity 객체
	// springSecurityFilterChain 이라는 이름의 
	// DelegatingFilterProxy Bean 객체를 생성
	// DelegatingFilterProxy Bean는 많은
	// Spring Security Filter Chain에 위임한다.
	@Override
	public void configure(WebSecurity web) throws Exception {
		//super.configure(web);
		// 예외가 되는 웹접근 URL을 설정한다.
		// ACL(Access Control List)에 등록하지 않을 URL을 설정

		//web.ignoring().antMatchers("/assets/**");
		//web.ignoring().antMatchers("/favicon.icon");
		
		web.ignoring().regexMatchers("\\A/assets/.*\\Z");
		web.ignoring().regexMatchers("\\A/favicon.icon\\Z");
		
		
	}
	
	/*
	/user/update	->	(ROLE_USER, ROLE_ADMIN)	-> Authenticated
	/user/logout	->	(ROLE_USER, ROLE_ADMIN)	-> Authenticated
	/user/write		->	(ROLE_USER, ROLE_ADMIN)	-> Authenticated
	/board/delete	->	(ROLE_USER, ROLE_ADMIN)	-> Authenticated
	/board/modify	->	(ROLE_USER, ROLE_ADMIN)	-> Authenticated
	/admin/**		->	ROLE_ADMIN(Authorized)

	allow all

	 */
	// Interceptor URL의 요청을 안전하게 보호(보안)
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 인증이 되어있을 때(authenticated?)
		http.authorizeRequests()
		.antMatchers("/user/update", "/user/logout").authenticated()
		.antMatchers("/board/write", "/board/delete", "/board/modify").authenticated()
		.antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")		// ADMIN Authority(ADMIN 권한, ROLE_ADMIN)
		
		.anyRequest().permitAll();		// 모두 허용

		// Temporary for Testing
		http.csrf().disable();
		
		//
		// 2. 로그인 설정
		//
		http.formLogin()
		.loginPage("/user/login")
		.loginProcessingUrl("/user/auth")
		.failureUrl("/user/login?result=fail")
		.defaultSuccessUrl("/", true)
		.usernameParameter("email")
		.passwordParameter("password");
		
		
		//
		// 3. 로그아웃 설정
		//
		http.logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
		.logoutSuccessUrl("/")
		.invalidateHttpSession(true);
	}
	
	// UserDetailService를 설정
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}



}
