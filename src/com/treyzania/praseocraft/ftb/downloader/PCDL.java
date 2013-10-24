package com.treyzania.praseocraft.ftb.downloader;

import java.io.File;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.treyzania.praseocraft.ftb.downloader.resouces.LogFormatter;
import com.treyzania.praseocraft.ftb.downloader.resouces.MasterFrame;

public class Pcdl {
	
	public static final String VERSION = "v0.1";
	public static final String thisPath = Pcdl.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	public static MasterFrame frame;
	public static PackFile packfile;
	
	public static String packFolder = "Praseocraft_FTB";
	//public static String address = "test.xml";
	
	public static File tmpDir;
	public static File packDir;
	
	public static Logger log = Logger.getLogger("PCDL");
	public static Handler consoleHandler;;
	public static Handler fileHandler;
	
	@Deprecated
	public static String dlMode = "";
	
	public static void main(String[] args) {
		
		tmpDir = new File("tmp");
		packDir = new File(packFolder);
		tmpDir.mkdir();
		
		log.setUseParentHandlers(false); // Wow.  This was extremely important that fixed a HUUUUUGE problem.
		
		//NotificationFrame np = new NotificationFrame("This is a secret message.");
		//np.waitForExit();
		
		consoleHandler = new ConsoleHandler();
		try { fileHandler = new FileHandler("tmp/pcdl-log_" + Long.toString(System.currentTimeMillis()) + ".log");
		} catch (SecurityException | IOException e) {}
		log.setLevel(Level.ALL);
		consoleHandler.setLevel(Level.ALL);
		fileHandler.setLevel(Level.ALL);
		consoleHandler.setFormatter(new LogFormatter());
		fileHandler.setFormatter(new LogFormatter());
		log.addHandler(consoleHandler);
		log.addHandler(fileHandler);
		
		String logTestPrefix = "{LOGTEST}";
		log.info(logTestPrefix + "Beginning log test...");
		log.severe(logTestPrefix + "Severe");
		log.warning(logTestPrefix + "Warning");
		log.info(logTestPrefix + "Info");
		log.fine(logTestPrefix + "Fine");
		log.finer(logTestPrefix + "Finer");
		log.finest(logTestPrefix + "Finest");
		log.info(logTestPrefix + "Log test finished.");
		
		log.finest(thisPath);
		log.info("Thank you for using Treyzania's Prasocraft FTB Pack Installer!");
		log.fine("Installer starting up...");
		
		LibLoader.classloadJar();
		
		MasterFrame.laf();
		
		frame = new MasterFrame();
		frame.setTitle("Praseocraft FTB Pack Downloader");
		
		log.fine("\t...done!");
		
	}
	
	// Do I need these two methods?
	public static String fs_genericPath(String path) {
		
		return path.replace('\\', '/');
		
	}

	public static String fs_sysPath(String path) {
		
		String os = System.getProperty("os.name");
		
		String adj = path;
		
		if (os.toLowerCase().contains("windows")) {
			adj.replace('/', '\\');
		}
		
		return adj;
		
	}

	public static String getMinecraftDir() {
		
		String dir = "C:/minecraft/";
		String os = System.getProperty("os.name").toLowerCase();
		
		if (os.contains("windows")) {
			dir = "%appdata%/.minecraft";
		} else if (os.contains("linux") || os.contains("debian") || os.contains("ubuntu")) { // I hope this is good enough
			dir = "~/.minecraft";
		} else if (os.contains("osx")) {
			dir = "~/Library/Application Support/minecraft";
		}
		
		return dir;
		
	}
	
	// Not safe to use yet!  Don't depend on this!
	public static String getPCDLDir() {
		
		String dir = "";
		String os = System.getProperty("os.name").toLowerCase();
		
		if (os.contains("windows")) {
			dir = "%appdata%/.pcdlmc";
		} else if (os.contains("linux") || os.contains("debian") || os.contains("ubuntu")) { // I hope this is good enough
			dir = "~/.pcdlmc";
		} else if (os.contains("osx")) {
			dir = "~/Library/Application Support/pcdlmc";
		}
		
		return dir;
		
	}
	
	// Technically not safe yet either!
	public static String getTempDir() {
		
		return (getPCDLDir() + "/tmp");
		
	}
	
}
