package com.dy.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.dy.domain.UserDTO;
import com.dy.service.UserService;

/**
 * AuthenticationProvider => 클라이언트가 입력한 정보와 DB에서 가지고 온 사용자 정보를 비교할 때 사용하는 인터페이스
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * 로그인 인증 처리
	 * @param authentication 클라이언트가 입력한 정보를 담은 오브젝트
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String username = authentication.getName();
		String password = (String) authentication.getCredentials();

		// TODO => 예외 처리 어떻게 할지 생각해보기
		// TODO => 권한이 없는 경우는 어떻게 해야 할지?
		UserDTO user = (UserDTO) userService.loadUserByUsername(username);
		if (user == null) {
			throw new InternalAuthenticationServiceException("account does not exist...");
			// TODO => 어떤 것을 사용하는 것이 좋은지, 또는 다른 좋은 방법이 있는지 찾아보기
			// throw new UsernameNotFoundException("user does not exist...");
		} else if (passwordEncoder.matches(password, user.getPassword()) == false) {
			throw new BadCredentialsException("passwords do not match...");
		}
		// TODO => 계정 활성화 관련 프로퍼티 = throw new AuthenticationCredentialsNotFoundException();

		return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
		// TODO => 어떤 것을 사용하는 것이 좋은지, 또는 다른 좋은 방법이 있는지 찾아보기
		// return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}