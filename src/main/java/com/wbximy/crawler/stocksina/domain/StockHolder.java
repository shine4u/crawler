package com.wbximy.crawler.stocksina.domain;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class StockHolder {
	String stockCode; // -- 股票代码 600000
	Timestamp infoDate; // -- 出信息的日期
	int holderPos; // INTEGER, --  股东位置
	String holderName; // VARCHAR(1024) not null, -- 股东名称 中国移动通信集团广东有限公司
	long stockNum; // BIGINT, -- 持股数量(股) 4103760000
	double stockPercent; // REAL, -- 持股比例(%) 12.34
	String stockType; // VARCHAR(64), -- 股本性质 国有股
}
