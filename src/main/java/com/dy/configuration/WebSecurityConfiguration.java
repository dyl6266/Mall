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

import com.dy.security.CustomAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomAuthenticationProvider authenticationProvider;

	/* 스프링에서 권장하는 Hash 알고리즘 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
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
		http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN")
//			.antMatchers("/goods/**").hasAnyRole("ADMIN", "MANAGER", "USER")
				.anyRequest().authenticated();

		http.csrf().disable(); // TODO => 추후에 제거하기

		http.formLogin().loginPage("/login").loginProcessingUrl("/authentication").defaultSuccessUrl("/goods/list")
//			.successHandler(null)
				.failureUrl("/login?fail")
//			.failureHandler(null)
				.usernameParameter("email").passwordParameter("password").permitAll();

		http.logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout").invalidateHttpSession(true).permitAll();
//			.logoutSuccessHandler()
//			.logoutRequestMatcher() TODO => 이건 뭔지 찾아보자
	}

}
