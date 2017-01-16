

-- 股票按天的分价格图 http://market.finance.sina.com.cn/pricehis.php?symbol=sz000858&startdate=2017-01-05&enddate=2017-01-05

CREATE TABLE Stock(
	stockCode VARCHAR(10) not null, -- 股票代码 600000
	stockName VARCHAR(100), -- 股票名称 浦发银行
	buy DECIMAL(10, 3), -- 买一
	sell DECIMAL(10, 3), -- 卖一
	prevEndPrice DECIMAL(10, 3),
	curLowestPrice DECIMAL(10, 3),
	curHighestPrice DECIMAL(10, 3),
	curPrice DECIMAL(10, 3),
	curBegPrice DECIMAL(10, 3),
	changepercent DECIMAL(10, 3), -- 涨幅 0.931
	turnoverratio REAL, -- 换手率 0.09276
	nmc REAL, -- 
	mktcap REAL, --
	ticktime DATETIME, -- 最近更新数据时间 timestamp
	tradeAmount BIGINT, -- 成交额
	pb REAL,
	nta DECIMAL(10, 4),
	per_d REAL,
	per REAL,
	tradeHands BIGINT,
	
	PRIMARY KEY(stockCode)
);