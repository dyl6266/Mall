package com.dy;

import java.util.regex.Pattern;

public class Test {

	public static void main(String[] args) {

		String regex = "^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[~`!@#$%\\^&*()-]).{8,20}$";
		String input = "flehdud3328@$#^";

		boolean isValid = Pattern.matches(regex, input);
		System.out.println("first : " + isValid);

		input = "ehdud3328!2436";
		isValid = Pattern.matches(regex, input);
		System.out.println("second : " + isValid);
	}

}
