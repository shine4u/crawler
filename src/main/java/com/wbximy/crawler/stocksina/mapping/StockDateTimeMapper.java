package com.wbximy.crawler.stocksina.mapping;

import java.sql.Timestamp;
import java.util.List;

import com.wbximy.crawler.stocksina.domain.StockDateTime;


public interface StockDateTimeMapper {
	
	void insert(StockDateTime stockDateTime);
	
	// 查询一天的所有数据
	List<StockDateTime> selectDate(String stockCode, Timestamp date);
}

