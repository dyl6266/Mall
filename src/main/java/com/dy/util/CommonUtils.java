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
	 * 숫자를 포함한 랜덤 문자열 10글자를 반환
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length) {
		return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
	}

//	/**
//	 * 문자, 숫자를 포함한 랜덤 문자열 반환
//	 * 
//	 * @param length 문자열 길이
//	 * @return 랜덤 문자열
//	 */
//	public static String getRandomString(int length) {
//		
//		char[] chars = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
//		StringBuffer sb = new StringBuffer();
//		
//		for (int i = 0; i < length; i++) {
//			/* 36 * 난수를 int 타입으로 캐스팅 (소수점 제거) */
//			sb.append(chars[ (int) (chars.length * Math.random()) ]);
//		}
//		
//		return sb.toString();
//	}

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
