package com.wbximy.crawler.tools;

import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class JSParser {
	private ScriptEngineManager sem = new ScriptEngineManager();  
    private ScriptEngine engine = sem.getEngineByName("JavaScript"); 
    
    public void run(String script) {
    	try {
			engine.eval(script);
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public Object getObject(String var) {
    	Object obj = engine.get(var);
    	return obj;
    }
    
    public List<Object> getList(String var) {
    	
    	String varLen = var + "Len"; 
		run(varLen + " = " + var + ".length");
		
    	long len = (Long) engine.get(varLen);
    	List<Object> res = new ArrayList<Object>();
    	for (int i = 0; i < len; ++ i) {
    		String varI = var + i;
    		run(varI + "=" + var + "[" + i + "]");
    		res.add(engine.get(varI));
    	}
    	return res;
    }
  public List<List<Object>> getListOfList(String var) {
    	
    	String varLen = var + "Len"; 
		run(varLen + " = " + var + ".length");
		
    	long len = (Long) engine.get(varLen);
    	List<List<Object>> res = new ArrayList<List<Object>>();
    	for (int i = 0; i < len; ++ i) {
    		String varI = var + i;
    		run(varI + "=" + var + "[" + i + "]");
    		res.add(getList(varI));
    	}
    	return res;
    }
}
