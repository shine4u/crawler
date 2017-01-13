package com.wbximy.crawler.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.log4j.Logger;

public class HtmlHelper {

	static Logger logger = Logger.getLogger(HtmlHelper.class);

	/*
	 * <tr>
	 * 	  <td>涨跌幅:</td>
	 * 	  <td><h6><span style="color:#FF0000">3.05%</span></h6></td>
	 * </tr>
	 * 获取当前html string中的文本，采用token遍历的方法。
	 * webmagic不支持 string(*)的xpath语法  http://blog.csdn.net/MrLevo520/article/details/53158050 
	 * 文本<标签 * >文本</标签>文本
	 * 文本<标签 * />
	 */
	
	public static List<String> getText(String html) {
		
		List<String> texts = new ArrayList<String>();
		
		// Stack<String> nodes = new Stack<String>(); TODO
		
		int state = 0;
		int pos = 0;
		for (int idx = 0; idx < html.length(); ++ idx) {
			// logger.info("idx:" + idx + " state:" + state + " pos:" + pos + " " + html.charAt(idx));
			if (state == 0) { // text state.
				if (html.charAt(idx) == '<') { // begin of node.
					if (idx > pos) {
						// logger.info("add string: " + html.substring(pos, idx));
						texts.add(html.substring(pos, idx));
					}
					state = 1; // at node.
					pos = idx + 1;
				} else {
					if (idx == html.length() - 1) {
						if (idx > pos) {
							texts.add(html.substring(pos, idx + 1));
						}
					}
				}
			} else if (state == 1) {
				if (html.charAt(idx) == '>') {
					state = 0; // at text;
					pos = idx + 1;
				} else {
					// nothing.
				}
			}
		}
		return texts;
	}
	
	public static String removeBlanks(String html) {
		return html.replaceAll("\\s+", "");
	}
}
