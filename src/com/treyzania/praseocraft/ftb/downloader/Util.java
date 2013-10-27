package com.treyzania.praseocraft.ftb.downloader;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class Util {
	
	// Do I need these two methods?
	public static String fs_genericPath(String path) {
		
		return path.replace('\\', '/');
		
	}

	public static String fs_sysPath(String path) {
		
		String os = System.getProperty("os.name").toLowerCase();
		
		String adj = path;
		
		if (os.contains("windows")) {
			adj = adj.replace('/', '\\');
		}
		
		return adj;
		
	}

	public static String getMinecraftDir() {
		
		String dir = System.getProperty("user.dir") + "/mc-pcdl/";
		String os = System.getProperty("os.name").toLowerCase();
		String userHome = System.getProperty("user.home");
		
		if (os.contains("windows")) {
			dir = userHome + "/AppData/Roaming/.minecraft";
		} else if (os.contains("linux") || os.contains("debian") || os.contains("ubuntu")) { // I hope this is good enough
			dir = userHome + "/.minecraft";
		} else if (os.contains("osx")) {
			dir = userHome + "/Library/Application Support/minecraft";
		}
		
		return dir;
		
	}
	
	public static String getPCDLDir() {
		
		String dir = "";
		String os = System.getProperty("os.name").toLowerCase();
		String userHome = System.getProperty("user.home");
		
		if (os.contains("windows")) {
			dir = userHome + "/AppData/Roaming/.mc-pcdl";
		} else if (os.contains("linux") || os.contains("debian") || os.contains("ubuntu")) { // I hope this is good enough
			dir = userHome + "/mc-pcdl";
		} else if (os.contains("osx")) {
			dir = userHome + "/mc-pcdl";
		}
		
		return dir;
		
	}
	
	public static String getTempDir() {
		
		return (getPCDLDir() + "/tmp");
		
	}
	
	protected static void testDirs() {
		
		Pcdl.log.finest("WORKING DIR: " + System.getProperty("user.dir"));
		Pcdl.log.finest("MC DIR: " + Util.fs_genericPath(Util.getMinecraftDir()));
		Pcdl.log.finest("TEMP DIR: " + Util.fs_genericPath(Util.getTempDir()));
		Pcdl.log.finest("SYSPATH TEST: " + Util.fs_sysPath(Util.getTempDir() + "/dicks/and/balls/hi.txt"));
		
	}

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
			
			file = new File(fs_sysPath(filename));
			
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
			Pcdl.log.warning(e.getMessage() + " -> " + filename);
			good = false;
		}
		
		return good;
		
	}

	/**
	 * Only this isn't fully mine.
	 * 
	 * @param zipFile
	 * @param outputDir
	 * @throws ZipException
	 * @throws IOException
	 */
	public static void extract(String zipFile, String outputDir) throws ZipException, IOException {
		
	    System.out.println(zipFile);
	    int BUFFER = 2048;
	    File file = new File(zipFile);
	
	    @SuppressWarnings("resource")
		ZipFile zip = new ZipFile(file);
	    String newPath = outputDir;
	
	    new File(newPath).mkdir();
	    Enumeration<? extends ZipEntry> zipFileEntries = zip.entries();
	
	    // Process each entry
	    while (zipFileEntries.hasMoreElements()) {
	    	
	        // grab a zip file entry
	        ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
	        String currentEntry = entry.getName();
	        File destFile = new File(newPath, currentEntry);
	        //destFile = new File(newPath, destFile.getName());
	        File destinationParent = destFile.getParentFile();
	
	        // create the parent directory structure if needed
	        destinationParent.mkdirs();
	
	        if (!entry.isDirectory()) {
	        	
	            BufferedInputStream is = new BufferedInputStream(zip.getInputStream(entry));
	            int currentByte;
	            // establish buffer for writing file
	            byte data[] = new byte[BUFFER];
	
	            // write the current file to disk
	            FileOutputStream fos = new FileOutputStream(destFile);
	            BufferedOutputStream dest = new BufferedOutputStream(fos,
	            BUFFER);
	
	            // read and write until last byte is encountered
	            while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
	                dest.write(data, 0, currentByte);
	            }
	            
	            dest.flush();
	            dest.close();
	            is.close();
	            
	        }
	        
	    }
	    
	}
	
	public static String readWebpage(String addr) {
		
		URL url;
	    InputStream is = null;
	    BufferedReader br;
	    String line = "";
	    
	    StringBuilder builder = new StringBuilder();
	    
	    try {
	    	
	    	url = new URL(addr);
	    	is = url.openStream();  // throws an IOException
	    	br = new BufferedReader(new InputStreamReader(is));
	    	
	        while ((line = br.readLine()) != null) {
	        	builder.append(line);
	        }
	        
	    } catch (MalformedURLException mue) {
	    	mue.printStackTrace();
	    } catch (IOException ioe) {
	    	ioe.printStackTrace();
	    } finally {
	    	
	    	try {
	    		if (is != null) is.close();
	        } catch (IOException ioe) {
	        	// nothing to see here
	        }
	    }
	    
	    return (builder.toString());
	    
	}
	
	public static String removeUTF8BOM(String s) {
		
		String UTF8_BOM = "\uFEFF";
		String ns = "";
		
    	if (s.startsWith(UTF8_BOM)) {
    		ns = s.substring(1);
    	}
    	
    	return ns;
    }
	
}
