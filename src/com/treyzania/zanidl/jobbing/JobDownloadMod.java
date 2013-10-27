package com.treyzania.zanidl.jobbing;

import com.treyzania.zanidl.ZaniDl;
import com.treyzania.zanidl.Util;

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
		
		String filename = ZaniDl.packfile.generatePackPath() + "/mods/" + this.modFilename;
		String possNN = metadata.access("NewName");
		if (possNN != null && possNN != "") {
			filename = possNN;
		}
		
		String newAddr = this.modAddr
				.replace("&amp;", "&")
				;
		
		String newFilename = filename
				.replace("%20", "_");
		
		ZaniDl.log.fine("JOBS: Downloading mod \'" + newAddr + "\' , to " + newFilename + " .");
		
		boolean out = Util.download(newAddr, newFilename);
		
		/*
		 * 
		 * The comment that was here is no longer relevant.  But it still lives on.SWWWWW
		 * 
		 */
		
		return out;
		
	}

}
