package com.dy.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

public class CustomAccessDeniedHandler extends AccessDeniedHandlerImpl {

	public CustomAccessDeniedHandler(String errorPage) {
		this.errorPage = errorPage;
	}

	private String errorPage;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {

		super.setErrorPage(errorPage);
		super.handle(request, response, accessDeniedException);
	}

}
