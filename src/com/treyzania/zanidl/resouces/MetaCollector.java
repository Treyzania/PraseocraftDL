package com.treyzania.zanidl.resouces;

import java.util.HashMap;

public class MetaCollector {

	public HashMap<String, String> metas;
	
	public MetaCollector() {
		
		this.metas = new HashMap<String, String>();
		
	}
	
	public void define(String key, String data) {
		
		metas.put(key, data);
		
	}
	
	public String access(String key) {
		
		return metas.get(key);
		
	}
	
}
