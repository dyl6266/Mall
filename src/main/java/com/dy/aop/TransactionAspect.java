package com.dy.aop;

import java.util.Collections;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

@Configuration
public class TransactionAspect {

	/*
	 * 트랜잭션을 설정할 때 사용되는 설정값 상수
	 */
	private static final String AOP_TRANSACTION_METHOD_NAME = "*";
	private static final String AOP_TRANSACTION_EXPRESSION = "execution(* com.dy..service.*Impl.*(..))";

	@Autowired
	private PlatformTransactionManager transactionManager;

	@Bean
	public TransactionInterceptor transactionAdvice() {

		RuleBasedTransactionAttribute transactionAttribute = new RuleBasedTransactionAttribute();
		/* 트랜잭션 이름 설정 */
		transactionAttribute.setName(AOP_TRANSACTION_METHOD_NAME);
		/* 트랜잭션에서 롤백을 하는 룰(rule)을 설정. 자바의 모든 예외는 Exception 클래스를 상속받기 때문에 어떠한 예외가 발생해도 롤백이 수행됨 */
		transactionAttribute.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));

		MatchAlwaysTransactionAttributeSource source = new MatchAlwaysTransactionAttributeSource();
		source.setTransactionAttribute(transactionAttribute);

		return new TransactionInterceptor(transactionManager, source);
	}

	@Bean
	public Advisor transactionAdviceAdvisor() {

		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		/* AOP의 포인트컷 설정 */
		pointcut.setExpression(AOP_TRANSACTION_EXPRESSION);

		return new DefaultPointcutAdvisor(pointcut, transactionAdvice());
	}

}
