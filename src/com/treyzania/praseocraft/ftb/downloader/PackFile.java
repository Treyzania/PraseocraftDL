package com.treyzania.praseocraft.ftb.downloader;

import java.io.IOException;
import java.util.ArrayList;

import nu.xom.Attribute;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;

import com.treyzania.praseocraft.ftb.downloader.jobbing.Job;
import com.treyzania.praseocraft.ftb.downloader.jobbing.JobCreateVersionData;
import com.treyzania.praseocraft.ftb.downloader.jobbing.JobDownloadForge;
import com.treyzania.praseocraft.ftb.downloader.jobbing.JobDownloadServer;
import com.treyzania.praseocraft.ftb.downloader.jobbing.JobGetMinecraft;
import com.treyzania.praseocraft.ftb.downloader.jobbing.JobInstallForge;
import com.treyzania.praseocraft.ftb.downloader.jobbing.Joblist;
import com.treyzania.praseocraft.ftb.downloader.parsing.Handlers;
import com.treyzania.praseocraft.ftb.downloader.resouces.MasterFrame;
import com.treyzania.praseocraft.ftb.downloader.resouces.MetaCollector;
import com.treyzania.praseocraft.ftb.downloader.resouces.NotificationFrame;
import com.treyzania.praseocraft.ftb.downloader.resouces.Worker;

public class PackFile implements Runnable {
	
	private static final String[] wNames = {"Alpha", "Bravo", "Charlie", "Delta", "Echo", "Foxtrot", "Golf", "Hotel"};
	
	public final String packAddr; // The place the pack file is hosted.
	public final String packVer; // The "ver" attribute of the "verison" tag.  Can be a fancy name like the Android devs do.  (ie. "Jelly Bean")
	
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
		
		//metadata.define("PackName", "PCDL-Generated_Nameless_Pack");
		
	}
	
	public String generateLauncherVersionName() {
		
		return (this.metadata.access("MCVersion") + "-Forge" + this.metadata.access("ForgeVersion"));
		
	}
	
	public String generatePackName() {
		
		String name = "PraseocraftDL_Pack-" + Long.toHexString(System.currentTimeMillis());
		
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
		
		String path = this.generatePackPath() + "/lostGameJar.jar";
		
		if (this.domain == Domain.CLIENT) {
			path = Util.getTempDir() + "/delete_me/derpityderp.jar";
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
	public void init() {
		
		MasterFrame frame = Pcdl.frame;
		Pcdl.packDir.mkdir();
		
		Pcdl.log.info("Pack Location: " + frame.addrField.getText());
		
		boolean docbuild = this.buildDocument();
		Pcdl.log.info(docbuild ? "Document build successfully!" : "Document build failure!  That's an ERROR!");
		
		Domain d = null;
		String dString = "";
		
		if (frame.buttonClient.isSelected()) {
			
			d = Domain.CLIENT;
			dString = "client";
			
		} else if (frame.buttonServer.isSelected()) {
			
			d = Domain.SERVER;
			dString = "server";
			
		} // There will always be one selected because the Client button is selected by default.
		Pcdl.log.info("Dowload Type: " + d.toString());
		
		this.domain = d;
		
		Pcdl.log.info("Preparing PackFile, initalizing...");
		
		Pcdl.dlMode = dString;
		this.readJobs(d);
		Pcdl.log.info("Job list created and organized successfully. (Hopefully...)");
		
		Pcdl.log.info("MC Version: " + metadata.access("MCVersion"));
		Pcdl.log.info("Forge Version: " + metadata.access("ForgeVersion"));
		
		frame.progressBar.setMaximum(this.joblist.getJobsRemaining());
		
		this.defineWorkers();
		
		Pcdl.log.info("Starting workers...");
		this.startWorkers();
		
		this.waitForWorkersToFinish();
		
		Pcdl.frame.beep();
		NotificationFrame nf = new NotificationFrame("Pack building done!");
		nf.waitForExit(); // I luuuuv this method!
		
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
		Builder b = new Builder();
		
		try {
			this.doc = b.build(this.packAddr);
		} catch (ParsingException | IOException e) {
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
		
		//this.readJobs_readoutElements(root, ver, null);
		
		Elements groupTags = ver.getChildElements("group");
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
				
				System.out.println("Invalid domain used in Configuration.  Culprit: " + groupDomain.getValue());
				
			}
			
		}
		
		//this.readJobs_readoutElements(root, ver, null);
		
		// Handle the tags.
		for (Element ele : elementPool) {
			
			Job j = Handlers.handleTag(this, ele);
			
			if (Domain.Calc.isCompatible(this.domain, Domain.Calc.parseDomain(((Element) ele.getParent()).getAttribute("domain").getValue())) && j != null) {
				// My my, that was a long line...  I hope it works.
				
				this.joblist.addJob(j); // Somewhat anti-climatic.
				
			}
			
		}
		
		this.extractNecessaryMetadata(ver);
		this.createFormattingJobs();
		
		Pcdl.log.info("If this is being read, then the XML was probably parsed successfully!  " + elementPool);
		
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
	
	private void createFormattingJobs() {
		
		// Initialization.
		ArrayList<Job> tJobs = new ArrayList<Job>();
		
		String forgeZipName = Util.getTempDir() + "forge-" + this.metadata.access("ForgeVersion") + "-MC" + this.metadata.access("MCVersion") + ".jar";
		String dirForNewMc = this.generatePackJarPath();
		
		Job dlForge = new JobDownloadForge(joblist, this.metadata.access("ForgeVersion"), this.metadata.access("MCVersion"));
		tJobs.add(dlForge);
		
		if (this.domain == Domain.CLIENT) {
			Job getMc = new JobGetMinecraft(joblist, this, Util.getTempDir() + "/mc-" + this.getMCVersion() + ".jar");
			Job cvd = new JobCreateVersionData(joblist, this);
			tJobs.add(getMc);
			tJobs.add(cvd);
		}
		
		if (this.domain == Domain.SERVER) {
			dirForNewMc = Util.getTempDir() + "/vanillaserver-" + this.metadata.access("MCVersion") + ".jar";
			Job dlServer = new JobDownloadServer(joblist, dirForNewMc);
			tJobs.add(dlServer);
		}
		
		//String forgeLoc = Util.getTempDir() + "/forge-" + Pcdl.packfile.metadata.access("ForgeVersion") + "-MC" + Pcdl.packfile.metadata.access("MCVersion") + ".jar";
		String mcJar = Util.getTempDir() + "/mc-" + this.getMCVersion() + ".jar";
		//String finalJar = Util.getMinecraftDir() + "/versions/" + Pcdl.packfile.generateLauncherVersionName() + "/" + Pcdl.packfile.generateLauncherVersionName() + ".jar";
		Job installForge = new JobInstallForge(joblist, mcJar, forgeZipName, dirForNewMc);
		tJobs.add(installForge);
		
		// Post-processing.
		for (Job theJob : tJobs) {
			this.joblist.addJob(theJob);
		}
		
	}
	
	@SuppressWarnings("unused")
	private void readJobs_readoutElements(Element root, Element ver, Element group) {
		
		Pcdl.log.finest("ELEMENTS READOUT: " + root + "," + ver + "," + group);
		
	}
	
	public void startWorkers() {
		
		for (Worker w : workers) {
			
			w.jobExecutor.start();
			
		}
		
	}

	@Override
	public void run() {
		
		this.init();
		
	}
	
}
