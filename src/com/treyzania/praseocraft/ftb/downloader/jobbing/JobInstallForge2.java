package com.treyzania.praseocraft.ftb.downloader.jobbing;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import com.treyzania.praseocraft.ftb.downloader.Pcdl;

public class JobInstallForge2 extends Job {

	// 4MB buffer
    private final byte[] BUFFER = new byte[4096 * 1024];
	
	private String mcJarName;
	private String forgeZipName;
	private String destZipName;

	public JobInstallForge2(Joblist jl, String mcJarName, String forgeZipLoc,
			String destZipName) {

		super(jl);

		this.mcJarName = mcJarName;
		this.forgeZipName = forgeZipLoc;
		this.destZipName = destZipName;

	}

	@Override
	public boolean runJob() {

		boolean out = true;

		try {
			// read war.zip and write to append.zip
			ZipFile war = new ZipFile(this.mcJarName);
			ZipOutputStream append = new ZipOutputStream(new FileOutputStream(this.destZipName));

			// first, copy contents from existing war
			Enumeration<? extends ZipEntry> entries = war.entries();
			while (entries.hasMoreElements()) {
				ZipEntry e = entries.nextElement();
				System.out.println("copy: " + e.getName());
				append.putNextEntry(e);
				if (!e.isDirectory()) {
					copy(war.getInputStream(e), append);
				}
				append.closeEntry();
			}

			// now append some extra content
			ZipEntry e = new ZipEntry(this.forgeZipName);
			System.out.println("append: " + e.getName());
			append.putNextEntry(e);
			append.write("42\n".getBytes());
			append.closeEntry();

			// close
			war.close();
			append.close();
		} catch (Exception e) {
			Pcdl.log.severe(e.getMessage());
			e.printStackTrace();
			out = false;
		}

		return out;

	}

	private void copy(InputStream input, ZipOutputStream output) {
		
		int bytesRead;
		
        try {
        	
			while ((bytesRead = input.read(BUFFER))!= -1) {
				output.write(BUFFER, 0, bytesRead);
			}
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
	}
}
