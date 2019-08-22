package com.dy.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import com.dy.service.UserService;

// TODO => SimpleUrlAuthenticationSuccessHandler를 상속받는 게 나을지 확인해보기
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private UserService userService;

	public LoginSuccessHandler(String defaultTargetUrl) {
		this.defaultTargetUrl = defaultTargetUrl;
	}

	private String defaultTargetUrl;

	/* Spring Security에서 제공하는 클라이언트의 요청을 저장하고 불러올 수 있는 인터페이스 */
	private RequestCache requestCache = new HttpSessionRequestCache();
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		String username = request.getParameter("username");
		/* 실패 카운트 초기화 */
		userService.initializeLoginFailureCount(username);
		/* 로그인 실패 에러 세션 제거 */
		clearAuthenticationAttributes(request);

		/* 로그인 화면 이전의 URI를 가지고 오는 데 사용하는 오브젝트 */
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		if (savedRequest != null) {
			/*  권한이 필요한 페이지로 이동한 경우 */
			String targetUrl = savedRequest.getRedirectUrl();
			redirectStrategy.sendRedirect(request, response, targetUrl);

		} else {
			/* 직접 로그인 페이지로 이동한 경우 */
			redirectStrategy.sendRedirect(request, response, request.getContextPath() + defaultTargetUrl);
		}
	}

	/**
	 * 로그인 실패 에러 세션 제거
	 * 
	 * @param request
	 */
	protected final void clearAuthenticationAttributes(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		}

		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}

}
