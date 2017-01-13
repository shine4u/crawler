package com.wbximy.crawler.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wbximy.crawler.Constants;
import com.wbximy.crawler.exception.ListSizeException;

public class RegexHelper {
	public static String SimpleKVPatternMatch(String s, String pat, String defaultStr) {
		Matcher matcher = Pattern.compile(pat).matcher(s);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return defaultStr;
	}
	
	// from pattern to string. 
	// this method may change in future.
	// for example: (\d)abc(\w) ["23", "ww"] -> 23abcww
	// notice nest () cannnot work.
	// no check matching for string in ().
	public static String PatternToString(String patternStr, List<String>matchGroups) {
		
		System.out.println("patternStr: " + patternStr);
		
		Pattern pat = Pattern.compile("\\([^)]*\\)");
		Matcher matcher = pat.matcher(patternStr);
		
		int matchCount = 0;
		StringBuilder sb = new StringBuilder();
		int lastMatchEnd = 0;
		while (matcher.find()) {
			int thisMatchStart = matcher.start();
			int thisMatchEnd = matcher.end();
			// System.out.println("" + thisMatchStart + " " + thisMatchEnd);
			sb.append(patternStr.subSequence(lastMatchEnd, thisMatchStart));
			
			if (matchCount >= matchGroups.size()) break;
			sb.append(matchGroups.get(matchCount));
			
			lastMatchEnd = thisMatchEnd;
			matchCount += 1;
		}
		sb.append(patternStr.subSequence(lastMatchEnd, patternStr.length()));
		
		if (matchCount > matchGroups.size()) {
			String message = "matchGroups=" + matchGroups + " patternStr=" + patternStr + " size not match.";
			try {
				throw new ListSizeException(message);
			} catch (ListSizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// regex addtional \ remove.
		return sb.toString().replace("\\", "");
	}
	public static void main(String[] args) throws ListSizeException {
		System.out.println(PatternToString("12(3)4(567)8", new ArrayList<String>(Arrays.asList("Tom", "Jerry", "a", "b"))));
		System.out.println(PatternToString(Constants.STOCK_CODE_LIST_PATTERN, new ArrayList<String>(Arrays.asList("Tom", "Jerry", "a", "b"))));
	}
}

