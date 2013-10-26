package com.treyzania.praseocraft.ftb.downloader.jobbing;

import com.treyzania.praseocraft.ftb.downloader.Util;

public class JobDownloadServer extends Job {

	public String mcVer;
	
	public JobDownloadServer(Joblist jl, String mcVer) {
		
		super(jl);
		
		this.mcVer = mcVer;
		
	}

	@Override
	public boolean runJob() {
		
		boolean out = true;
		
		String serverJarLocation = "http://s3.amazonaws.com/Minecraft.Download/versions/" + this.mcVer + "/minecraft_server." + this.mcVer + ".jar";
		out = Util.download(serverJarLocation, Util.fs_sysPath(Util.getTempDir() + "/vanillaserver-" + this.mcVer + ".jar")); // I hope this works.
		
		return out;
	}
	
}
