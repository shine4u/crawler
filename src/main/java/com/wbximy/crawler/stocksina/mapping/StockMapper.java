package com.wbximy.crawler.stocksina.mapping;

import org.apache.ibatis.annotations.Param;

import com.wbximy.crawler.stocksina.domain.Stock;

public interface StockMapper {
	void insert(Stock stock);
	Stock selectOne(@Param("stockCode")String stockCode);
}
