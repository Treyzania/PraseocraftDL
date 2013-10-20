package com.treyzania.praseocraft.ftb.downloader.parsing;

import com.treyzania.praseocraft.ftb.downloader.PackFile;

import nu.xom.Element;

public class THMeta extends TagHandler {

	@Override
	public String getHandledTag() {
		
		return "meta";
		
	}

	@Override
	public boolean handleTag(PackFile pf, Element element) {
		
		String name = element.getAttribute("name").getValue();
		String val = element.getValue();
		
		pf.metadata.define(name, val);
		
		return true;
		
	}

}
