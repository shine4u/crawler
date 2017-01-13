package com.wbximy.crawler.domain.stocksina;

import java.lang.reflect.Field;
import java.util.Map;

import com.wbximy.crawler.tools.TypeConverter;

public class AutoFieldUpdateDomain {
	public AutoFieldUpdateDomain updateField(Map<String, Object> dataMap) {
		// TODO Auto-generated method stub

		Field[] fields = this.getClass().getDeclaredFields();

		for (Field field : fields) {
			String fieldname = field.getName();
			if (!dataMap.containsKey(fieldname))
				continue;
			TypeConverter.cvt(this, field, dataMap.get(fieldname));
		}

		return this;
	}
}
