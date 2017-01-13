package com.wbximy.crawler.stocksina.processor;

import java.sql.Timestamp;
import java.text.ParseException;
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
import com.wbximy.crawler.domain.stocksina.Stock;
import com.wbximy.crawler.main.UrlPatPageProcessor;
import com.wbximy.crawler.tools.HtmlHelper;
import com.wbximy.crawler.tools.TypeConverter;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.selector.Selectable;

public class CirculateStockHolderPageProcessor implements UrlPatPageProcessor {

	Logger logger = Logger.getLogger(CirculateStockHolderPageProcessor.class);
	
	private final Pattern urlpattern = Pattern.compile(Constants.CIRCULATE_STOCK_HOLDER_PATTERN);
	
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
		
		Selectable  selectable = page.getHtml().xpath("//table[@id='CirculateShareholderTable']/tbody");
		
		String statDateStr = String.join("", HtmlHelper.getText(selectable.xpath("//tr[1]/td[2]").toString())); // 2016-09-30
		Timestamp statDate = TypeConverter.toTimestamp(statDateStr, "yyyy-MM-dd");
		logger.info(statDateStr + " " + statDate);
		
		List<String> holderInfo = new LinkedList<String>();
		
		int lastNo = 0;
		for (int row = 2; ; row ++) {
			String noStr = String.join("", HtmlHelper.getText(selectable.xpath("//tr[" + row + "]/td[1]").toString()));
			noStr = HtmlHelper.removeBlanks(noStr);
			if (Integer.valueOf(lastNo+1).toString().equals(noStr)) {
				String holderName = selectable.xpath("//tr[" + row + "]/td[2]/div/text()").toString(); // 中国移动通信集团广东有限公司
				long stockNum = Long.parseLong(selectable.xpath("//tr[" + row + "]/td[3]/div/text()").toString()); // 持股数量(股) 4103760000 
				double stockPercent = TypeConverter.toDouble(selectable.xpath("//tr[" + row + "]/td[4]/div/text()").toString()); // 持股比例(%)  20.000
				String holderType = selectable.xpath("//tr[" + row + "]/td[5]/div/text()").toString(); // 股本性质 国有股
				holderInfo.add(holderName + " " + stockNum + " " + stockPercent + " " + holderType);
				lastNo += 1;
			} else {
				// 股东排名是有序且连续的
				if (lastNo == 0) {
					continue;
				} else {
					break;
				}
			}
		}
		Map<String, Object> dataMap = new HashMap<String,Object>();
		dataMap.put("stockCode", stockCode);
		dataMap.put("holderInfo", holderInfo);
		Stock stock = new Stock().updateField(dataMap);
		
		logger.info(stock);
		
		page.getResultItems().put("stockholderInfo", stock);
	}

}

