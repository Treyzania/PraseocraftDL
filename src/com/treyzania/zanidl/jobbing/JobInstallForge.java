package com.treyzania.zanidl.jobbing;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import com.treyzania.zanidl.ZaniDl;

public class JobInstallForge extends Job {

	// 4MB buffer
    private final byte[] BUFFER = new byte[4096 * 1024];
	
	private String mcJarName;
	private String forgeZipName;
	private String destZipName;

	public JobInstallForge(Joblist jl, String mcJarName, String forgeZipLoc,
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
			
			// Make the file before hand, so we don't get errors and such.
			File dz = new File(this.destZipName);
			if (!dz.exists()) {
				dz.getParentFile().mkdirs();
				dz.createNewFile();
			}
			
			// read war.zip and write to append.zip
			ZipFile mcJar = new ZipFile(this.mcJarName);
			ZipOutputStream destZip = new ZipOutputStream(new FileOutputStream(this.destZipName));
			
			int originalsCopied = 0;
			int forgesCopied = 0;
			
			ZipFile forgeZip = new ZipFile(this.forgeZipName);
			ZipEntry[] forgeZes = this.getEntries(forgeZip);
			
			// first, copy contents from existing war
			ZaniDl.log.finer("Copying over Minecraft files...");
			Enumeration<? extends ZipEntry> entries = mcJar.entries();
			while (entries.hasMoreElements()) {
				
				ZipEntry e = entries.nextElement();
				
				if (!e.getName().toLowerCase().contains("META-INF".toLowerCase()) && !this.testDuplicates(e, forgeZes)) {
					
					destZip.putNextEntry(e);
					if (!e.isDirectory()) {
						copy(mcJar.getInputStream(e), destZip);
					}
					destZip.closeEntry();
					
					originalsCopied++;
					
				}
				
			}
			ZaniDl.log.finer("\tDone!");
			
			ZaniDl.log.finer("Copying over MCForge files...");
			for (int i = 0; i < forgeZes.length; i++) {
				
				ZipEntry theZe = forgeZes[i];
				if (theZe != null) {
					
					destZip.putNextEntry(theZe);
					
					InputStream is = forgeZip.getInputStream(theZe);
					for (int j = 0; j < theZe.getSize(); j++) {
						destZip.write(is.read()); // Not the most efficient, but I don't care.
					}
					destZip.closeEntry();
					
					forgesCopied++;
					
				}
				
			}
			ZaniDl.log.finer("\tDone!");
			
			ZaniDl.log.fine("Done installing MCForge.  MC Files Copied: " + originalsCopied + ".  Forge Files Copied: " + forgesCopied + ".");
			
			// close
			mcJar.close();
			destZip.close();
			
		} catch (Exception e) {
			ZaniDl.log.severe(e.getMessage());
			e.printStackTrace();
			out = false;
		}
		
		return out;
		
	}
	
	private boolean testDuplicates(ZipEntry e, ZipEntry[] forgeZes) {
		
		boolean dupe = false; // True if there is some duplicate files.
		
		for (ZipEntry fze : forgeZes) {
			
			if (fze != null && e.getName() == fze.getName()) {
				
				dupe = true;
				
			}
			
		}
		
		return dupe;
		
	}

	private ZipEntry[] getEntries(ZipFile zf) {
		
		ArrayList<ZipEntry> entries = new ArrayList<ZipEntry>();
		Enumeration<? extends ZipEntry> zes = zf.entries();
		
		while (zes.hasMoreElements()) {
			entries.add(zes.nextElement());
		}
		
		ZipEntry[] zs = entries.toArray(new ZipEntry[1024 * 8]); // I hope this is enough.
		return zs;
		
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
