package com.wbximy.crawler.stocksina.domain;

import java.sql.Date;

import lombok.Data;

@Data public class Stock {
	private String stockCode; // 600000 PRIMARY KEY
	
	// 接下来的来自 StockCodeList
	Date date; // 当前日期，对应于 今开
	private String stockName; // 浦发银行
	String curPrice; // 当前价格 16.170
	String changepercent; // 价格变化百分比 0.02
	String buy; // 当前买入价 更新达不到要求 此字段不可信
	String sell; // 当前卖出价 更新达不到要求 此字段不可信
	String prevEndPrice; // 昨收
	String curBegPrice; // 今开
	String curHighestPrice; // 今高
	String curLowestPrice;
	String tradeHands; // 成交量 手
	String tradeAmount; // 成交额  元
	String ticktime; // 最近更新时间 "11:30:00"
	double per; // per TODO
	double per_d; // per TODO
	String nta; // 每股净资产 元
	double pb; // 市盈率
	double mktcap; // 总市值 亿
	double nmc; // 流通市值 亿
	double turnoverratio; // 换手率 0.02155



	
	
	
	
}
