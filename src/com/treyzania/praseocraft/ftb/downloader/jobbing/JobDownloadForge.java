package com.treyzania.praseocraft.ftb.downloader.jobbing;

import com.treyzania.praseocraft.ftb.downloader.Pcdl;
import com.treyzania.praseocraft.ftb.downloader.Util;

public class JobDownloadForge extends Job {

	public String forgeVer;
	public String mcVer;
	public String dlDir;
	
	public JobDownloadForge(Joblist jl, String version, String mcVersion, String dlDir) {
		
		super(jl);
		
		this.forgeVer = version;
		this.mcVer = mcVersion;
		this.dlDir = dlDir;
		
	}

	@Override
	public boolean runJob() {
		
		boolean out = true;
		
		Pcdl.log.fine("JOBS: Downloading MCForge version \'" + this.forgeVer + "\' for Minecraft \'" + this.mcVer + "\'.");
		
		String forgeAddr = "http://files.minecraftforge.net/minecraftforge/minecraftforge-universal-" + this.mcVer + "-" + this.forgeVer + ".jar";
		
		out = Util.download(forgeAddr, this.dlDir);
		
		return out;
		
	}

}
