package com.treyzania.zanidl.parsing;

import java.util.ArrayList;

import com.treyzania.zanidl.PackFile;
import com.treyzania.zanidl.ZaniDl;
import com.treyzania.zanidl.jobbing.Job;

import nu.xom.Element;

public class Handlers {

	public static ArrayList<TagHandler> handlers;
	
	public static void registerHandler(TagHandler th) {
		
		handlers.add(th);
		
	}
	
	public static Job handleTag(PackFile pf, Element ele) {
		
		TagHandler tagHandler = null;
		Job j = null;
		
		// Find which TagHandler to use.
		for ( TagHandler triedTH : handlers ) {
			
			if (triedTH.getHandledTag() == ele.getLocalName()) {
				
				tagHandler = triedTH;
				break;
				
			}
			
		}
		
		ZaniDl.log.info(tagHandler == null ? "Tag (" + ele.getQualifiedName() + ") not supported!  Ignoring!" : "Using tag handler: " + tagHandler.getClass().getSimpleName());
		if (tagHandler != null) {
			j = tagHandler.handleTag(pf, ele);
		}
		
		return j;
		// Not the best possible code for this method, but I don't care.
		
	}
	
	static {
		
		handlers = new ArrayList<TagHandler>();
		
		registerHandler(new ThFile());
		registerHandler(new ThMeta());
		registerHandler(new ThWrite());
		registerHandler(new ThMod());
		// Probably going to add more.
		
	}
	
}
