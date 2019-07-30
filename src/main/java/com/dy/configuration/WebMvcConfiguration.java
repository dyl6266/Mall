package com.dy.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.dy.interceptor.LoggerInterceptor;

@Configuration
//@EnableWebMvc
public class WebMvcConfiguration implements WebMvcConfigurer {

	/*
	 * addPathPataterns() => 특정 패턴의 주소 추가 excludePathPatterns() => 특정 패턴의 주소 제외
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoggerInterceptor());
	}

	/*
	 * 파일 업로드로 들어오는 데이터를 처리하는 빈(Bean)
	 */
	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setDefaultEncoding("UTF-8");
		multipartResolver.setMaxUploadSize(5 * 1024 * 1024);
		multipartResolver.setMaxUploadSizePerFile(5 * 1024 * 1024);
		multipartResolver.setMaxInMemorySize(0);
		return multipartResolver;
	}

}
