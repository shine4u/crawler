package com.wbximy.crawler.mapping.win007nba;

import java.sql.Timestamp;

import org.apache.ibatis.annotations.Param;

import com.wbximy.crawler.domain.win007nba.MatchStat;


public interface MatchStatMapper {
	
	/* 根据matchId获取一条记录 */
	MatchStat select(@Param("matchId")int matchId);
	
	/* 插入一条记录 */
	int insert(MatchStat match);

}
