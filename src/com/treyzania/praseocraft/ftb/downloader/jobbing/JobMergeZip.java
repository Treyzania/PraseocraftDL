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

public class JobMergeZip extends Job {

	private static final byte[] BUFFER = new byte[4096 * 1024]; // 4 KB buffer.
	
	public JobMergeZip(Joblist jl) {

		super(jl);

	}

	/**
	 * I stole this from "http://stackoverflow.com/questions/2223434/appending-files-to-a-zip-file-with-java".
	 * 
	 */
	@Override
	public boolean runJob() {

		boolean out = true;

		try {

			// read war.zip and write to append.zip
			ZipFile war = new ZipFile("war.zip");
			ZipOutputStream append = new ZipOutputStream(new FileOutputStream("append.zip"));

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
			ZipEntry e = new ZipEntry("answer.txt");
			System.out.println("append: " + e.getName());
			append.putNextEntry(e);
			append.write("42\n".getBytes());
			append.closeEntry();

			// close
			war.close();
			append.close();

		} catch (Exception e) {

			PCDL.log.severe(e.getMessage());
			out = false;

		}

		return out;

	}
	
	/**
	 * I stole this from "http://stackoverflow.com/questions/2223434/appending-files-to-a-zip-file-with-java" also.
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
