package com.treyzania.zanidl.parsing;

import nu.xom.Attribute;
import nu.xom.Element;

import com.treyzania.zanidl.PackFile;
import com.treyzania.zanidl.jobbing.Job;
import com.treyzania.zanidl.jobbing.JobDownloadMod;

public class ThMod extends TagHandler {

	@Override
	public String getHandledTag() {
		return "mod";
	}

	@Override
	public Job handleTag(PackFile pf, Element element) {
		
		String[] loc = element.getValue().split("\\/");
		
		String modLocation = loc[loc.length - 1]; // Technically speaking, this should be the name of the Zip file that will be in the "/mods/" folder.
		String modAddress = element.getValue();
		
		Job j = new JobDownloadMod(pf.joblist, modLocation, modAddress);
		
		Attribute renameAttr = element.getAttribute("rename");
		if (renameAttr != null) {
			j.metadata.define("NewName", renameAttr.getValue());
		}
		
		return j;
		
	}

}
