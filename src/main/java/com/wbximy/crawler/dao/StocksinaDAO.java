package com.wbximy.crawler.dao;

import org.apache.log4j.Logger;

import com.wbximy.crawler.mapping.TableMapper;

import lombok.Setter;

public class StocksinaDAO {
	Logger logger = Logger.getLogger(StocksinaDAO.class);

	@Setter private TableMapper tableMapper;
	
	public boolean existTable(String tableName) {
		return tableMapper.existTable(tableName) > 0;
	}
}
