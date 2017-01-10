package com.wbximy.crawler.win007;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.wbximy.crawler.mapping.TableMapper;

public class DAO {
	
	@Autowired TableMapper tableMapper;
	
	public void createIfNoExist(String tablename) {
		if (tableMapper.exist(tablename) == 0) {
			//tableMapper.create(getSchema(tablename));
		}
	}
	
	
	private static Map<String, Object> schemaMap = new HashMap<String, Object>();
	
	//private static void addSchema(String tablename, List<String>)
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getSchema(String tablename) {
		return (Map<String, Object>) schemaMap.get(tablename);
	}
}
