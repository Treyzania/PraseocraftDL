package com.treyzania.praseocraft.ftb.downloader;

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

}
