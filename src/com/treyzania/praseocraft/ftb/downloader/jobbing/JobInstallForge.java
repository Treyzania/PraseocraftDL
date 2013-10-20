package com.treyzania.praseocraft.ftb.downloader.jobbing;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import com.treyzania.praseocraft.ftb.downloader.PCDL;

public class JobInstallForge extends Job {

	private static final byte[] BUFFER = new byte[4096 * 1024]; // 4 KB buffer.

	public String mcVerName;
	public String forgeZipName;
	public String destZipName;
	
	public JobInstallForge(Joblist jl, String mcVerName, String forgeZipLoc, String destZipName) {
		
		super(jl);
		
	}

	/**
	 * I stole this from
	 * "http://stackoverflow.com/questions/2223434/appending-files-to-a-zip-file-with-java".
	 * 
	 */
	@Override
	public boolean runJob() {

		boolean out = true;
		
		String forgeJarZipThingy = "";
		String mcJar = PCDL.getMinecraftDir() + "/versions/" + mcVerName + "/" + mcVerName + ".jar";
		String moddedJar = "";
		
		try {
			
			// read war.zip and write to append.zip
			ZipFile src = new ZipFile(mcJar);
			ZipOutputStream append = new ZipOutputStream(new FileOutputStream(forgeJarZipThingy));
			
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
			ZipEntry e = new ZipEntry(moddedJar);
			System.out.println("append: " + e.getName());
			append.putNextEntry(e);
			append.write("42\n".getBytes());
			append.closeEntry();
			
			// close
			src.close();
			append.close();
			
		} catch (Exception e) {
			
			PCDL.log.severe(e.getMessage());
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
