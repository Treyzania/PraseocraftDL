package com.treyzania.praseocraft.ftb.downloader.parsing;

import nu.xom.Element;

public abstract class TagHandler {

	public TagHandler() {
		
	}
	
	public abstract String getHandledTag();
	public abstract boolean handleTag(PackFile pf, Element element);
	
}
