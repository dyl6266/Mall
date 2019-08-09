package com.dy.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.dy.security.CustomAuthenticationProvider;
import com.dy.security.CustomFilter;
import com.dy.security.LoginSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	/**
	 * 인증에 사용하는 클래스
	 */
	@Autowired
	private CustomAuthenticationProvider authenticationProvider;

	/**
	 * 스프링에서 권장하는 Hash 알고리즘
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationSuccessHandler successHandler() {
		return new LoginSuccessHandler();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/font/**", "/images/**", "/jquery/**", "/js/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/css/**", "/font/**", "/images/**", "/jquery/**", "/js/**").permitAll()
		.antMatchers("/user/**", "/users/**", "/goods/**").permitAll()
        .antMatchers("/admin/**").hasAnyRole("ADMIN", "MANAGER");
//        .anyRequest().authenticated();

		http.formLogin()
		.loginPage("/login")
		.loginProcessingUrl("/authentication")
		.successHandler(successHandler())
		.failureUrl("/login?fail")
//		.failureHandler(null)
		.usernameParameter("username")
		.passwordParameter("password")
		.permitAll();
//		.permitAll();

		http.logout()
		.logoutUrl("/logout")
		.logoutSuccessUrl("/login?logout")
		.invalidateHttpSession(true).permitAll();
//		.deleteCookies(cookieNamesToClear);
//		.logoutSuccessHandler()
//		.logoutRequestMatcher() TODO => 이건 뭔지 찾아보자
		
		http.addFilterAfter(new CustomFilter(), BasicAuthenticationFilter.class);
	}

}