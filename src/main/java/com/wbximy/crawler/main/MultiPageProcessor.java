package com.wbximy.crawler.main;

import java.util.HashMap;
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

	
	private Map<Pattern, PageProcessor> processorMap = new HashMap<Pattern, PageProcessor>();

	@Override
	public void process(Page page) {
		process(page, false);
	}
	public void process(Page page, boolean multiProcess) {
		// TODO Auto-generated method stub
		int processCount = 0;
		String url = page.getUrl().toString();
		
		for (Pattern pat : processorMap.keySet()) {
			if (pat.matcher(url).find()) {
				//logger.debug(url + " " + pat.pattern());
				PageProcessor processor = processorMap.get(pat);
				logger.info("process page url=" + url + " processor=" + processor.getClass());
				processor.process(page);
				processCount += 1;
				if (!multiProcess) {
					return ;
				}
			}
		}
		if (processCount == 0) {
			logger.warn("no processor, pattern don't match. url=" + url);
			return ;
		}
	}

	@Override
	public Site getSite() {
		// TODO Auto-generated method stub
		for (Pattern pat : processorMap.keySet()) {
			return processorMap.get(pat).getSite();
		}
		return Site.me();
	}
	
	public MultiPageProcessor addProcessor(Pattern pat, PageProcessor processor) {
		processorMap.put(pat, processor);
		return this;
	}
}
