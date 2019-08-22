package com.dy.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.dy.interceptor.LoggerInterceptor;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

	/**
	 * addPathPataterns() => 특정 패턴의 주소 추가 excludePathPatterns() => 특정 패턴의 주소 제외
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoggerInterceptor())
		.excludePathPatterns("/css/**", "/font/**", "/images/**", "/jquery/**", "/js/**");
	}

	/**
	 * 파일 업로드로 들어오는 데이터를 처리하는 빈(Bean)
	 * 
	 * @return MultipartResolver
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

	/**
	 * 메시지 소스를 생성
	 * 
	 * @return ReloadableResourceBundleMessageSource
	 */
	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:/messages/*.properties");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(60);
		messageSource.setUseCodeAsDefaultMessage(true);
		return messageSource;
	}

	/**
	 * 변경된 언어 정보를 기억하는 로케일 리졸버를 생성
	 * @return SessionLocaleResolver
	 */
	@Bean
	public SessionLocaleResolver localeResolver() {
		return new SessionLocaleResolver();
	}

}
