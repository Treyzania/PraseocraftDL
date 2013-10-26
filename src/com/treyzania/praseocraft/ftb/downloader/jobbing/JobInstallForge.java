package com.treyzania.praseocraft.ftb.downloader.jobbing;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
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
	 * I stole some of this from
	 * "http://stackoverflow.com/questions/2223434/appending-files-to-a-zip-file-with-java".
	 * 
	 */
	@Override
	public boolean runJob() {

		boolean out = true;
		
		Pcdl.log.fine("JOBS: Attempting to install MCForge.");
		
		// For these 3 things here, I was reeeeealy tire when I wrote them, and I don't know hoe they work, so I'm just messing with things until it works.
		String forgeJarZipThingy = this.forgeZipName;
		String mcJar = this.mcJarName;
		String moddedJar = Util.getMinecraftDir() + "/versions/" + Pcdl.packfile.generateLauncherVersionName() + "/" + Pcdl.packfile.generateLauncherVersionName() + ".jar";
		
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
			
			// read war.zip and write to append.zip
			ZipFile src = new ZipFile(Util.fs_sysPath(mcJar));
			ZipOutputStream append = new ZipOutputStream(new FileOutputStream(Util.fs_sysPath(forgeJarZipThingy)));
			
			// first, copy contents from existing war
			Enumeration<? extends ZipEntry> entries = src.entries();
			while (entries.hasMoreElements()) {
				
				ZipEntry e = entries.nextElement();
				System.out.println("copy: " + e.getName());
				append.putNextEntry(e);
				
				if (!e.isDirectory()) {
					copy(src.getInputStream(e), append);
				}
				append.closeEntry();
				
			}
			
			// now append some extra content
			//ZipEntry e = new ZipEntry(Util.fs_sysPath(moddedJar));
			//System.out.println("append: " + e.getName());
			//append.putNextEntry(e);
			//append.write("42\n".getBytes());
			//append.closeEntry();
			
			// close
			src.close();
			append.close();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			Pcdl.log.severe(e.getMessage());
			out = false;
			
		}
		
		return out;
		
	}

	/**
	 * I stole this from
	 * "http://stackoverflow.com/questions/2223434/appending-files-to-a-zip-file-with-java"
	 * also.
	 * 
	 */
	private void copy(InputStream input, OutputStream output)
			throws IOException {

		int bytesRead;
		while ((bytesRead = input.read(BUFFER)) != -1) {
			output.write(BUFFER, 0, bytesRead);
		}

	}

}
