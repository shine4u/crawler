package com.wbximy.crawler.dao;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.wbximy.crawler.domain.stocksina.Stock;
import com.wbximy.crawler.mapping.stocksina.StockMapper;
import com.wbximy.crawler.mapping.stocksina.TableMapper;

import lombok.Setter;

public class StocksinaDAO {
	Logger logger = Logger.getLogger(StocksinaDAO.class);

	@Setter private TableMapper tableMapper;
	@Setter private StockMapper stockMapper;
	
	private static Set<String> checkedTables = new HashSet<String>();
	
	private boolean existTable(String tablename) {
		return tableMapper.exist(tablename) > 0;
	}
	
	public void addStock(Stock stock) {
		String tablename = Stock.class.getSimpleName();
		if (!checkedTables.contains(tablename)) {
			checkedTables.add(tablename);
			logger.info("checked for table creation tablename=" + tablename);
			if (!existTable(tablename)) {
				String sql = "CREATE TABLE " + tablename + "(";
				for (String col : Stock.fieldRules.keySet()) {
					sql += "\n" + col + " " + Stock.fieldRules.get(col) + ",";
				}
				sql += "\nPRIMARY KEY(" + Stock.primaryKeyRule + ")";
				sql += "\n);";
				
				tableMapper.create(sql);
			}	
		}
		stockMapper.add(stock);
	}
	
}
