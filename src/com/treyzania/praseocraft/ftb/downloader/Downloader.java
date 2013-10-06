package com.treyzania.praseocraft.ftb.downloader;

import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * 
 * "I hope this works..." -Treyzania
 * From "http://stackoverflow.com/questions/921262/how-to-download-and-save-a-file-from-internet-using-java".
 * 
 * CHANGES:
 * - try/catch, etc.
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
	    ReadableByteChannel rbc;
	    FileOutputStream fos;
	    
	    boolean good = true;
	    
	    try {
	    	
	    	website = new URL(address);
	    	rbc = Channels.newChannel(website.openStream());
	    	fos = new FileOutputStream(filename);
	    	
	    	fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
	    	
	    	good = true;
	    	
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	good = false;
	    }
		
	    return good;
	    
	}
	
}
