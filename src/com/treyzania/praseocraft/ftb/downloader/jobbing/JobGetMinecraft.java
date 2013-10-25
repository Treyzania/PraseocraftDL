package com.treyzania.praseocraft.ftb.downloader.jobbing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
		File dest;
		
		try {
			
			src = new File(Util.getMinecraftDir() + "/versions/" + mcVer);
			dest = new File(src.getParent() + "/.pcdlmc/packs/" + Pcdl.packfile.packVer); // I hope this works too!
			
			this.copyDirectory(src, dest);
			
		} catch (Exception e) {
			
			Pcdl.log.severe(e.getMessage());
			out = false;
			
		}
		
		
		return out;

	}

	/**
	 * I stole this from
	 * "http://stackoverflow.com/questions/5368724/how-to-copy-a-folder-and-all-its-subfolders-and-files-into-another-folder".
	 * 
	 * @param sourceLocation
	 * @param targetLocation
	 * @throws IOException 
	 */
	private void copyDirectory(File sourceLocation, File targetLocation) throws IOException {

		if (sourceLocation.isDirectory()) {
			
			if (!targetLocation.exists()) {
				targetLocation.mkdir();
			}

			String[] children = sourceLocation.list();
			for (int i = 0; i < children.length; i++) {
				copyDirectory(new File(sourceLocation, children[i]), new File(
						targetLocation, children[i]));
			}
			
		} else {

			InputStream in = null;
			OutputStream out = null;
			
			try {
				in = new FileInputStream(sourceLocation);
				out = new FileOutputStream(targetLocation);
			} catch (Exception e) {
				Pcdl.log.severe(e.getMessage());
			}

			// Copy the bits from instream to outstream
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				try {
					out.write(buf, 0, len);
				} catch (Exception e) {
					Pcdl.log.severe(e.getMessage());
				}
			}
			
			try {
				in.close();
				out.close();
			} catch (Exception e) {
				Pcdl.log.severe(e.getMessage());
			}
			
		}

	}

}
