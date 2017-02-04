package com.wbximy.crawler.stocksina.mapping;

import java.sql.Timestamp;

import org.apache.ibatis.annotations.Param;

import com.wbximy.crawler.stocksina.domain.StockDate;

public interface StockDateMapper {
	void insert(StockDate Stockdate);
	StockDate selectOne(@Param("stockCode")String stockCode, Timestamp date);
}
