package com.treyzania.praseocraft.ftb.downloader.parsing;

import com.treyzania.praseocraft.ftb.downloader.Domain;

public class FileOp {

	public String src;
	public String loc;
	
	public Domain type;
	
	public FileOp(String src, String loc, Domain ft) {
		
		this.src = src;
		this.loc = loc;
		
		this.type = ft;
		
	}
	
	public boolean isClient() {
		return this.type == Domain.GENERIC || this.type == Domain.CLIENT;
	}
	
	public boolean isServer() {
		return this.type == Domain.GENERIC || this.type == Domain.SERVER;
	}
	
}
