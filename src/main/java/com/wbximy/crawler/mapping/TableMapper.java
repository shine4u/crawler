package com.wbximy.crawler.mapping;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TableMapper {

	/* 判断表是否存在 */
	int exist(@Param("table_name")String tablename);

	/* 删除表 */
	int drop(@Param("table_name")String tablename);
	
	/* 创建表 */
	int create(String tablename, List<String> args);
}
