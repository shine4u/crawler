package com.wbximy.crawler.stocksina.processor;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.wbximy.crawler.Constants;
import com.wbximy.crawler.SiteSetting;
import com.wbximy.crawler.tools.HtmlHelper;
import com.wbximy.crawler.tools.RegexHelper;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

public class TradeHistoryPageProcessor implements PageProcessor {
	
	Logger logger = Logger.getLogger(TradeHistoryPageProcessor.class);
	
	@Override
	public Site getSite() {
		// TODO Auto-generated method stub
		return SiteSetting.getSite(Constants.STOCK_SINA_SITE);
	}

	// http://vip.stock.finance.sina.com.cn/quotes_service/view/vMS_tradehistory.php?symbol=sz000858&date=2016-03-28
	@Override
	public void process(Page page) {
		// TODO Auto-generated method stub
		String url = page.getUrl().toString();
		String html = page.getHtml().toString();

		String pat = "http://vip\\.stock\\.finance\\.sina\\.com\\.cn/quotes_service/view/vMS_tradehistory\\.php\\?symbol=(\\w+)&date=(\\d\\d\\d\\d)-(\\d\\d)-(\\d\\d)";
		// 赛程页，月份，时间排序
		Matcher matcher = Pattern.compile(pat).matcher(url);
		if (!matcher.find()) {
			logger.warn("can't parse data from url=" + url + " pat=" + pat);
			return;
		}
		
		String stockCode = matcher.group(1);
		String year = matcher.group(2);
		String month = matcher.group(3);
		String day = matcher.group(4);
		
		Selectable quoteAreaSelectable = page.getHtml().xpath("/html/body/div[6]/div/div[2]/div[3]/div/div[1]/div[2]/table/tbody");
		
		String quoteAreaHtml = String.join("", quoteAreaSelectable.xpath("//tr/td").all());	
		String quoteAreaText = String.join("", HtmlHelper.getText(quoteAreaHtml));
		
		String curEndPrice = RegexHelper.SimpleKVPatternMatch(quoteAreaText, "收盘价:(\\d+\\.\\d+)", ""); // 收盘价:28.08
		String prevEndPrice = RegexHelper.SimpleKVPatternMatch(quoteAreaText, "前收价:(\\d+\\.\\d+)", ""); // 前收价:27.25
		String changeRate = RegexHelper.SimpleKVPatternMatch(quoteAreaText, "涨跌幅:(\\d+\\.\\d+%)", ""); // 涨跌幅:3.05%
		String curBegPrice = RegexHelper.SimpleKVPatternMatch(quoteAreaText, "开盘价:(\\d+\\.\\d+)", ""); // 开盘价:27.38
		String curHighestPrice = RegexHelper.SimpleKVPatternMatch(quoteAreaText, "最高价:(\\d+\\.\\d+)", ""); // 最高价:28.68
		String curLowestPrice = RegexHelper.SimpleKVPatternMatch(quoteAreaText, "最低价:(\\d+\\.\\d+)", ""); // 最低价:27.38
		String curTradeHands = RegexHelper.SimpleKVPatternMatch(quoteAreaText, "成交量\\(手\\):(\\d+\\.\\d+)", ""); // 成交量(手):555269.13
		String curTradeAmount = RegexHelper.SimpleKVPatternMatch(quoteAreaText, "成交额\\(千元\\):(\\d+\\.\\d+)", ""); // 成交额(千元):1564138.69
		
		logger.info("curEndPrice: " + curEndPrice);
		logger.info("prevEndPrice: " + prevEndPrice);
		logger.info("changeRate: " + changeRate);
		logger.info("curBegPrice: " + curBegPrice);
		logger.info("curHighestPrice: " + curHighestPrice);
		logger.info("curLowestPrice: " + curLowestPrice);
		logger.info("curTradeHands: " + curTradeHands);
		logger.info("curTradeAmount: " + curTradeAmount);
	}

}
