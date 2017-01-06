package com.wbximy.crawler.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexHelper {
	public static String SimpleKVPatternMatch(String s, String pat, String defaultStr) {
		Matcher matcher = Pattern.compile(pat).matcher(s);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return defaultStr;
	}
}
