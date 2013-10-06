package com.treyzania.praseocraft.ftb.downloader.parsing;

import com.treyzania.praseocraft.ftb.downloader.Domain;

public class FileOp {

	public String src;
	public String loc;
	public String action;
	
	public Domain type;
	public int priority;
	
	public FileOp(String src, String loc, Domain ft, int priority, String act) {
		
		this.src = src;
		this.loc = loc;
		
		this.type = ft;
		this.priority = priority;
		this.action = act;
		
	}
	
	public boolean isActionless() {
		return this.action == "_";
	}
	
	public boolean isActionUnzip() {
		return this.action == "unzip";
	}
	
	public boolean isActionOverwrite() {
		return this.action == "unzip-over";
	}
	
	public boolean isClient() {
		return this.type == Domain.GENERIC || this.type == Domain.CILENT;
	}
	
	public boolean isServer() {
		return this.type == Domain.GENERIC || this.type == Domain.SERVER;
	}
	
}
