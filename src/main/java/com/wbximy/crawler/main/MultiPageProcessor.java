package com.wbximy.crawler.main;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/*
 * 所有的processor需要使用相同的Site配置
 */
public class MultiPageProcessor implements PageProcessor {
	
	Logger logger = Logger.getLogger(MultiPageProcessor.class);
	
	List<UrlPatPageProcessor> processors = new LinkedList<UrlPatPageProcessor>();
	
	@Override
	public void process(Page page) {
		process(page, false);
	}
	
	public void process(Page page, boolean multiProcess) {
		// TODO Auto-generated method stub
		int processCount = 0;
		String url = page.getUrl().toString();
		
		for (UrlPatPageProcessor processor : processors) {
			if (processor.getPattern().matcher(url).find()) {
				//logger.debug(url + " " + pat.pattern());
				logger.info("process page url=" + url + " processor=" + processor.getClass());
				processor.process(page);
				processCount += 1;
				if (!multiProcess) {
					return ;
				}
			}
		}
		if (processCount == 0) {
			logger.warn("no processor, url don't match any pattern. url=" + url);
			return ;
		}
	}

	@Override
	public Site getSite() {
		// TODO Auto-generated method stub
		for (UrlPatPageProcessor processor : processors) {
			return processor.getSite();
		}
		return Site.me();
	}
	
	public MultiPageProcessor addUrlPatPageProcessor(UrlPatPageProcessor urlPatPageProcessor) {
		processors.add(urlPatPageProcessor);
		return this;
	}
}
