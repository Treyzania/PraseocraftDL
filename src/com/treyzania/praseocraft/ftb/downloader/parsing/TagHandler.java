package com.treyzania.praseocraft.ftb.downloader.parsing;

import com.treyzania.praseocraft.ftb.downloader.PackFile;
import com.treyzania.praseocraft.ftb.downloader.jobbing.Job;

import nu.xom.Element;

public abstract class TagHandler {

	public TagHandler() {
		
	}
	
	public abstract String getHandledTag();
	public abstract Job handleTag(PackFile pf, Element element);
	
}
