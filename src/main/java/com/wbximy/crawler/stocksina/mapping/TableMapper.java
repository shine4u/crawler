package com.wbximy.crawler.stocksina.mapping;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TableMapper {

	/* 判断表是否存在 */
	int exist(@Param("tableName")String tableName);

	/* 创建表 */
	int create(@Param("sql")String sql);

}
