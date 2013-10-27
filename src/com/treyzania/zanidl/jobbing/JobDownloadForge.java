package com.treyzania.zanidl.jobbing;

import com.treyzania.zanidl.ZaniDl;
import com.treyzania.zanidl.Util;

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
		
		ZaniDl.log.fine("JOBS: Downloading MCForge version \'" + this.forgeVer + "\' for Minecraft \'" + this.mcVer + "\'.");
		
		String forgeAddr = "http://files.minecraftforge.net/minecraftforge/minecraftforge-universal-" + this.mcVer + "-" + this.forgeVer + ".jar";
		
		out = Util.download(forgeAddr, this.dlDir);
		
		return out;
		
	}

}
