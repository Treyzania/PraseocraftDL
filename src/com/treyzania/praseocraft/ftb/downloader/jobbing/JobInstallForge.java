package com.treyzania.praseocraft.ftb.downloader.jobbing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.treyzania.praseocraft.ftb.downloader.Util;
import com.treyzania.praseocraft.ftb.downloader.Pcdl;

public class JobInstallForge extends Job {

	private static final byte[] BUFFER = new byte[4096 * 1024]; // 4 KB buffer.

	public String mcJarName;
	public String forgeZipName;
	public String destZipName;
	
	public JobInstallForge(Joblist jl, String mcJarName, String forgeZipLoc, String destZipName) {
		
		super(jl);
		
		this.mcJarName = mcJarName;
		this.forgeZipName = forgeZipLoc;
		this.destZipName = destZipName;
		
	}

	/**
	 * No more stealing!
	 */
	@SuppressWarnings({ "unused", "resource" })
	@Override
	public boolean runJob() {

		boolean out = true;
		
		Pcdl.log.fine("JOBS: Attempting to install MCForge.");
		
		// For these 3 things here, I was reeeeealy tire when I wrote them, and I don't know hoe they work, so I'm just messing with things until it works.
		String forgeJarZipThingy = this.forgeZipName;
		String mcJar = this.mcJarName;
		String moddedJar = this.destZipName;
		
		Pcdl.log.finer("Forge Zip: " + forgeJarZipThingy);
		Pcdl.log.finer("Original MC Jar: " + mcJar);
		Pcdl.log.finer("Modded Jar: " + moddedJar);
		
		
		File tempModdedJar = new File(Util.fs_sysPath(moddedJar));
		if (!tempModdedJar.exists()) {
			tempModdedJar.getParentFile().mkdirs();
			try {
				tempModdedJar.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			
			File src = new File(Util.fs_sysPath(mcJar)); // The MC Jar.
			File mod = new File(Util.fs_sysPath(forgeJarZipThingy)); // The Forge Jar.
			File fnl = new File(Util.fs_sysPath(moddedJar)); // The final, runnable Forge MC.  Also, this var is a.k.a. "Final".
			
			ZipInputStream zissrc = new ZipInputStream(new FileInputStream(src));
			ZipInputStream zismod = new ZipInputStream(new FileInputStream(mod));
			ZipOutputStream zisfnl = new ZipOutputStream(new FileOutputStream(fnl));
			
			while (zissrc.available() != 0) {
				
				zisfnl.write(zissrc.read());
				
			}
			
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			Pcdl.log.severe(e.getMessage());
			out = false;
			
		}
		
		return out;
		
	}
	
}
