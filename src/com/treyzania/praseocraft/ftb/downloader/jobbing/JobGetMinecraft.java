package com.treyzania.praseocraft.ftb.downloader.jobbing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
			
			src = new File(Util.getMinecraftDir() + "/versions/" + mcVer + "/" + mcVer + ".jar");
			dest = new File(pf.generatePackPath()); // I hope this works too!
			
			if (!dest.exists()) {
				dest.getParentFile().mkdirs();
				dest.createNewFile();
			}
			
			fis = new FileInputStream(src);
			fos = new FileOutputStream(dest);
			
			int kbInterval = 16;
			
			for (int i = 0; i < src.length(); i++) {
				
				fos.write(fis.read()); // Plug one into the other!  So nifty!
				if (i % (1024 * kbInterval) == 0) {
					Pcdl.log.finer("{JOB:COPYING MINECRAFT JAR}Kilobytes copied: " + ((int) Math.floor( ( i / (1024 * kbInterval) ) * kbInterval )) + "/" + (src.length() / 1024));
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			Pcdl.log.severe(e.getMessage());
			out = false;
		} finally {
			fis = null;
			fos = null;
		}
		
		
		return out;

	}

}
