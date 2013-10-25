package com.treyzania.praseocraft.ftb.downloader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import com.treyzania.praseocraft.ftb.downloader.resouces.Downloader;

public class LibLoader {

	public static final String xomDirectLink = "http://www.cafeconleche.org/XOM/xom-1.2.10.jar";
	public static final String xomJarName = "xom.jar";
	
	// Do something with this.
	
	public static boolean isDownloaded() {
		
		File xomJarDir = new File(xomJarName);
		return xomJarDir.exists();
		
	}
	
	// Do I need to actually download this file?
	public static void downloadJar() {
		
		Downloader.download(xomDirectLink, xomJarName);
		
	}
	
	@SuppressWarnings({ "unused", "resource" })
	public static void classloadJar() {
		
		URL xomurl = null;
		
		try {
			xomurl = new URL(xomDirectLink);
		} catch (MalformedURLException e) {
			Pcdl.log.severe(e.getMessage());
		}
		
		URLClassLoader urlcl = new URLClassLoader(new URL[] { xomurl });
		
		
	}
	
}
