package com.wbximy.crawler.main;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wbximy.crawler.mapping.MatchStatMapper;
import com.wbximy.crawler.tools.JSParser;
import com.wbximy.crawler.tools.TypeConverter;
import com.wbximy.crawler.win007.processor.MatchResultPageProcessor;
import com.wbximy.crawler.win007.processor.TechStatPageProcessor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class Win007Spider {

	Logger logger = Logger.getLogger(Win007Spider.class);
	
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext context = 
                new ClassPathXmlApplicationContext(new String[]{"classpath:META-INF/spring/app.xml"});
        context.start();
        
		MultiPageProcessor processor = new MultiPageProcessor()
				.addProcessor(Pattern.compile("http://nba\\.win007\\.com/jsData/matchResult/(\\d\\d\\-\\d\\d)/l1_1_(\\d+_\\d+)\\.js"), new MatchResultPageProcessor())
				.addProcessor(Pattern.compile("http://nba\\.win007\\.com/jsData/tech/\\d/\\d\\d/\\d+.js"), new TechStatPageProcessor())
				;
		Spider.create(processor)
		// 从"https://github.com/code4craft"开始抓
		.addUrl("http://nba.win007.com/jsData/matchResult/16-17/l1_1_2016_12.js")
		// 开启1个线程抓取
		.thread(1)
		// 启动爬虫
		.run();
    }
}
