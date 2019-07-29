package com.dy.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

public class CommonUtils {

	/**
	 * 숫자를 포함한 랜덤 문자열 32글자를 반환
	 * 
	 * @return 랜덤 문자열
	 */
	public static String getRandomString() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 현재 시간을 문자열 형태로 반환
	 * 
	 * @return 현재 시간
	 */
	public static String getCurrentTime() {

		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return (String) sdf.format(calendar.getTime());
	}

}
