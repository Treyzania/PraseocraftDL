package com.treyzania.praseocraft.ftb.downloader.jobbing;

import com.treyzania.praseocraft.ftb.downloader.Util;
import com.treyzania.praseocraft.ftb.downloader.Pcdl;
import com.treyzania.praseocraft.ftb.downloader.resouces.Downloader;

public class JobDownloadForge extends Job {

	public String forgeVer;
	public String mcVer;
	
	public JobDownloadForge(Joblist jl, String version, String mcVersion) {
		
		super(jl);
		
		this.forgeVer = version;
		this.mcVer = mcVersion;
		
	}

	@Override
	public boolean runJob() {
		
		boolean out = true;
		
		Pcdl.log.fine("JOBS: Downloading MCForge version \'" + this.forgeVer + "\' for Minecraft \'" + this.mcVer + "\'.");
		
		String forgeAddr = "http://files.minecraftforge.net/minecraftforge/minecraftforge-universal-" + this.mcVer + "-" + this.forgeVer + ".jar";
		String tempLocation = Util.getTempDir() + "/forge-" + this.forgeVer + "-MC" + this.mcVer + ".jar";
		
		out = Downloader.download(forgeAddr, tempLocation);
		
		return out;
		
	}

}
