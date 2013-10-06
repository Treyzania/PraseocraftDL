package com.treyzania.praseocraft.ftb.downloader.parsing;

import nu.xom.Element;

public class THMeta extends TagHandler {

	@Override
	public String getHandledTag() {
		
		return "meta";
		
	}

	@Override
	public boolean handleTag(PackFile pf, Element element) {
		
		// DO SOMETHING WITH THIS!!!
		
		return true;
		
	}

}
