package com.wbximy.crawler.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wbximy.crawler.Constants;
import com.wbximy.crawler.dao.StocksinaDAO;
import com.wbximy.crawler.exception.ListSizeException;
import com.wbximy.crawler.stocksina.processor.PriceHistoryPageProcessor;
import com.wbximy.crawler.stocksina.processor.StockCodeListPageProcessor;
import com.wbximy.crawler.stocksina.processor.TradeHistoryPageProcessor;
import com.wbximy.crawler.tools.RegexHelper;

import us.codecraft.webmagic.Spider;

public class StocksinaStarter {

	static Logger logger = Logger.getLogger(StocksinaStarter.class);
	
	public static void main(String[] args) throws ListSizeException {
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext context = 
                new ClassPathXmlApplicationContext(new String[]{"classpath:META-INF/spring/app.xml"});
        context.start();
        
        StocksinaDAO stocksinaDAO = (StocksinaDAO) context.getBean("stocksinaDAO");
        
        StockCodeListPageProcessor stockCodeListPageProcessor = new StockCodeListPageProcessor();
        stockCodeListPageProcessor.setStocksinaDAO(stocksinaDAO);
        
		MultiPageProcessor processor = new MultiPageProcessor()
				.addUrlPatPageProcessor(new TradeHistoryPageProcessor())
				.addUrlPatPageProcessor(new PriceHistoryPageProcessor())
				.addUrlPatPageProcessor(stockCodeListPageProcessor)
				;
		Spider.create(processor)
		.addUrl(RegexHelper.PatternToString(Constants.STOCK_CODE_LIST_PATTERN, new ArrayList<String>(Arrays.asList("1", "20"))))
		// 开启1个线程抓取
		.thread(1)
		// 启动爬虫
		.run();
    }
}
