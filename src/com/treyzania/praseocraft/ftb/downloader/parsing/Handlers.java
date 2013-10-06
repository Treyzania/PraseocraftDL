package com.treyzania.praseocraft.ftb.downloader.parsing;

import java.util.ArrayList;

import nu.xom.Element;

public class Handlers {

	public static ArrayList<TagHandler> handlers;
	
	public static void registerHandler(TagHandler th) {
		
		handlers.add(th);
		
	}
	
	public static void handleTag(PackFile pf, Element ele) {
		
		TagHandler tagHandler = null;
		
		// Find which TagHandler to use.
		for ( TagHandler triedTH : handlers ) {
			
			if (triedTH.getHandledTag() == ele.getQualifiedName()) {
				
				tagHandler = triedTH;
				break;
				
			}
			
		}
		
		tagHandler.handleTag(pf, ele);
		
		// Not the best possible code for this method, but I don't care.
		
	}
	
	static {
		
		handlers = new ArrayList<TagHandler>();
		
		registerHandler(new THFile());
		registerHandler(new THMeta());
		// Probably going to add more.
		
	}
	
}
