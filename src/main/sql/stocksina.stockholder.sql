
-- http://vip.stock.finance.sina.com.cn/corp/go.php/vCI_CirculateStockHolder/stockid/600000/displaytype/30.phtml
-- CIRCULATE_STOCK_HOLDER_PATTERN
	
CREATE TABLE StockHolder(
	stockCode VARCHAR(10) not null, -- 股票代码 600000
	infoDate DATETIME, -- 出信息的日期
	holderPos INTEGER, --  股东位置
	holderName VARCHAR(1024) not null, -- 股东名称 中国移动通信集团广东有限公司
	stockNum BIGINT, -- 持股数量(股) 4103760000
	stockPercent REAL, -- 持股比例(%) 12.34
	stockType VARCHAR(64), -- 股本性质 国有股
	PRIMARY KEY(stockCode,holderName)
);