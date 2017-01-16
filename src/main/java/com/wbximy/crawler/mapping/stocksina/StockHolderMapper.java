package com.wbximy.crawler.mapping.stocksina;

import org.apache.ibatis.annotations.Param;

import com.wbximy.crawler.domain.stocksina.StockHolder;


public interface StockHolderMapper {
	void insert(StockHolder stockHolder);
	StockHolder selectOne(@Param("stockCode")String stockCode, @Param("holderName")String holderName);
}
