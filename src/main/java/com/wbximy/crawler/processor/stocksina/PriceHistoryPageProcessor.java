package com.wbximy.crawler.processor.stocksina;


import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.wbximy.crawler.Constants;
import com.wbximy.crawler.SiteSetting;
import com.wbximy.crawler.main.UrlPatPageProcessor;
import com.wbximy.crawler.tools.HtmlHelper;
import com.wbximy.crawler.tools.RegexHelper;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

public class PriceHistoryPageProcessor implements UrlPatPageProcessor {
	
	Logger logger = Logger.getLogger(PriceHistoryPageProcessor.class);

	private final Pattern urlpattern = Pattern.compile(Constants.PRICE_HISTORY_PATTERN);

	@Override
	public Pattern getPattern() {
		// TODO Auto-generated method stub
		return urlpattern;
	}
	
	
	@Override
	public Site getSite() {
		// TODO Auto-generated method stub
		return SiteSetting.getSite(Constants.STOCK_SINA_SITE);
	}

	// http://market.finance.sina.com.cn/pricehis.php?symbol=sz000858&startdate=2017-01-05&enddate=2017-01-05
	@Override
	public void process(Page page) {
		// TODO Auto-generated method stub
		String url = page.getUrl().toString();
		
		Matcher matcher = getPattern().matcher(url);
		if (!matcher.find()) {
			logger.warn("can't parse data from url=" + url + " pat=" + getPattern().pattern());
			return;
		}
		String stockCode = matcher.group(1);
		String startDate = matcher.group(2);
		String endDate = matcher.group(3);
		
		Selectable dataListSelectable = page.getHtml().xpath("//table[@id='datalist']/tbody");
		// logger.info("dataListSelectable: " + dataListSelectable);
		
		List<String> tradePrices = dataListSelectable.xpath("//tr/td[1]/text()").all(); // 成交价(元)
		List<String> tradeAmount = dataListSelectable.xpath("//tr/td[2]/text()").all(); // 成交量(股)
		List<String> tradePercent = dataListSelectable.xpath("//tr/td[3]/text()").all();
		logger.info("tradePrices: " + tradePrices);
		logger.info("tradeAmount: " + tradeAmount);
		logger.info("tradePercent: " + tradePercent);
	}

}