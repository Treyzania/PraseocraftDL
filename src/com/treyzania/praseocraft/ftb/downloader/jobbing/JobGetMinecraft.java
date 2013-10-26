package com.treyzania.praseocraft.ftb.downloader.jobbing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

import com.treyzania.praseocraft.ftb.downloader.Util;
import com.treyzania.praseocraft.ftb.downloader.Pcdl;

public class JobGetMinecraft extends Job {

	public JobGetMinecraft(Joblist jl) {

		super(jl);

	}

	@Override
	public boolean runJob() {

		boolean out = true;
		String mcVer = Pcdl.packfile.metadata.access("MCVersion");
		
		Pcdl.log.fine("JOBS: Attempting to copy the vanilla Minecraft Jar file.  (No promises.)");
		
		File src;
		@SuppressWarnings("unused")
		File dest;
		
		try {
			
			src = new File(Util.getMinecraftDir() + "/versions/" + mcVer);
			dest = new File(src.getParent() + "/.pcdlmc/packs/" + Pcdl.packfile.packVer); // I hope this works too!
			
		} catch (Exception e) {
			
			Pcdl.log.severe(e.getMessage());
			out = false;
			
		}
		
		
		return out;

	}

}
