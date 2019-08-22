package com.dy.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.dy.domain.UserDTO;
import com.dy.service.UserService;

public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Autowired
	private UserService userService;

	public LoginFailureHandler(String defaultFailureUrl) {
		this.defaultFailureUrl = defaultFailureUrl;
	}

	private String defaultFailureUrl;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		String username = request.getParameter("username");
		String message = exception.getMessage();

		request.setAttribute("username", username);
		request.setAttribute("message", message);

		/* 비밀번호가 틀린 경우에만 실패 카운트 증가 (아이디가 존재하지 않거나, 이미 계정이 잠겨 있는 경우에는 update를 할 수 없음) */
		if (exception instanceof BadCredentialsException) {
			UserDTO user = (UserDTO) userService.loadUserByUsername(username);
			if (user.isAccountNonLocked()) {
				userService.checkLoginFailureCount(username);
			}
		}

		super.setDefaultFailureUrl(request.getContextPath() + defaultFailureUrl);
		super.setUseForward(true);
		super.onAuthenticationFailure(request, response, exception);
	}

}
