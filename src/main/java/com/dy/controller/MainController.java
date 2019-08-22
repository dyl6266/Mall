package com.dy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping(value = "/index")
	public String openIndexPage() {

		return "index";
	}

	@GetMapping(value = "/upload")
	public String openUpload() {
		return "upload";
	}

	/**
	 * TODO => 로그인 페이지를 GetMapping으로 처리하면 LoginFailureHandler에서 forward 처리에 문제가 생김
	 * 
	 * @return 페이지
	 */
	@RequestMapping(value = "/login")
	public String openLoginPage() {

		return "login";
	}

}
