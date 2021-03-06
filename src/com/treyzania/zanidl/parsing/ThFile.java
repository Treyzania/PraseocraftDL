package com.treyzania.zanidl.parsing;

import com.treyzania.zanidl.Domain;
import com.treyzania.zanidl.PackFile;
import com.treyzania.zanidl.jobbing.Job;
import com.treyzania.zanidl.jobbing.JobDownload;

import nu.xom.Element;

public class ThFile extends TagHandler {

	@Override
	public String getHandledTag() {
		
		return "file";
		
	}

	@Override
	public Job handleTag(PackFile pf, Element element) {
		
		Job j = null;
		FileOp fo = null;
		
		String e_src = element.getAttribute("src").getValue();
		String e_dom = ((Element) element.getParent()).getAttribute("domain").getValue(); // I hope this works.
		
		String e_dest = element.getValue();
		
		fo = new FileOp(e_src, e_dest, Domain.Calc.parseDomain(e_dom));
		j = new JobDownload(pf.joblist, fo);
		
		j.setDomain(Domain.Calc.parseDomain(e_dom));
		
		return j;
		
	}

}
