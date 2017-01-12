package com.wbximy.crawler.mapping.stocksina;

import org.apache.ibatis.annotations.Param;

import com.wbximy.crawler.domain.stocksina.Stock;

public interface StockMapper {
	void insert(Stock stock);
	Stock selectOne(@Param("stockCode")String stockCode);
}
