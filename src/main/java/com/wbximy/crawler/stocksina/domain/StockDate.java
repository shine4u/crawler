package com.wbximy.crawler.stocksina.domain;

import java.sql.Date;


import lombok.Data;

@Data public class StockDate {
	private String stockCode; // 600000 PRIMARY KEY
	private Date date; // 日期 PRIMARY KEY

	// TradeHistory
	private String curEndPrice; // 收盘价:28.08
	private String prevEndPrice; // 前收价:27.25
	private String changeRate; // 涨跌幅:3.05%
	private String curBegPrice; // 开盘价:27.38
	private String curHighestPrice; // 最高价:28.68
	private String curLowestPrice; // 最低价:27.38
	private String curTradeHands; // 成交量(手):555269.13
	private String curTradeAmount; // 成交额(千元):1564138.69
}
