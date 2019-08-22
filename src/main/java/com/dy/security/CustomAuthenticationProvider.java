package com.dy.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

		// TODO => 1. 예외 처리 어떻게 할지 생각해보기 / 2. 권한이 없는 경우는 어떻게 해야 할지? / 3. throw new에 지정한 메시지를 MessageSource로 변경하기
		UserDTO user = (UserDTO) userService.loadUserByUsername(username);
		if (user == null) {
			// TODO => InternalAuthenticationService와 UsernameNotFound 중 어떤 것을 사용하는 것이 좋은지, 또는 다른 좋은 방법이 있는지 찾아보기
			throw new UsernameNotFoundException("아이디 또는 비밀번호를 다시 확인해 주세요.");

		} else if (passwordEncoder.matches(password, user.getPassword()) == false) {
			throw new BadCredentialsException("아이디 또는 비밀번호를 다시 확인해 주세요.");

		} else if (user.isAccountNonLocked() == false) {
			throw new LockedException("계정이 잠겨 있습니다. 관리자에게 문의해 주세요.");
		}
		// TODO => 인증 요구가 거부되었을 때 던지는 예외 = throw new AuthenticationCredentialsNotFoundException();

		return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
		// TODO => 어떤 것을 사용하는 것이 좋은지, 또는 다른 좋은 방법이 있는지 찾아보기
		// return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
