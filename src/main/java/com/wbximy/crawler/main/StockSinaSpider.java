package com.wbximy.crawler.main;

import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wbximy.crawler.stocksina.processor.PriceHistoryPageProcessor;
import com.wbximy.crawler.stocksina.processor.TradeHistoryPageProcessor;

import us.codecraft.webmagic.Spider;

public class StockSinaSpider {

	Logger logger = Logger.getLogger(StockSinaSpider.class);
	
	public static void main(String[] args) {
		/*
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext context = 
                new ClassPathXmlApplicationContext(new String[]{"classpath:META-INF/spring/app.xml"});
        context.start();
        */
		MultiPageProcessor processor = new MultiPageProcessor()
				.addProcessor(Pattern.compile("http://vip\\.stock\\.finance\\.sina\\.com\\.cn/quotes_service/view/vMS_tradehistory\\.php\\?symbol=(\\w+)&date=([\\d\\-]+)"), new TradeHistoryPageProcessor())
				.addProcessor(Pattern.compile("http://market\\.finance\\.sina\\.com\\.cn/pricehis\\.php\\?symbol=(\\w+)&startdate=([\\d\\-]+)&enddate=([\\d\\-]+)"), new PriceHistoryPageProcessor())
				;
		Spider.create(processor)
		.addUrl("http://vip.stock.finance.sina.com.cn/quotes_service/view/vMS_tradehistory.php?symbol=sz000858&date=2016-03-28")
		.addUrl("http://market.finance.sina.com.cn/pricehis.php?symbol=sz000858&startdate=2017-01-05&enddate=2017-01-05")
		// 开启1个线程抓取
		.thread(1)
		// 启动爬虫
		.run();
    }
}
