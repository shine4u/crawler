
-- 股票历史指标 http://vip.stock.finance.sina.com.cn/quotes_service/view/vMS_tradehistory.php?symbol=sz000858&date=2016-03-28
		
CREATE TABLE TradeHistory(
	stockCode VARCHAR(10) not null, -- 股票代码 600000
	stockDate DATETIME, -- 日期
	prevEndPrice DECIMAL(10, 3),
	curLowestPrice DECIMAL(10, 3),
	curHighestPrice DECIMAL(10, 3),
	curEndPrice DECIMAL(10, 3),
	curBegPrice DECIMAL(10, 3),
	changepercent DECIMAL(10, 3), -- 涨幅 0.931
	tradeAmount BIGINT, -- 成交额
	tradeHands BIGINT,
	
	PRIMARY KEY(stockCode,stockDate)
);