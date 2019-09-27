package com.dy.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggerAspect {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/*
	 * Aspect(관점) : 구현하고자 하는 횡단 관심사의 기능을 의미. 한 개 이상의 포인트컷과 어드바이스의 조합으로 만들어짐
	 * Join Point(조인 포인트) : Aspect(관점)를 삽입하여 어드바이스가 적용될 수 있는 위치를 뜻함
	 * Advice(어드바이스) : Aspect(관점)의 구현체로 조인 포인트에 삽입되어 동작하는 코드
	 * 					 Advice(어드바이스)는 조인 포인트와 결합하여 동작하는 시점에 5개로 구분
	 * 
	 *		1. Before Advice : 조인포인트 전에 실행되는 Advice
	 *		2. After Returning Advice : 조인 포인트에서 성공적으로 리턴된 다음 실행되는 Advice
	 *		3. After Throwning Advice : 예외가 발생하였을 경우 실행되는 Advice
	 *		4. After Advice : 조인 포인트에서 메서드의 실행결과에 상관없이 무조건 실행되는 Advice, 자바의 Finally와 비슷한 역할
	 *		5. Around Advice : 조인 포인트의 전 과정(전, 후)에 수행되는 Advice
	 * 
	 * PointCut(포인트컷) : 어드바이스를 적용할 조인 포인트를 선별하는 과정이나 그 기능을 정의한 모듈로 패턴매칭을 이용하여 어떤 조인포인트를 사용할지 결정
	 * Target(타겟) : 어드바이스를 받을 대상, 즉 객체를 의미하며 비즈니스 로직을 수행하는 클래수일수도 있지만, 프록시 객체(Object)가 될 수도 있음
	 */

	/*
	 * Around Advice
	 * execution()을 포함하여 여러가지 포인트컷 지시자(PointCut Designator)를 사용할 수 있음
	 * 
	 * 		1. execution() : 가장 대표적이고 강력한 지시자로, 접근제한자, 리턴 타입, 타입 패턴, 메서드, 파라미터 타입,
	 * 						  예외 타입 등을 조합해서 메서드까지 선택한 가장 정교한 포인트컷을 만들 수 있음
	 * 
	 * 		2. within() : 타입 패턴만을 이용하여 조인 포인트를 정의
	 * 		3. this : 빈 오브젝트 타입의 조인 포인트를 정의
	 * 		4. target : 대상 객체의 타입 비교를 이용한 조인 포인트를 정의
	 * 		5. args : 메서드의 파라미터 타입만을 이용하여 조인 포인트를 정의
	 * 		6. @target : 특정 애너테이션이 정의돈 객체를 찾는 조인 포인트를 정의
	 * 		7. @args : 특정 애너테이션을 파라미터로 받는 오브젝트를 찾는 조인 포인트를 정의
	 * 		8. @within : @target과 유사하게 특정 애너테이션이 정의된 객체를 찾는데, 선택될 조인 포인트 메서드는 타겟 클래스에서 선언이 되어있어야 함
	 * 		9. @annotation : 조인 포인트 메서드에 특정 애너테이션을 찾는 조인 포인트를 정의
	 * 
	 * 문법 설명 : * com.dy..controller.*Controller.*(..)
	 * 			com.dy.. : com.dy 패키지 밑의 모든 서브 패키지를 의미
	 * 			controller. : controller 패키지 밑의 클래스와 인터페이스만을 의미
	 * 			*Controller. : COntroller라는 이름으로 끝나는 것을 의미
	 * 			*(..)은 모든 메서드를 의미
	 * 
	 *		 	or : or, and, not 또는 ||, &&, !으로도 표현할 수 있음
	 */
	@Around("execution(* com.dy..controller.*Controller.*(..)) or execution(* com.dy..service.*Impl.*(..)) or execution(* com.dy..*Mapper.*(..))")
	public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {

		String type = "";
		String name = joinPoint.getSignature().getDeclaringTypeName();

		if (name.indexOf("Controller") > -1) {
			type = "Controller \t: ";

		} else if (name.indexOf("Service") > -1) {
			type = "ServiceImpl \t: ";

		} else if (name.indexOf("Mapper") > -1) {
			type = "Mapper \t: ";
		}

		logger.info(type + name + "." + joinPoint.getSignature().getName() + "()");
		return joinPoint.proceed();
	}

}
