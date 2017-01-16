# stocksina-crawler
抓取股票数据，目前抓取新浪站点的

股票列表以及最新指标 http://money.finance.sina.com.cn/d/api/openapi_proxy.php/?__s=[["hq","hs_a","",0,3,40]] 

股票股东信息 http://vip.stock.finance.sina.com.cn/corp/go.php/vCI_CirculateStockHolder/stockid/600000/displaytype/30.phtml

股票历史指标 http://vip.stock.finance.sina.com.cn/quotes_service/view/vMS_tradehistory.php?symbol=sz000858&date=2016-03-28

股票按天的分价格图 http://market.finance.sina.com.cn/pricehis.php?symbol=sz000858&startdate=2017-01-05&enddate=2017-01-05

主要使用的技术： spring, mybatis, sqlite3, webmagic.