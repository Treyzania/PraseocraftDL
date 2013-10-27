package com.treyzania.zanidl.parsing;

import com.treyzania.zanidl.PackFile;
import com.treyzania.zanidl.jobbing.Job;
import com.treyzania.zanidl.jobbing.JobWrite;

import nu.xom.Element;

public class ThWrite extends TagHandler {

	@Override
	public String getHandledTag() {
		
		return "write";
		
	}

	@Override
	public Job handleTag(PackFile pf, Element element) {
		
		Job j = new JobWrite(pf.joblist, element);
		
		return j;
		
	}

}
