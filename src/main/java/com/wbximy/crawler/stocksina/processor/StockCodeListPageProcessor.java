package com.wbximy.crawler.stocksina.processor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.wbximy.crawler.Constants;
import com.wbximy.crawler.SiteSetting;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Json;

public class StockCodeListPageProcessor implements PageProcessor {

	Logger logger = Logger.getLogger(StockCodeListPageProcessor.class);

	@Override
	public Site getSite() {
		// TODO Auto-generated method stub
		return SiteSetting.getSite(Constants.STOCK_SINA_SITE);
	}

	
	// http://money.finance.sina.com.cn/d/api/openapi_proxy.php/?__s=[["hq","hs_a","",0,1,20]]
	// 倒数第二个参数 1 表示 第一页，从1开始。
	// 最后一个参数表示每次请求返回的stock的数目。
	// 如果页数超过最大页，那么返回的股票数目为0
	@Override
	public void process(Page page) {
		// TODO Auto-generated method stub
		String url = page.getUrl().toString();
		String html = page.getHtml().toString();

		Matcher matcher = Pattern.compile(Constants.STOCK_CODE_LIST_PATTERN).matcher(url);
		if (!matcher.find()) {
			logger.warn("can't parse data from url=" + url + " pat=" + Constants.STOCK_CODE_LIST_PATTERN);
			return;
		}
		
		Json json = page.getJson();
		logger.debug("json=" + json);
		
		logger.debug("test: " + json.jsonPath("$[0].items.length()"));
		
		int code = Integer.parseInt(json.jsonPath("[0].code").toString());
		if (code != 0) {
			logger.warn("code=" + code + "bad code. json=" + json.toString());
			page.setSkip(true);
			return ;
		}
		int stockNum = Integer.parseInt(json.jsonPath("[0].items").toString());
		logger.debug("stockNum=" + stockNum);
		
		
	}

}
