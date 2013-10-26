package com.treyzania.praseocraft.ftb.downloader.jobbing;

import com.treyzania.praseocraft.ftb.downloader.Util;
import com.treyzania.praseocraft.ftb.downloader.resouces.Downloader;

public class JobGenerateServerFiles extends Job {

	public String mcVer;
	
	public JobGenerateServerFiles(Joblist jl, String mcVer) {
		
		super(jl);
		
		this.mcVer = mcVer;
		
	}

	@Override
	public boolean runJob() {
		
		boolean out = true;
		
		String serverJarLocation = "http://s3.amazonaws.com/Minecraft.Download/versions/" + this.mcVer + "/minecraft_server." + this.mcVer + ".jar";
		out = Downloader.download(serverJarLocation, Util.fs_sysPath(Util.getTempDir() + "vanillaserver-" + this.mcVer + ".jar")); // I hope this works.
		
		return out;
	}
	
}
