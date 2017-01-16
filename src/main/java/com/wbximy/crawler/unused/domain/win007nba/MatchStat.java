package com.wbximy.crawler.unused.domain.win007nba;

import java.sql.Timestamp;

import lombok.Data;

@Data public class MatchStat {
	
	private int matchId;
	private int matchType;
	private Timestamp matchTime;
	private int homeTeamId;
	private int awayTeamId;
	private int homeTeamScore;
	private int awayTeamScore;
	private int homeTeamScoreHalf;
	private int awayTeamScoreHalf;
	private double handicap;
	private double totals;
}
