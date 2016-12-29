package com.wbximy.crawler.processor;

import java.sql.Timestamp;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.wbximy.crawler.Constants;
import com.wbximy.crawler.SiteSetting;
import com.wbximy.crawler.tools.TypeConverter;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class TechStatPageProcessor implements PageProcessor {

	Logger logger = Logger.getLogger(MatchResultPageProcessor.class);

	@Override
	public Site getSite() {
		return SiteSetting.getSite(Constants.NBA_WIN007_SITE);
	}

	// http://nba.win007.com/jsData/tech/2/57/257293.js
	@Override
	public void process(Page page) {
		String url = page.getUrl().toString();
		String html = page.getHtml().toString();

		String pat = "http://nba\\.win007\\.com/jsData/tech/\\d/\\d\\d/(\\d+).js";
		Matcher matcher = Pattern.compile(pat).matcher(url);
		if (!matcher.find()) {
			logger.warn("can't parse data from url=" + url + " pat=" + pat);
			return;
		}
		int matchId = Integer.parseInt(matcher.group(1));
		String techStatData = page.getHtml().xpath("/html/body/text()").toString();
		String[] techStatTokens = techStatData.split("$");

		try {
			// 2016-12-01 08:30
			String[] techStatGeneralTokens = techStatTokens[0].split("^");
			Timestamp matchTime = TypeConverter.toTimestamp(techStatGeneralTokens[0], "yyyy-MM-dd hh:mm");
			int matchLengthMinutes = Integer.parseInt(techStatGeneralTokens[1].split(":")[1])
					+ Integer.parseInt(techStatGeneralTokens[1].split(":")[0]) * 60;
			String matchArena = techStatGeneralTokens[2];
			int matchAttendance = Integer.parseInt(techStatGeneralTokens[3]);
			int homeFastBreakPoints = Integer.parseInt(techStatGeneralTokens[4]);
			int awayFastBreakPoints = Integer.parseInt(techStatGeneralTokens[5]);
			int homeInPaintPoints = Integer.parseInt(techStatGeneralTokens[6]);
			int awayInPaintPoints = Integer.parseInt(techStatGeneralTokens[7]);
			int homeMaxLeadPoints = Integer.parseInt(techStatGeneralTokens[8]);
			int awayMaxLeadPoints = Integer.parseInt(techStatGeneralTokens[9]);
			// 写入 MatchStat
			
			
			for (int idx = 1; idx <= 2; ++idx) {
				String[] teamTechStatTokens = techStatTokens[idx].split("!");
				int homeOrAway = idx;
				for (int jdx = 0; jdx < teamTechStatTokens.length; ++jdx) {
					if (idx == teamTechStatTokens.length - 2) {
						String[] teamStatTokens = teamTechStatTokens[idx].split("^");
						int varXXX = Integer.parseInt(teamStatTokens[0]);
						int shootTotal = Integer.parseInt(teamStatTokens[1]); // 2pts+3pts
						int shootScored = Integer.parseInt(teamStatTokens[2]);
						int shoot3PtsTotal = Integer.parseInt(teamStatTokens[3]);
						int shoot3PtsScored = Integer.parseInt(teamStatTokens[4]);
						int freeThrowTotal = Integer.parseInt(teamStatTokens[5]);
						int freeThrowScored = Integer.parseInt(teamStatTokens[6]);
						int offensiveRebounds = Integer.parseInt(teamStatTokens[7]);
						int defensiveRebounds = Integer.parseInt(teamStatTokens[8]);
						int assists = Integer.parseInt(teamStatTokens[9]);
						int fouls = Integer.parseInt(teamStatTokens[10]);
						int steals = Integer.parseInt(teamStatTokens[11]);
						int turnovers = Integer.parseInt(teamStatTokens[12]);
						int blocks = Integer.parseInt(teamStatTokens[13]);
						int points = Integer.parseInt(teamStatTokens[14]);
						// 写入 MatchDetail playerid=0

					} else if (idx == teamTechStatTokens.length - 1) {
						String[] teamStatExTokens = teamTechStatTokens[idx].split("^");
						// int teamId = Integer.parseInt(teamStatExTokens[0]);
						// double teamMatchShootAccuracy = Double.parseDouble(teamStatExTokens[1]) * 0.01; // 可计算得到，不存储
						// double teamMatchShoot3PtsAccuracy = Double.parseDouble(teamStatExTokens[2]) * 0.01; // 可计算得到，不存储
						// double teamMatchFreeeThrowAccuracy = Double.parseDouble(teamStatExTokens[3]) * 0.01; // 可计算得到，不存储
						// int teamMatchSecondAttack = Integer.parseInt(teamStatExTokens[4]); // 数据不准确，不存储
						// int teamMatchTurnover = Integer.parseInt(teamStatExTokens[5]); // 可计算得到，不存储
					} else {
						String[] playerStatTokens = teamTechStatTokens[idx].split("^");
						int playerId = Integer.parseInt(playerStatTokens[0]);
						String playerSCName = playerStatTokens[1];
						String playerTCName = playerStatTokens[2];
						String playerENName = playerStatTokens[3];
						int varXXX = Integer.parseInt(playerStatTokens[4]);
						String position = playerStatTokens[5];
						int minutesPlayed = Integer.parseInt(playerStatTokens[6]);
						int shootTotal = Integer.parseInt(playerStatTokens[7]); // 2pts
																				// +
																				// 3pts
						int shootScored = Integer.parseInt(playerStatTokens[8]);
						int shoot3PtsTotal = Integer.parseInt(playerStatTokens[9]);
						int shoot3PtsScored = Integer.parseInt(playerStatTokens[10]);
						int freeThrowTotal = Integer.parseInt(playerStatTokens[11]);
						int freeThrowScored = Integer.parseInt(playerStatTokens[12]);
						int offensiveRebounds = Integer.parseInt(playerStatTokens[13]);
						int defensiveRebounds = Integer.parseInt(playerStatTokens[14]);
						int assists = Integer.parseInt(playerStatTokens[15]);
						int fouls = Integer.parseInt(playerStatTokens[16]);
						int steals = Integer.parseInt(playerStatTokens[17]);
						int turnovers = Integer.parseInt(playerStatTokens[18]);
						int blocks = Integer.parseInt(playerStatTokens[19]);
						int points = Integer.parseInt(playerStatTokens[20]);
						// 写入 MatchDetail
					}
				}
			}

		} catch (Exception e) {
			logger.warn("error parse techStatData=" + techStatData.substring(0, 100) + "...");
			e.printStackTrace();
		}
	}
}
