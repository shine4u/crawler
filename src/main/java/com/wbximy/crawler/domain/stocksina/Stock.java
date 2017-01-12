package com.wbximy.crawler.domain.stocksina;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import com.wbximy.crawler.Constants;
import com.wbximy.crawler.tools.TypeConverter;

import lombok.Data;

@Data public class Stock {
	
	public final static Map<String, String> fieldRules = new HashMap<String, String>();
	public final static String primaryKeyRule;
	static {
		primaryKeyRule = "stockCode";
		
		fieldRules.put("stockCode", "VARCHAR(10) not null");
		fieldRules.put("stockName", "VARCHAR(100)");
		fieldRules.put("curPrice", "DECIMAL(10, 3)");
		fieldRules.put("changepercent", "DECIMAL(10, 3)");
		fieldRules.put("buy", "DECIMAL(10, 3)");
		fieldRules.put("sell", "DECIMAL(10, 3)");
		fieldRules.put("prevEndPrice", "DECIMAL(10, 3)");
		fieldRules.put("curBegPrice", "DECIMAL(10, 3)");
		fieldRules.put("curHighestPrice", "DECIMAL(10, 3)");
		fieldRules.put("curLowestPrice", "DECIMAL(10, 3)");
		fieldRules.put("tradeHands", "BIGINT");
		fieldRules.put("tradeAmount", "BIGINT");
		fieldRules.put("ticktime", "DATETIME");
		fieldRules.put("per", "REAL");
		fieldRules.put("per_d", "REAL");
		fieldRules.put("nta", "DECIMAL(10, 4)");
		fieldRules.put("pb", "REAL");
		fieldRules.put("mktcap", "REAL");
		fieldRules.put("nmc", "REAL");
		fieldRules.put("turnoverratio", "REAL");
	}
	
	private String stockCode; // 600000 PRIMARY KEY
	
	// 接下来的来自 StockCodeList
	private String stockName; // 浦发银行
	private double curPrice; // 当前价格 16.170
	private double changePercent; // 价格变化百分比 2% 存2
	private double buy; // 当前买入价 更新达不到要求 此字段不可信
	private double sell; // 当前卖出价 更新达不到要求 此字段不可信
	private double prevEndPrice; // 昨收
	private double curBegPrice; // 今开
	private double curHighestPrice; // 今高
	private double curLowestPrice;
	private long tradeHands; // 成交量 手
	private long tradeAmount; // 成交额  元
	private Timestamp ticktime; // 最近更新时间 datetime
	private double per; // per TODO
	private double per_d; // per TODO
	private double nta; // 每股净资产 元
	private double pb; // 市盈率
	private double mktcap; // 总市值 亿
	private double nmc; // 流通市值 亿
	private double turnoverratio; // 换手率 0.02155
	
	public void updateStock(String pat, Map<String, Object> dataMap) {
		// TODO Auto-generated method stub
	
		Field[] fields = this.getClass().getDeclaredFields();
		
		if (pat.equals(Constants.STOCK_CODE_LIST_PATTERN)) {
			for (Field field : fields) {
				String fieldname = field.getName();
				if (!dataMap.containsKey(fieldname)) continue;
				TypeConverter.cvt(this, field, dataMap.get(fieldname));
			}
		}
	}
}
