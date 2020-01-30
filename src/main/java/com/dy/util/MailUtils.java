package com.dy.util;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.dy.common.Const.MailType;

// TODO => 개발 마무리 하고 이메일 인증 기능도 추가하기
/*
 * value 속성을 사용하여 bean처럼 이름을 지정할 수 있음 (미지정 시, 자동으로 camel case 처리)
 * @component는 개발자가 직접 작성한 class를 Bean으로 만드는 것, @Bean은 개발자가 직접 제어가 불가능한 외부 라이브러리 등을 Bean으로 만들 때 사용 
 */
@Component(value = "mailUtils")
public class MailUtils {

	@Autowired
	private JavaMailSender mailSender;

	public boolean sendMailByUsername(String username, String subject, String text, MailType mailType) {

		if (mailType == MailType.TEXT) {
			/* Text Message */
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(username);
			message.setSubject(subject);
			message.setText(text);
			mailSender.send(message);

		} else {
			/* HMTL or Multipart Message */
			try {
				MimeMessage message = mailSender.createMimeMessage();
				/* 두 번째 인자 => multipart message */
				MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
				helper.setTo(username);
				helper.setSubject(subject);
				/* 두 번째 인자 => text/html 타입 (default => text/plain) */
				helper.setText(text, true);
				mailSender.send(message);

			} catch (MessagingException e) {
				e.printStackTrace();
				return false;
			}
		}

		return true;
	}

}
