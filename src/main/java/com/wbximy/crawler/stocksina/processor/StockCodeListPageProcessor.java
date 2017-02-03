package com.wbximy.crawler.stocksina.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import com.wbximy.crawler.Constants;
import com.wbximy.crawler.SiteSetting;
import com.wbximy.crawler.main.UrlPatPageProcessor;
import com.wbximy.crawler.stocksina.domain.Stock;
import com.wbximy.crawler.tools.GetterSetter;
import com.wbximy.crawler.tools.RegexHelper;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

public class StockCodeListPageProcessor implements UrlPatPageProcessor {

	Logger logger = Logger.getLogger(StockCodeListPageProcessor.class);

	
	private static final Map<String, String> fieldnameMappings = new HashMap<String, String>();
	static {
		fieldnameMappings.put("code", "stockCode");
		fieldnameMappings.put("name", "stockName");
		fieldnameMappings.put("trade", "curPrice");
		fieldnameMappings.put("changepercent", "changePercent");
		fieldnameMappings.put("settlement", "prevEndPrice");
		fieldnameMappings.put("open", "curBegPrice");
		fieldnameMappings.put("high", "curHighestPrice");
		fieldnameMappings.put("low", "curLowestPrice");
		fieldnameMappings.put("volume", "tradeHands");
		fieldnameMappings.put("amount", "tradeAmount");
	}
	
	private final Pattern urlpattern = Pattern.compile(Constants.STOCK_CODE_LIST_PATTERN);
	
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

	// http://money.finance.sina.com.cn/d/api/openapi_proxy.php/?__s=[["hq","hs_a","",0,1,20]]
	// 倒数第二个参数 1 表示 第一页，从1开始。
	// 最后一个参数表示每次请求返回的stock的数目。
	// 如果页数超过最大页，那么返回的股票数目为0
	@Override
	@SuppressWarnings("unchecked")
	public void process(Page page) {
		String url = page.getUrl().toString();

		Matcher matcher = getPattern().matcher(url);
		if (!matcher.find()) {
			logger.warn("can't parse data from url=" + url + " pat=" + getPattern().pattern());
			return;
		}
		
		int pageId = Integer.parseInt(matcher.group(1));
		int pageStockNum = Integer.parseInt(matcher.group(2));
		
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
		
		String day = (String)JSONPath.eval(jsonObj, "$[0].day"); // "2017-01-11"
		
		List<Object> rawFields = (List<Object>) JSONPath.eval(jsonObj, "$[0].fields");
		List<String> fields = rawFields.stream().map(x -> fieldnameMappings.getOrDefault((String)x, (String)x)).collect(Collectors.toList());
		int fieldNum = fields.size();
		
		int itemNum = (Integer)JSONPath.eval(jsonObj, "$[0].items.size()");
			
		List<Stock> stocks = new LinkedList<Stock>();
		
		for (int itemIdx = 0; itemIdx < itemNum; ++ itemIdx) {
			
			List<Object> stockEntity = (List<Object>) JSONPath.eval(jsonObj, "$[0].items["+itemIdx +"]");
			
			Map<String, Object> stockData = IntStream.range(0, fieldNum).boxed().collect(Collectors.toMap(i -> fields.get(i), i -> stockEntity.get(i)));
			
			// add ticktime
			String ticktime = day + " " + (String)stockData.getOrDefault("ticktime", "00:00:00"); // "2017-01-11" "14:04:18"
			stockData.put("ticktime", ticktime);
			
			stocks.add(GetterSetter.updateField(new Stock(), stockData));				
		}
		page.getResultItems().put("stocks", stocks);
		
		// 判断是否抓下一页
		if (stocks.size() < pageStockNum) {
			// 最后一页
		} else {
			String nextPageUrl = RegexHelper.PatternToString(getPattern().pattern(), new ArrayList<String>(
					Arrays.asList(Integer.valueOf(pageId+1).toString(), Integer.valueOf(pageStockNum).toString())));
			// 测试的时候
			page.addTargetRequest(nextPageUrl);
		}
	}

}
