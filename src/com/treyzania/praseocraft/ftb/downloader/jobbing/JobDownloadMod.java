package com.treyzania.praseocraft.ftb.downloader.jobbing;

import com.treyzania.praseocraft.ftb.downloader.Pcdl;
import com.treyzania.praseocraft.ftb.downloader.resouces.Downloader;

public class JobDownloadMod extends Job {

	public String modLoc;
	public String modAddr;
	
	public JobDownloadMod(Joblist jl, String modLocation, String modAddress) {
		
		super(jl);
		
		this.modLoc = modLocation;
		this.modAddr = modAddress;
		
	}

	@Override
	public boolean runJob() {
		
		String filename = Pcdl.packFolder + "/mods/" + this.modLoc;
		String possNN = metadata.access("NewName");
		if (possNN != null && possNN != "") {
			filename = possNN;
		}
		
		Pcdl.log.fine("JOBS: Downloading mod \'" + this.modAddr + "\'.");
		
		boolean out = Downloader.download(this.modAddr, filename);
		/*
		 * 
		 * 
		 * 
		 * 
		 * 
		 * Why didn't I put this entire method on one line?
		 * How about we just forget all about this huge comment?
		 * 
		 * 
		 * 
		 * 
		 * 
		 */
		return out;
		
	}

}
