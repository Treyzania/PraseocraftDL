package com.treyzania.praseocraft.ftb.downloader.parsing;

import java.util.ArrayList;

import com.treyzania.praseocraft.ftb.downloader.PCDL;
import com.treyzania.praseocraft.ftb.downloader.PackFile;

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
			
			if (triedTH.getHandledTag() == ele.getLocalName()) {
				
				tagHandler = triedTH;
				break;
				
			}
			
		}
		
		PCDL.log.info(tagHandler == null ? "Tag (" + ele.getQualifiedName() + ") not supported!  Ignoring!" : "Using tag handler: " + tagHandler.toString());
		if (tagHandler != null) tagHandler.handleTag(pf, ele);
		
		// Not the best possible code for this method, but I don't care.
		
	}
	
	static {
		
		handlers = new ArrayList<TagHandler>();
		
		registerHandler(new THFile());
		registerHandler(new THMeta());
		registerHandler(new THWrite());
		// Probably going to add more.
		
	}
	
}
