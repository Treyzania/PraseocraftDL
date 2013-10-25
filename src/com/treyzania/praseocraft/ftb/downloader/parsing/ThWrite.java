package com.treyzania.praseocraft.ftb.downloader.parsing;

import com.treyzania.praseocraft.ftb.downloader.PackFile;
import com.treyzania.praseocraft.ftb.downloader.jobbing.Job;
import com.treyzania.praseocraft.ftb.downloader.jobbing.JobWrite;

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
