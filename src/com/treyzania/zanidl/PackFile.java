package com.treyzania.zanidl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

import nu.xom.Attribute;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;

import com.treyzania.zanidl.jobbing.Job;
import com.treyzania.zanidl.jobbing.JobAddLauncherProfile;
import com.treyzania.zanidl.jobbing.JobDownloadForge;
import com.treyzania.zanidl.jobbing.JobDownloadServer;
import com.treyzania.zanidl.jobbing.JobGetMinecraft;
import com.treyzania.zanidl.jobbing.JobInstallForge;
import com.treyzania.zanidl.jobbing.Joblist;
import com.treyzania.zanidl.parsing.Handlers;
import com.treyzania.zanidl.resouces.MasterFrame;
import com.treyzania.zanidl.resouces.MetaCollector;
import com.treyzania.zanidl.resouces.NotificationFrame;
import com.treyzania.zanidl.resouces.Worker;

public class PackFile implements Runnable {
	
	private static final String[] wNames = {"Alpha", "Bravo", "Charlie", "Delta", "Echo", "Foxtrot", "Golf", "Hotel"};
	
	public boolean errored = false;
	
	public final long time;
	public final String packAddr; // The place the pack file is hosted.
	public final String packVer; // The "ver" attribute of the "version" tag.  Can be a fancy name like the Android devs do.  (ie. "Jelly Bean")
	
	public Joblist joblist;
	public Worker[] workers = null;
	public Domain domain;
	
	public Document doc;
	public MetaCollector metadata;
	public Thread pfexe;
	
	public PackFile(String addr, String ver) {
		
		this.packAddr = addr;
		this.packVer = ver;
		
		this.joblist = new Joblist();
		this.metadata = new MetaCollector();
		this.pfexe = new Thread(this, "PackFile-Thread");
		this.joblist = new Joblist();
		
		this.time = System.currentTimeMillis();
				
	}
	
	public String generateLauncherVersionName() {
		
		return (this.metadata.access("MCVersion") + "-Forge" + this.metadata.access("ForgeVersion"));
		
	}
	
	public String generatePackName() {
		
		String name = "PraseocraftDL_Pack-" + Long.toHexString(this.time);
		
		String packName = metadata.access("PackName");
		if (packName != null) {
			name = packName + "-" + this.packVer;
		}
		
		return name;
		
	}
	
	public String generatePackPath() {
		return Util.fs_sysPath(Util.getPCDLDir() + "/packs/" + this.generatePackName());
	}
	
	public String generatePackJarPath() {
		
		String path = Util.getTempDir() + "/lostGameJar" + Long.toHexString(this.time) + ".jar";
		
		if (this.domain == Domain.CLIENT) {
			path = Util.getMinecraftDir() + "/versions/" + this.generateLauncherVersionName() + "/" + this.generateLauncherVersionName() + ".jar";
		} else if (this.domain == Domain.SERVER) {
			path = this.generatePackPath() + "/minecraft_server.jar";
		}
		
		return path;
		
	}
	
