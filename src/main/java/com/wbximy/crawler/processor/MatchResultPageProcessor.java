package com.wbximy.crawler.processor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.wbximy.crawler.SiteSetting;
import com.wbximy.crawler.mapping.BasketballMatchStatMapper;
import com.wbximy.crawler.tools.JSParser;
import com.wbximy.crawler.tools.TypeConverter;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class MatchResultPageProcessor implements PageProcessor {

	Logger logger = Logger.getLogger(MatchResultPageProcessor.class);

	private JSParser jsParser = new JSParser();
	
	private static final int maxMatchNum = 2; // 用于测试， -1表示不限制 
	
	@Autowired
	private BasketballMatchStatMapper basketballMatchStatMapper;

	@Override
	public Site getSite() {
		return SiteSetting.getSite("nba.win007.com");
	}

	// seasonId ymId matchResultStat :
	// http://nba.win007.com/jsData/matchResult/16-17/l1_1_2016_12.js?version=2016122712
	@Override
	public void process(Page page) {
		String url = page.getUrl().toString();
		String html = page.getHtml().toString();

		String pat = "http://nba\\.win007\\.com/jsData/matchResult/(\\d\\d\\-\\d\\d)/l1_1_(\\d+_\\d+)\\.js";
		// 赛程页，月份，时间排序
		Matcher matcher = Pattern.compile(pat).matcher(url);
		if (!matcher.find()) {
			logger.warn("can't parse data from url=" + url + " pat=" + pat);
			return;
		}
		String seasonId = matcher.group(1);
		String ymId = matcher.group(2);
		String script = page.getHtml().xpath("/html/body/text()").toString();
		jsParser.run(script);

		List<Object> arrLeague = jsParser.getList("arrLeague");
		List<List<Object>> arrTeam = jsParser.getListOfList("arrTeam");
		List<List<Object>> ymList = jsParser.getListOfList("ymList");
		Object lastUpdateTime = jsParser.getObject("lastUpdateTime");
		List<List<Object>> arrData = jsParser.getListOfList("arrData");
		
		if (maxMatchNum != -1) {
			if (arrData.size() > maxMatchNum) {
				arrData = arrData.subList(0, maxMatchNum);
			}
		}
		
		for (List<Object> matchEntry : arrData) {
			try {
				int matchId = (Integer) matchEntry.get(0);
				int matchType = (Integer) matchEntry.get(1);
				// 2016-12-01 08:30
				Timestamp matchTime = TypeConverter.toTimestamp((String) matchEntry.get(2), "yyyy-MM-dd hh:mm");
				int homeTeamId = (Integer) matchEntry.get(3);
				int awayTeamId = (Integer) matchEntry.get(4);
				int homeTeamPoints = (Integer) matchEntry.get(5);
				int awayTeamPoints = (Integer) matchEntry.get(6);
				int homeTeamPointsHalf = (Integer) matchEntry.get(7);
				int awayTeamPointsHalf = (Integer) matchEntry.get(8);
				int varXXX = (Integer) matchEntry.get(9);
				double handicap = TypeConverter.toDouble(matchEntry.get(10));
				double totals = TypeConverter.toDouble(matchEntry.get(11));
				int varXXX2 = (Integer) matchEntry.get(12);
				int varXXX3 = (Integer) matchEntry.get(13);
				logger.info("get a match: " + matchEntry);

				// 写数据到sqlite
				// logger.debug(basketballMatchStatMapper.get(matchId));
				/*if (!basketballMatchStatMapper.contains(matchId)) {
					basketballMatchStatMapper.add(matchId, matchType, matchTime, homeTeamId, awayTeamId, homeTeamPoints,
							awayTeamPoints, homeTeamPointsHalf, awayTeamPointsHalf, handicap, totals);
					*/
					String matchIdSub1 = String.valueOf(matchId).substring(0, 1);
					String matchIdSub2 = String.valueOf(matchId).substring(1, 3);
					String techStatUrl = "http://nba.win007.com/jsData/tech/" + matchIdSub1 + "/" + matchIdSub2 + "/"
							+ matchId + ".js";
					String txtLiveStatUrl = "http://nba.win007.com/jsData/txtLive/" + matchIdSub1 + "/" + matchIdSub2
							+ "/" + matchId + ".js";
					page.addTargetRequest(techStatUrl);
					// page.addTargetRequest(txtLiveStatUrl);
				/*}*/
				

			} catch (Exception e) {
				if (matchEntry.get(5) == null) {
					logger.debug("match is not finished.");
				} else {
					logger.warn("error parse matchEntry entry: " + matchEntry);
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {

		Spider.create(new MatchResultPageProcessor())
				// 从"https://github.com/code4craft"开始抓
				.addUrl("http://nba.win007.com/jsData/matchResult/16-17/l1_1_2016_12.js")
				// 开启1个线程抓取
				.thread(1)
				// 启动爬虫
				.run();
	}
}
