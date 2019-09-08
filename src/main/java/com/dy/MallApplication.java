package com.dy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;

//@EnableJpaAuditing
// 애플리케이션이 실행될 때 basePackage로 지정된 패키지의 하위에서 JPA 엔티티(@Entity 애너테이션이 설정된 도메인 클래스)를 검색
// Jsr310JpaConverters를 사용하지 않으면 스프링 부트의 자동 설정에 의해서 처리될 베이스 패키지를 지정해야 하는 단점이 생김
//@EntityScan(basePackageClasses = { Jsr310JpaConverters.class }, basePackages = { "com.dy" })
@SpringBootApplication(exclude = { MultipartAutoConfiguration.class })
public class MallApplication {

	public static void main(String[] args) {
		SpringApplication.run(MallApplication.class, args);
	}

}
