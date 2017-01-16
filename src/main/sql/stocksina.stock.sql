-- sqlite3 常用语句 http://www.cnblogs.com/zibuyu/p/3564408.html
-- sqlite 常用命令  http://blog.csdn.net/shellching/article/details/9701229

sqlite3 stocksina.db 

-- http://money.finance.sina.com.cn/d/api/openapi_proxy.php/?__s=[["hq","hs_a","",0,3,40]] 
-- STOCK_CODE_LIST_PATTERN
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

SELECT stockCode, curPrice FROM Stock;
-- ALTER TABLE Stock ADD  stockHolderInfo VARCHAR(10241024);  -- 1MB 另外表存储。
-- SELECT stockCode, curPrice, stockHolderInfo FROM Stock;


