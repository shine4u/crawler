package com.wbximy.crawler.stocksina.domain;

import java.sql.Date;
import lombok.Data;

// 目前看来股票按照价格进行统计的数据用处不大，优先级低进行。
@Data
public class StockDatePrice {
	private String stockCode; // 600000 PRIMARY KEY
	private Date date; // 日期 PRIMARY KEY
	private double price; // PRIMARY KEY 百分位
	
	private int tradeAmount; // 成交量(股)
	private double tradePercent; // 当日成交占比 0.02
}
