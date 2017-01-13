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

import com.wbximy.crawler.mapping.win007nba.MatchStatMapper;
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
		
    }
}
