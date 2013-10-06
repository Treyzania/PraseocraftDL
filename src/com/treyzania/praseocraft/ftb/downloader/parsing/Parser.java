package com.treyzania.praseocraft.ftb.downloader.parsing;

import java.io.IOException;

import com.treyzania.praseocraft.ftb.downloader.Domain;
import com.treyzania.praseocraft.ftb.downloader.jobbing.Job;
import com.treyzania.praseocraft.ftb.downloader.jobbing.JobDownload;
import com.treyzania.praseocraft.ftb.downloader.jobbing.Joblist;

import nu.xom.*;

@Deprecated
public class Parser {

	public static OpList parseXML(String addr) {
		
		OpList filelist = new OpList();
		
		Builder b = new Builder();
		Document doc = null;
		try {
			doc = b.build(addr);
		} catch (ValidityException e) {
			e.printStackTrace();
		} catch (ParsingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Element root = doc.getRootElement();
		Elements groups = root.getChildElements("group");
		
		for (int i = 0; i < groups.size(); i++) {
			
			Element group = groups.get(i);
			Elements files = group.getChildElements();
			
			for (int j = 0; j < 1; j++) {
				
				FileOp construction;
				
				Element fileData = files.get(j);
				
				String src = fileData.getAttribute("src").getValue();
				String loc = fileData.getValue();
				String action = fileData.getAttribute("action").getValue(); // Operations: "download", "unzip", "unzip-over", etc.
				int priority = Integer.parseInt(fileData.getAttribute("priority").getValue());
				
				Domain ft = Domain.GENERIC;
				
				Attribute gName = group.getAttribute("name");
				
				if (gName.getValue() == "generic") {
					ft = Domain.GENERIC;
				} else if (gName.getValue() == "client") {
					ft = Domain.CILENT;
				} else if (gName.getValue() == "server") {
					ft = Domain.SERVER;
				}
				
				construction = new FileOp(src, loc, ft, priority, action);
				filelist.addFile(construction);
				
			}
			
		}
		
		return filelist;
		
	}
	
	public static Joblist makeJobs(OpList ol) {
		
		Joblist jl = new Joblist();
		
		for (int i = 0; i < ol.files.size(); i++) {
			
			FileOp fo = ol.files.get(i);
			Job j = null;
			String act = fo.action;
			
			if (act == "normal") {
				j = new JobDownload(jl, fo);
			} else if (act == "unzip") {
				j = new JobDownload(jl, fo);
				((JobDownload) j).isToUnzip = true;
			}
			
			jl.addJob(j);
			
		}
		
		return jl;
		
	}
	
}
