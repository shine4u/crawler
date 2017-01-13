
sqlite3 stocksina.db 

CREATE TABLE Stock(
	stockCode VARCHAR(10) not null,
	stockName VARCHAR(100),
	changepercent DECIMAL(10, 3),
	turnoverratio REAL,
	nmc REAL,
	mktcap REAL,
	buy DECIMAL(10, 3),
	sell DECIMAL(10, 3),
	ticktime DATETIME,
	tradeAmount BIGINT,
	pb REAL,
	prevEndPrice DECIMAL(10, 3),
	curLowestPrice DECIMAL(10, 3),
	curHighestPrice DECIMAL(10, 3),
	nta DECIMAL(10, 4),
	curPrice DECIMAL(10, 3),
	curBegPrice DECIMAL(10, 3),
	per_d REAL,
	per REAL,
	tradeHands BIGINT,
	
	PRIMARY KEY(stockCode)
);

SELECT stockCode, curPrice FROM Stock;

ALTER TABLE Stock ADD  stockHolderInfo VARCHAR(10241024);  -- 1MB

SELECT stockCode, curPrice, stockHolderInfo FROM Stock;

-- sqlite3 常用语句 http://www.cnblogs.com/zibuyu/p/3564408.html
