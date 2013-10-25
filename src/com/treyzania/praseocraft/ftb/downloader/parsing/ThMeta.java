package com.treyzania.praseocraft.ftb.downloader.parsing;

import com.treyzania.praseocraft.ftb.downloader.PackFile;
import com.treyzania.praseocraft.ftb.downloader.jobbing.Job;

import nu.xom.Element;

public class ThMeta extends TagHandler {

	@Override
	public String getHandledTag() {
		
		return "meta";
		
	}

	@Override
	public Job handleTag(PackFile pf, Element element) {
		
		String name = element.getAttribute("name").getValue();
		String val = element.getValue();
		
		pf.metadata.define(name, val);
		
		return null;
		
	}

}
