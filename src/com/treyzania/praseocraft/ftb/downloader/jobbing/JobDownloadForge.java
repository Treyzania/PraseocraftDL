package com.treyzania.praseocraft.ftb.downloader.jobbing;

public class JobDownloadForge extends Job {

	public String forgeVer;
	
	public JobDownloadForge(Joblist jl, String version) {
		
		super(jl);
		
		this.forgeVer = version;
		
	}

}
