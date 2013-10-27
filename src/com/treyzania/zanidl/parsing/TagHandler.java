package com.treyzania.zanidl.parsing;

import com.treyzania.zanidl.PackFile;
import com.treyzania.zanidl.jobbing.Job;

import nu.xom.Element;

public abstract class TagHandler {

	public TagHandler() {
		
	}
	
	public abstract String getHandledTag();
	public abstract Job handleTag(PackFile pf, Element element);
	
}
