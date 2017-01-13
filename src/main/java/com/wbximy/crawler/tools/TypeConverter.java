package com.wbximy.crawler.tools;

import java.util.Date;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TypeConverter {

	public static double toDouble(Object o) {
		if (o instanceof BigDecimal) {
			return ((BigDecimal)o).doubleValue();

		} else if (o instanceof Double) {
			return (Double)o;
		} else {
			return Double.parseDouble(String.valueOf(o));
		}
	}
	
	
	// http://stackoverflow.com/questions/18915075/java-convert-string-to-timestamp
	public static Timestamp toTimestamp(String s, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date parsedDate = null;
		try {
			parsedDate = dateFormat.parse(s);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Timestamp timestamp = new Timestamp(parsedDate.getTime());
		return timestamp;
	}
	
	public static void cvt(Object o, Field field, Object value) {
		try {
			if (field.getType().isAssignableFrom(value.getClass())) { // string
				GetterSetter.invokeSetter(o, field.getName(), value);
			} else if (String.class.isAssignableFrom(field.getType())) { // string
				GetterSetter.invokeSetter(o, field.getName(), value.toString());
			} else if (field.getType() == int.class) { // int
				if (value instanceof Integer || value instanceof Long) {
					GetterSetter.invokeSetter(o, field.getName(), (int)value);
				} else if (value.getClass() == int.class || value.getClass() == long.class) {
					GetterSetter.invokeSetter(o, field.getName(), (int)value);
				} else if (value instanceof String) {
					GetterSetter.invokeSetter(o, field.getName(), Integer.parseInt((String)value));
				}
			} else if (field.getType() == long.class) { // long
				if (value instanceof Integer || value instanceof Long) {
					GetterSetter.invokeSetter(o, field.getName(), (long)value);
				} else if (value.getClass() == int.class || value.getClass() == long.class) {
					GetterSetter.invokeSetter(o, field.getName(), (long)value);
				} else if (value instanceof String) {
					GetterSetter.invokeSetter(o, field.getName(), Long.parseLong((String)value));
				}
			} else if (field.getType() == double.class) { // double
				if (value instanceof Integer || value.getClass() == int.class) {
					GetterSetter.invokeSetter(o, field.getName(), (int)value * 1.0);
				} else if (value instanceof Long || value.getClass() == long.class) {
					GetterSetter.invokeSetter(o, field.getName(), (long)value * 1.0);
				} else if (value instanceof String) {
					GetterSetter.invokeSetter(o, field.getName(), Double.parseDouble((String)value));
				} else if (value instanceof Double || value.getClass() == double.class) {
					GetterSetter.invokeSetter(o, field.getName(), (double)value);
				} else if (value instanceof BigDecimal) {
					GetterSetter.invokeSetter(o, field.getName(), ((BigDecimal)value).doubleValue());
				} 
			} else  if (Timestamp.class.isAssignableFrom(field.getType())) { // Timestamp
				if (value instanceof String) {
						GetterSetter.invokeSetter(o, field.getName(), toTimestamp((String)value, "yyyy-MM-dd HH:mm:ss"));
				} 
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

}
