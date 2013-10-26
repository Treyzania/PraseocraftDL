package com.treyzania.praseocraft.ftb.downloader.jobbing;

import com.treyzania.praseocraft.ftb.downloader.Pcdl;
import com.treyzania.praseocraft.ftb.downloader.Util;

public class JobDownloadMod extends Job {

	public String modFilename;
	public String modAddr;
	
	public JobDownloadMod(Joblist jl, String modFilename, String modAddress) {
		
		super(jl);
		
		this.modFilename = modFilename;
		this.modAddr = modAddress;
		
	}

	@Override
	public boolean runJob() {
		
		String filename = Pcdl.packfile.generatePackPath() + "/mods/" + this.modFilename;
		String possNN = metadata.access("NewName");
		if (possNN != null && possNN != "") {
			filename = possNN;
		}
		
		Pcdl.log.fine("JOBS: Downloading mod \'" + this.modAddr + "\'.");
		
		boolean out = Util.download(this.modAddr, filename);
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
