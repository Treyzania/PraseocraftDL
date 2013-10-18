package com.treyzania.praseocraft.ftb.downloader.parsing;

import com.treyzania.praseocraft.ftb.downloader.Domain;
import com.treyzania.praseocraft.ftb.downloader.PackFile;
import com.treyzania.praseocraft.ftb.downloader.jobbing.Job;
import com.treyzania.praseocraft.ftb.downloader.jobbing.JobDownload;

import nu.xom.Element;

public class THFile extends TagHandler {

	@Override
	public String getHandledTag() {
		
		return "file";
		
	}

	@Override
	public boolean handleTag(PackFile pf, Element element) {
		
		boolean out = true; // Work this out.
		
		Job j = null;
		FileOp fo = null;
		
		String e_src = element.getAttribute("src").getValue();
		int e_priority = Integer.parseInt(element.getAttribute("priority").getValue());
		String e_action = element.getAttribute("action").getValue();
		String e_dom = ((Element) element.getParent()).getAttribute("domain").getValue(); // I hope this works.
		
		String e_dest = element.getValue();
		
		fo = new FileOp(e_src, e_dest, Domain.Calc.parseDomain(e_dom), e_priority, e_action);
		j = new JobDownload(pf.joblist, fo);
		
		pf.joblist.addJob(j);
		
		return out;
		
	}

}
