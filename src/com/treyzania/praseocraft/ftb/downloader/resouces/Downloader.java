package com.treyzania.praseocraft.ftb.downloader.resouces;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * 
 * "I hope this works..." -Treyzania From
 * "http://stackoverflow.com/questions/921262/how-to-download-and-save-a-file-from-internet-using-java"
 * .
 * 
 * CHANGES:
 * - try/catch.
 * - Use a File object insted of creading the FOS directly from the filename.
 * - File.mkdirs() call.
 * 
 * @author Treyzania
 * 
 */
public class Downloader {

	/**
	 * Only this isn't fully mine.
	 * 
	 * @param address
	 * @param filename
	 */
	@SuppressWarnings("resource")
	public static boolean download(String address, String filename) {
		
		URL website;
		File file;
		ReadableByteChannel rbc;
		FileOutputStream fos;
		
		boolean good = true;
		
		try {
			
			file = new File(filename);
			
			if (!file.exists()) { // Now it works as intended.
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			
			website = new URL(address);
			rbc = Channels.newChannel(website.openStream());
			fos = new FileOutputStream(file);
			
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			
			good = true;
			
		} catch (Exception e) {
			e.printStackTrace();
			good = false;
		}
		
		return good;
		
	}

}
