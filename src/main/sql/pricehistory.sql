

-- 股票按天的分价格图 http://market.finance.sina.com.cn/pricehis.php?symbol=sz000858&startdate=2017-01-05&enddate=2017-01-05

logger.info("tradePrices: " + tradePrices);
		logger.info("tradeAmount: " + tradeAmount);
		logger.info("tradePercent: " + tradePercent);
		
CREATE TABLE Stock(
	stockCode VARCHAR(10) not null, -- 股票代码 600000
	stockDate DATETIME, -- 日期
	tradePrice DECIMAL(10, 3),
	tradePercent REAL,
	tradeHands BIGINT,
	
	PRIMARY KEY(stockCode,stockDate,tradePrice)
);