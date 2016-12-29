package com.wbximy.crawler;

import java.util.HashMap;
import java.util.Map;

import us.codecraft.webmagic.Site;


public class SiteSetting {
	private static Map<String, Site> siteMap = new HashMap<String, Site>();
	
	static {
		siteMap.put("nba.win007.com", Site.me().setRetryTimes(3).setSleepTime(1000).setCharset("UTF-8")
			.addHeader("User-Agent",  "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.11; rv:50.0) Gecko/20100101 Firefox/50.0"));
	}
	
	public static Site getSite(String site) {
		// URI uri = new URI(url);
	    // String hostname = uri.getHost();
		return siteMap.get(site);
	}
}
