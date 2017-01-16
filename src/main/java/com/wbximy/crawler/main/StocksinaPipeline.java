package com.wbximy.crawler.main;

import java.util.LinkedList;
import java.util.List;

import com.wbximy.crawler.dao.StocksinaDAO;
import com.wbximy.crawler.exception.TableNotExistException;
import com.wbximy.crawler.stocksina.domain.Stock;
import com.wbximy.crawler.stocksina.domain.StockHolder;

import lombok.Setter;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class StocksinaPipeline implements Pipeline {
	
	@Setter private StocksinaDAO stocksinaDAO;
	
	@Override
	public void process(ResultItems resultItems, Task task) {
		// TODO Auto-generated method stub

		List<Stock> stocks = resultItems.get("stocks");		
		for (Stock stock : stocks == null ? new LinkedList<Stock>() : stocks) {
			try {
				stocksinaDAO.writeStock(stock);
			} catch (TableNotExistException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		List<StockHolder> holders = resultItems.get("stockHolders");		
		for (StockHolder holder : holders == null ? new LinkedList<StockHolder>() : holders) {
			stocksinaDAO.writeStockHolder(holder);
		}
	}
}