	private void defineWorkers() {
		
		this.workers = new Worker[1]; // Expand/abstractify as necessary!
		for (int i = 0; i < workers.length; i++) {
			
			this.workers[i] = new Worker(wNames[i], joblist, domain);
			
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public void executeSequence() {
		
		MasterFrame frame = ZaniDL.frame;
		
		ZaniDL.log.info("Pack Location: " + frame.addrField.getText());
		
		boolean docbuild = this.buildDocument();
		ZaniDL.log.info(docbuild ? "Document build successfully!" : "Document build failure!  That's an ERROR!");
		
		Domain d = null;
		String dString = "";
		
		// Determine the download type.  (Domain)
		if (frame.buttonClient.isSelected()) {
			
			d = Domain.CLIENT;
			dString = "client";
			
		} else if (frame.buttonServer.isSelected()) {
			
			d = Domain.SERVER;
			dString = "server";
			
		} // There will always be one selected because the Client button is selected by default.
		ZaniDL.log.info("Dowload Type: " + d.toString());
		
		this.domain = d;
		
		ZaniDL.log.info("Preparing PackFile, initalizing...");
		
		ZaniDL.dlMode = dString;
		this.readJobs(d);
		
		if (!this.errored) {
			
			ZaniDL.log.info("Job list created and organized successfully. (Hopefully...)");
			
			// Dump some generic information.
			ZaniDL.log.info("MC Version: " + metadata.access("MCVersion"));
			ZaniDL.log.info("Forge Version: " + metadata.access("ForgeVersion"));
			
			frame.progressBar.setMaximum(this.joblist.getJobsRemaining());
			
			this.defineWorkers();
			
			ZaniDL.log.info("Starting workers...");
			
			this.startWorkers();
			this.waitForWorkersToFinish();
			
			ZaniDL.frame.beep();
			NotificationFrame.wait("Pack building done!");
			
			ZaniDL.log.info("Pack Path: " + Util.fs_sysPath(this.generatePackPath()));
			ZaniDL.log.info("Pack Name: " + this.generatePackName());
			ZaniDL.log.info("(More stastics coming later, such as build times!)");
			
		}
		
	}

	private void waitForWorkersToFinish() {
		
		boolean allFinished = false;
		
		while (!allFinished) {
			
			int onesDone = 0;
			for (Worker w : this.workers) {
				
				if (w.isFinished) onesDone++;
				
			}
			
			if (onesDone == workers.length) {
				allFinished = true;
			}
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {}
			
			System.gc(); // Just to be nice!
			
		}
		
	}
	
	public boolean buildDocument() {
		
		boolean out = true;
		Builder b = null;
		b = new Builder();
		
		String xml = Util.readWebpage(this.packAddr);
		String newXml = "";
		
		ZaniDL.log.info("Raw XML Length: " + xml.length() + " chars.");
		
		// Add more lines if needed.  I hope that nobody actually had to use any XML entities.
		newXml = xml
				.replace("&", "&amp;")
				;
		
		// Actually build it.
		try {
			this.doc = b.build(new ByteArrayInputStream(newXml.getBytes("UTF-8")));
		} catch (ParsingException | IOException e) {
			ZaniDL.log.severe("Document build error: " + e.getMessage());
			System.out.println(e.getMessage());
			out = false;
		}
		
		System.out.println(this.doc);
		return out;
		
	}
	
	public void readJobs(Domain dom) {
		
		// Set up data pool.
		ArrayList<Element> elementPool = new ArrayList<Element>(); // The pool for all of the individual process tags.
		
		// Start extracting data.
		Element root = doc.getRootElement(); // Should be a "pack" tag.
		
		// Make sure we are dealing with the proper version of the files to use!
		Elements verTags = root.getChildElements("version");
		Element ver = null;
		for (int i = 0; i < verTags.size(); i++) {
			Element e = verTags.get(i);
			System.out.println("VER_TAG: " + i + ", " + e.toString() + ", " + e.getAttribute("ver"));
			if (e.getAttribute("ver").getValue().equals(this.packVer)) {
				System.out.println("I found it! " + i);
				ver = verTags.get(i);
			}
		}
		
		Elements groupTags = null;
		
		try {
			groupTags = ver.getChildElements("group");
		} catch (Exception e) {
			ZaniDL.log.warning("Version element not properly selected, notifing!");
			ZaniDL.frame.dlStatus.setText("That's not a proper verison!");
		}
		
		if (groupTags != null) {
			ZaniDL.frame.dlStatus.setText(MasterFrame.noErrorsText);
		} else {
			return;
		}
		
		Elements globalMetaTags = ver.getChildElements("meta");
		
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
				
				System.out.println("Invalid domain used in Configuration.  Culprit: " + groupDomain.getValue());
				
			}
			
		}
		
		// These two lines are important.
		this.extractNecessaryMetadata(ver);
		this.createFormattingJobs();
		
		// Handle the tags.
		for (Element ele : elementPool) {
			
			Job j = Handlers.handleTag(this, ele);
			
			if (j != null && Domain.Calc.isCompatible(this.domain, Domain.Calc.parseDomain(((Element) ele.getParent()).getAttribute("domain").getValue()))) {
				// My my, that was a long line...  I hope it works.
				
				this.joblist.addJob(j); // Somewhat anti-climatic.
				
			}
			
		}
		
		ZaniDL.log.info("Pack Name: " + this.metadata.access("PackName"));
		
		ZaniDL.log.info("If this is being read, then the XML was probably parsed successfully!  " + elementPool);
		
	}
	
	private void extractNecessaryMetadata(Element versionTag) {
		
		String forgeVer = versionTag.getAttribute("forge").getValue();
		String mcVer = versionTag.getAttribute("mcver").getValue();
		
		this.metadata.define("ForgeVersion", forgeVer);
		this.metadata.define("MCVersion", mcVer);
		
	}
	
	public String getMCVersion() {
		return this.metadata.access("MCVersion");
	}
	
	public String getForgeVersion() {
		return this.metadata.access("ForgeVersion");
	}
	
	private void createFormattingJobs() {
		
		// Initialization.
		ArrayList<Job> tJobs = new ArrayList<Job>();
		
		// Retrieve the locations of temp files.
		String forgeDir = Util.getTempDir() + "/forge-" + this.getForgeVersion() + "-MC" + this.getMCVersion() + ".jar";
		String tempMcJarDir = Util.getTempDir() + "/mc-" + this.getMCVersion() + ".jar"; // I don't care if this is server or client, yet.
		String finalMcJarDir = this.generatePackJarPath();
		
		String fj = "{Formatting Jobs}";
		ZaniDL.log.fine(fj + "Forge Dir: " + forgeDir);
		ZaniDL.log.fine(fj + "Temp. MC Jar Dir: " + tempMcJarDir);
		ZaniDL.log.fine(fj + "Final MC Jar Dir: " + finalMcJarDir);
		
		// Create and register the jobs.
		if (this.domain == Domain.CLIENT) { // Client jobs.
			Job getMc = new JobGetMinecraft(joblist, this, tempMcJarDir);
			Job dlForge = new JobDownloadForge(joblist, this.getForgeVersion(), this.getMCVersion(), forgeDir);
			Job installForge = new JobInstallForge(joblist, tempMcJarDir, forgeDir, finalMcJarDir);
			Job alp = new JobAddLauncherProfile(joblist, this.generatePackName(), this);
			tJobs.add(getMc);
			tJobs.add(dlForge);
			tJobs.add(installForge);
			tJobs.add(alp);
		}
		if (this.domain == Domain.SERVER) { // Server jobs.
			Job dlServer = new JobDownloadServer(joblist, this.getMCVersion());
			Job dlForge = new JobDownloadForge(joblist, this.getForgeVersion(), this.getMCVersion(), forgeDir);
			Job installForge = new JobInstallForge(joblist, tempMcJarDir, forgeDir, finalMcJarDir);
			tJobs.add(dlServer);
			tJobs.add(dlForge);
			tJobs.add(installForge);
		}
		
		// Post-processing.
		for (Job theJob : tJobs) {
			this.joblist.addJob(theJob);
		}
		
	}
	
	public void startWorkers() {
		
		for (Worker w : workers) {
			
			w.jobExecutor.start();
			
		}
		
	}

	@Override
	public void run() {
		
		// I guess this is the only thing.
		this.executeSequence();
		
	}
	
}
