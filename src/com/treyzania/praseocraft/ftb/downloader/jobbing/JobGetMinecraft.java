package com.treyzania.praseocraft.ftb.downloader.jobbing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

import com.treyzania.praseocraft.ftb.downloader.PackFile;
import com.treyzania.praseocraft.ftb.downloader.Util;
import com.treyzania.praseocraft.ftb.downloader.Pcdl;

public class JobGetMinecraft extends Job {

	public PackFile pf;
	
	public JobGetMinecraft(Joblist jl, PackFile pf) {

		super(jl);
		
		this.pf = pf;
		
	}

	@SuppressWarnings({ "resource" })
	@Override
	public boolean runJob() {

		boolean out = true;
		String mcVer = Pcdl.packfile.metadata.access("MCVersion");
		
		Pcdl.log.fine("JOBS: Attempting to copy the vanilla Minecraft Jar file.  (No promises.)");
		
		File src;
		File dest;
		FileInputStream fis;
		FileOutputStream fos;
		
		try {
			
			src = new File(Util.getMinecraftDir() + "/versions/" + mcVer);
			dest = new File(pf.generatePackPath()); // I hope this works too!
			
			fis = new FileInputStream(src);
			fos = new FileOutputStream(dest);
			
			for (int i = 0; i < src.length(); i++) {
				
				fos.write(fis.read()); // Plug one into the other!  So nifty!
				
			}
			
		} catch (Exception e) {
			Pcdl.log.severe(e.getMessage());
			out = false;
		} finally {
			fis = null;
			fos = null;
		}
		
		
		return out;

	}

}
