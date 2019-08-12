package com.dy.util;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.dy.common.Const.MailType;

// TODO => 개발 마무리 하고 이메일 인증 기능도 추가하기
public class MailUtils {

	@Autowired
	private JavaMailSender mailSender;

	public boolean sendAuthNumberByEmail(String username, String subject, String text, MailType mailType) {

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
