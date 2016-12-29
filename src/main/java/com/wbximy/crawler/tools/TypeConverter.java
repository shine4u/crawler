package com.wbximy.crawler.tools;

import java.util.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TypeConverter {

	public static double toDouble(Object o) {
		if (o instanceof Double) {
			return (Double)o;
		} else {
			return Double.parseDouble(String.valueOf(o));
		}
	}
	
	
	// http://stackoverflow.com/questions/18915075/java-convert-string-to-timestamp
	public static Timestamp toTimestamp(String s, String format) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date parsedDate = dateFormat.parse(s);
		Timestamp timestamp = new Timestamp(parsedDate.getTime());
		return timestamp;
	}
}
