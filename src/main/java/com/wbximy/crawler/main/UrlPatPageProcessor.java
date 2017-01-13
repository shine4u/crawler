package com.wbximy.crawler.main;

import java.util.regex.Pattern;

import us.codecraft.webmagic.processor.PageProcessor;

public interface UrlPatPageProcessor extends PageProcessor {
	/* 返回可以被当前pageprocessor处理的pattern*/
	Pattern getPattern();
}
