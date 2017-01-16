package com.wbximy.crawler.stocksina.mapping;

import org.apache.ibatis.annotations.Param;

import com.wbximy.crawler.stocksina.domain.StockHolder;


public interface StockHolderMapper {
	void insert(StockHolder stockHolder);
	StockHolder selectOne(@Param("stockCode")String stockCode, @Param("holderName")String holderName);
}
