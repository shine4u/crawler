package com.wbximy.crawler.stocksina.processor;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.wbximy.crawler.Constants;
import com.wbximy.crawler.SiteSetting;
import com.wbximy.crawler.tools.TypeConverter;

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
		
		// the jsonpath use by webmagic is out of date(see pom). we use fastjson.
		String jsonStr = page.getJson().toString();
		
		// https://github.com/alibaba/fastjson/wiki/JSONPath
		Object jsonObj = JSON.parse(jsonStr);
		int code = (Integer)JSONPath.eval(jsonObj, "$[0].code");
		if (code != 0) {
			logger.warn("json code is not zeor, bad json");
			page.setSkip(true);
			return ;
		}
		
		int itemNum = (Integer)JSONPath.eval(jsonObj, "$[0].items.size()");
		logger.debug("itemNum: " + itemNum);
	
		for (int itemIdx = 0; itemIdx < itemNum; ++ itemIdx) {
			@SuppressWarnings("unchecked")
			List<Object> stockEntity = (List<Object>) JSONPath.eval(jsonObj, "$[0].items["+itemIdx +"]");
			String stockCode = (String) stockEntity.get(1);
			String stockName = (String) stockEntity.get(2);
			String curPrice = (String) stockEntity.get(3);
			String pricechange = (String) stockEntity.get(4);
			String changepercent = (String) stockEntity.get(5);
			String buy = (String) stockEntity.get(6); // 当前买入价 更新达不到要求 此字段不可信
			String sell = (String) stockEntity.get(7); // 当前卖出价 更新达不到要求 此字段不可信
			String prevEndPrice = (String) stockEntity.get(8); // 昨收
			String curBegPrice = (String) stockEntity.get(9); // 今开
			String curHighestPrice = (String) stockEntity.get(10); // 今高
			String curLowestPrice = (String) stockEntity.get(11);
			String tradeHands = (String) stockEntity.get(12); // 成交量 手
			String tradeAmount = (String) stockEntity.get(13); // 成交额  元
			String ticktime = (String) stockEntity.get(14); // 最近更新时间 "11:30:00"
			double mktcap = TypeConverter.toDouble(stockEntity.get(19)); // 总市值 亿
			double nmc = TypeConverter.toDouble(stockEntity.get(20)); // 流通市值 亿
			double turnoverratio = TypeConverter.toDouble(stockEntity.get(21)); // 换手率 0.02155
			
			logger.debug(stockCode + " " + stockName + " " + turnoverratio + " " + nmc);
		}
		
	}

}
