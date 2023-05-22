package com.hammerdev.authtest.utils;

import java.util.regex.Pattern;

public class Utils
{
	public static String removeMultiplesSpace(String text)
	{
		return text.replaceAll("\\s+", " ");
	}

	public static boolean match(String regex, String value)
	{
		return (Pattern.compile(regex)).matcher(value).find();
	}
}
