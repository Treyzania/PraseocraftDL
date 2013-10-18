package com.treyzania.praseocraft.ftb.downloader;

import java.io.IOException;
import java.util.ArrayList;

import nu.xom.Attribute;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;

import com.treyzania.praseocraft.ftb.downloader.jobbing.Joblist;
import com.treyzania.praseocraft.ftb.downloader.jobbing.Worker;
import com.treyzania.praseocraft.ftb.downloader.parsing.Handlers;

public class PackFile {
	
	private static final String[] wNames = {"Alpha", "Bravo", "Charlie", "Delta", "Echo", "Foxtrot", "Golf", "Hotel"};
	
	public final String addr; // The place the pack file is hosted.
	
	public Joblist joblist;
	public Worker[] workers = null;
	
	public Document doc;
	
	public PackFile(String addr) {
		
		this.addr = addr;
		
		this.workers = new Worker[2];
		for (int i = 0; i < workers.length; i++) {
			
			this.workers[i] = new Worker(wNames[i], joblist);
			
		}
		
		this.joblist = new Joblist();
		
	}
	
	public void buildDocument() {
		
		Builder b = new Builder();
		try {
			this.doc = b.build(this.addr);
		} catch (ParsingException | IOException e) {
			String msg = e.getMessage();
			PCDL.log.info(msg);
		}
		
	}
	
	public void readJobs(Domain dom) {
		
		// Set up data pool.
		ArrayList<Element> elementPool = new ArrayList<Element>(); // The pool for all of the individual process tags.
		
		// Start extracting data.
		Element root = doc.getRootElement(); // Should be a "pack" tag.
		
		Elements groupTags = root.getChildElements("group");
		Elements globalMetaTags = root.getChildElements("meta");
		
		// Deal with the global Metas.
		for (int i = 0; i < globalMetaTags.size(); i++) {
			elementPool.add(globalMetaTags.get(i));
		}
		
		// Deal with the 3 groups.  (Or if the configurator is an idiot, more.  I'll get around to ignoring unidentified tags.
		for (int i = 0; i < groupTags.size(); i++) {
			
			Element theGroup = groupTags.get(i);
			Attribute groupDomain = theGroup.getAttribute("domain");
			
			// Check if it's a valid group...
			if (Domain.Calc.isValid(groupDomain.getValue())) {
				
				// ... If so, then extract the children.  (That sounds morbid...  Whatever.)
				Elements children = theGroup.getChildElements();
				for (int j = 0; j < children.size(); j++) {
					
					// Add the baby to the pool!
					elementPool.add(children.get(j));
					
				}
				
			} else {
				
				PCDL.log.fine("Invalid domain used in Configuration.  Culprit: " + groupDomain.getValue());
				
			}
			
		}
		
		// Handle the tags.
		for (Element ele : elementPool) {
			
			Handlers.handleTag(this, ele);
			
		}
		
	}
	
	public void startWorkers() {
		
		for (Worker w : workers) {
			
			w.jobExecutor.start();
			
		}
		
	}
	
}
