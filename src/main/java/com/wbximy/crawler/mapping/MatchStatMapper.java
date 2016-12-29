package com.wbximy.crawler.mapping;

import java.sql.Timestamp;

import org.apache.ibatis.annotations.Param;

import com.wbximy.crawler.domain.MatchStat;


public interface BasketballMatchStatMapper {
	
	/* 根据matchId获取一条记录 */
	MatchStat get(@Param("matchId")int matchId);
	
	/* 插入一条记录 */
	int insert(MatchStat match);
	
	
	/*boolean add(int matchId, int matchType, Timestamp matchTime, int homeTeamId, int awayTeamId,
			int homeTeamScore,int awayTeamScore, int homeTeamScoreHalf,int awayTeamScoreHalf,
			double handicap, double totals);
	*/
}
