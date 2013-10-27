package com.treyzania.zanidl.parsing;

import com.treyzania.zanidl.PackFile;
import com.treyzania.zanidl.jobbing.Job;

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
		
		System.out.println("Handling metadata! KEY: \'" + name + "\' VALUE: \'" + val + "\'");
		
		return null;
		
	}

}
