package com.wbximy.crawler.win007.processor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.wbximy.crawler.Constants;
import com.wbximy.crawler.SiteSetting;
import com.wbximy.crawler.win007.MatchTxtLiveIncSimulator;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class TxtLivePageProcessor implements PageProcessor {

	Logger logger = Logger.getLogger(TxtLivePageProcessor.class);

	@Override
	public Site getSite() {
		return SiteSetting.getSite(Constants.NBA_WIN007_SITE);
	}

	// http://nba.win007.com/jsData/txtLive/2/57/257293.js
	@Override
	public void process(Page page) {
		String url = page.getUrl().toString();
		String html = page.getHtml().toString();

		String pat = "http://nba\\.win007\\.com/jsData/txtLive/\\d/\\d\\d/(\\d+).js";
		Matcher matcher = Pattern.compile(pat).matcher(url);
		if (!matcher.find()) {
			logger.warn("can't parse data from url=" + url + " pat=" + pat);
			return;
		}
		
		int matchId = Integer.parseInt(matcher.group(1));
		String txtLiveData = page.getHtml().xpath("/html/body/text()").toString();
		String[] txtLiveQuarterTokens = txtLiveData.split("$");
		
		MatchTxtLiveIncSimulator simulator = new MatchTxtLiveIncSimulator();
		try {
			assert(txtLiveQuarterTokens.length == 4);
			for (int idx = 0; idx < txtLiveQuarterTokens.length; ++ idx) {
				String[] txtMsgTokens = txtLiveQuarterTokens[idx].split("!");
				for (String txtMsg : txtMsgTokens) {
					String[] txtActionTokens = txtMsg.split("^");
					String actionTime = txtActionTokens[0];
					int actionType = Integer.parseInt(txtActionTokens[1]);
					int homeTeamCurPoints = Integer.parseInt(txtActionTokens[2]);
					int awayTeamCurPoints = Integer.parseInt(txtActionTokens[3]);
					String actionTxt = txtActionTokens[4];
					int actionId = Integer.parseInt(txtActionTokens[5]);
					simulator.simulate(actionTime, actionType, homeTeamCurPoints, awayTeamCurPoints, actionTxt, actionId);
				}	
			}
		} catch (Exception e) {
			logger.warn("error parse txtLiveData=" + txtLiveData.substring(0, 100) + "...");
			e.printStackTrace();
		}
		
		// write data to MatchDetail.
	}

	public static void main(String[] args) {

		Spider.create(new TxtLivePageProcessor())
				// 从"https://github.com/code4craft"开始抓
				.addUrl("http://nba.win007.com/jsData/txtLive/2/57/257293.js")
				// 开启1个线程抓取
				.thread(1)
				// 启动爬虫
				.run();
	}
}
