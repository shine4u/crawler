package com.wbximy.crawler.stocksina.processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import com.wbximy.crawler.Constants;
import com.wbximy.crawler.SiteSetting;
import com.wbximy.crawler.dao.StocksinaDAO;
import com.wbximy.crawler.domain.stocksina.Stock;
import com.wbximy.crawler.main.UrlPatPageProcessor;

import lombok.Setter;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class StockCodeListPageProcessor implements UrlPatPageProcessor {

	Logger logger = Logger.getLogger(StockCodeListPageProcessor.class);

	@Setter
	private StocksinaDAO stocksinaDAO;
	
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
		// TODO Auto-generated method stub
		String url = page.getUrl().toString();
		String html = page.getHtml().toString();

		Matcher matcher = getPattern().matcher(url);
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
		
		String day = (String)JSONPath.eval(jsonObj, "$[0].day"); // "2017-01-11"
		
		List<Object> rawFields = (List<Object>) JSONPath.eval(jsonObj, "$[0].fields");
		List<String> fields = rawFields.stream().map(x -> fieldnameMappings.getOrDefault((String)x, (String)x)).collect(Collectors.toList());
		int fieldNum = fields.size();
		
		int itemNum = (Integer)JSONPath.eval(jsonObj, "$[0].items.size()");
	
		logger.info("dao=" + stocksinaDAO);
		
		for (int itemIdx = 0; itemIdx < itemNum; ++ itemIdx) {
			
			List<Object> stockEntity = (List<Object>) JSONPath.eval(jsonObj, "$[0].items["+itemIdx +"]");
			
			Map<String, Object> stockData = IntStream.range(0, fieldNum).boxed().collect(Collectors.toMap(i -> fields.get(i), i -> stockEntity.get(i)));
			
			// add ticktime
			String ticktime = day + " " + (String)stockData.getOrDefault("ticktime", "00:00:00"); // "2017-01-11" "14:04:18"
			stockData.put("ticktime", ticktime);
			
			Stock stock = new Stock();
			
			stock.updateStock(Constants.STOCK_CODE_LIST_PATTERN, stockData);
			
			stocksinaDAO.writeStock(stock);
		}
		
	}

}
