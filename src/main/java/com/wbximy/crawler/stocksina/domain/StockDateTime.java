package com.wbximy.crawler.stocksina.domain;

import java.sql.Date;
import lombok.Data;


// stock data time 可以用来分析某一天的交易情况
// 不用来进行长期存储，存储量太大

@Data
public class StockDateTime {
	// TODO
	private String stockCode; // 600000 PRIMARY KEY
	private Date date; // 日期 PRIMARY KEY
	private int secOffset; // 时间偏移量，9点半偏移量就是0， 10点偏移量就是30*60
	
	private double price;
	private int hands; // 成交量 手
	private int type; // -1 卖盘 0 中性盘  1 买盘
}
