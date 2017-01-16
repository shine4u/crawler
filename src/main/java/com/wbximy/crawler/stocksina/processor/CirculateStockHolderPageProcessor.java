package com.wbximy.crawler.stocksina.processor;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.wbximy.crawler.Constants;
import com.wbximy.crawler.SiteSetting;
import com.wbximy.crawler.domain.stocksina.StockHolder;
import com.wbximy.crawler.main.UrlPatPageProcessor;
import com.wbximy.crawler.tools.GetterSetter;
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
		
		List<StockHolder> holders = new LinkedList<StockHolder>();
		
		int lastNo = 0;
		for (int row = 2; ; row ++) {
			String noStr = String.join("", HtmlHelper.getText(selectable.xpath("//tr[" + row + "]/td[1]").toString()));
			noStr = HtmlHelper.removeBlanks(noStr);
			if (Integer.valueOf(lastNo+1).toString().equals(noStr)) {
				StockHolder holder = new StockHolder();
				GetterSetter.updateField(holder, "stockCode", stockCode);
				GetterSetter.updateField(holder, "holderPos", noStr);
				GetterSetter.updateField(holder, "infoDate", statDate);
				
				String holderName = selectable.xpath("//tr[" + row + "]/td[2]/div/text()").toString(); // 中国移动通信集团广东有限公司
				GetterSetter.updateField(holder, "holderName", holderName);
				
				long stockNum = Long.parseLong(selectable.xpath("//tr[" + row + "]/td[3]/div/text()").toString()); // 持股数量(股) 4103760000 
				GetterSetter.updateField(holder, "stockNum", stockNum);
				
				double stockPercent = TypeConverter.toDouble(selectable.xpath("//tr[" + row + "]/td[4]/div/text()").toString()); // 持股比例(%)  20.000
				GetterSetter.updateField(holder, "stockPercent", stockPercent);
				
				String stockType = selectable.xpath("//tr[" + row + "]/td[5]/div/text()").toString(); // 股本性质 国有股
				GetterSetter.updateField(holder, "stockType", stockType);
				
				logger.info("stock holder:" + holder);
				holders.add(holder);				
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
		page.getResultItems().put("stockHolders", holders);
	}

}

