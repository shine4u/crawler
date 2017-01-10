package com.wbximy.crawler;

public class Constants {
	public static final String NBA_WIN007_SITE = "nba.win007.com";
	
	public static final String STOCK_SINA_SITE ="vip.stock.finance.sina.com.cn";
	
	
	// pattern in stock.sina
	
	// http://vip.stock.finance.sina.com.cn/quotes_service/view/vMS_tradehistory.php?symbol=sz000858&date=2016-03-28
	public static final String TRADE_HISTORY_PATTERN = "http://vip\\.stock\\.finance\\.sina\\.com\\.cn/quotes_service/view/vMS_tradehistory\\.php\\?symbol=(\\w+)&date=([\\d\\-]+)";
	
	// http://market.finance.sina.com.cn/pricehis.php?symbol=sz000858&startdate=2017-01-05&enddate=2017-01-05
	public static final String PRICE_HISTORY_PATTERN = "http://market\\.finance\\.sina\\.com\\.cn/pricehis\\.php\\?symbol=(\\w+)&startdate=([\\d\\-]+)&enddate=([\\d\\-]+)";
	
	// " -> %22
	// http://money.finance.sina.com.cn/d/api/openapi_proxy.php/?__s=[["hq","hs_a","",0,3,40]]
	public static final String STOCK_CODE_LIST_PATTERN = "http://money\\.finance\\.sina\\.com\\.cn/d/api/openapi_proxy\\.php/\\?__s=\\[\\[%22hq%22,%22hs_a%22,%22%22,0,(\\d+),(\\d+)\\]\\]";
}

