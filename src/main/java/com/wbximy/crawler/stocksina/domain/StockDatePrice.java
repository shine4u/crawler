package com.wbximy.crawler.stocksina.domain;

import java.sql.Date;
import java.util.List;

import lombok.Data;

@Data public class StockDatePrice {
	private String stockCode; // 600000 PRIMARY KEY
	private Date date; // 日期 PRIMARY KEY
	private double price; // PRIMARY KEY 百分位
	
	private String tradeAmount; // 成交量(股)
	private double tradePercent; // 当日成交占比 0.02
}
