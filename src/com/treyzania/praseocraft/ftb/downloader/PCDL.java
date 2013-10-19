package com.treyzania.praseocraft.ftb.downloader;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.treyzania.praseocraft.ftb.downloader.resouces.MasterFrame;

public class PCDL {
	
	public static final String VERSION = "v0.1";
	public static final String thisPath = PCDL.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	public static MasterFrame frame;
	public static PackFile packfile;
	
	public static String packFolder = "Praseocraft_FTB";
	//public static String address = "test.xml";
	
	public static Logger log = Logger.getLogger("PCDL");
	public static Handler consoleHandler;;
	public static Handler fileHandler;
	
	@Deprecated
	public static String dlMode = "";
	
	public static void main(String[] args) {
		
		log.setUseParentHandlers(false); // Wow.  This was extremely important that fixed a HUUUUUGE problem.
		
		consoleHandler = new ConsoleHandler();
		try { fileHandler = new FileHandler("pcdl-log.log");
		} catch (SecurityException | IOException e) {}
		consoleHandler.setLevel(Level.FINEST);
		fileHandler.setLevel(Level.FINEST);
		consoleHandler.setFormatter(new LogFormatter());
		fileHandler.setFormatter(new LogFormatter());
		log.addHandler(consoleHandler);
		log.addHandler(fileHandler);
		
		log.finest(thisPath);
		log.info("Thank you for using Treyzania's Prasocraft FTB Pack Installer!");
		log.fine("Installer starting up...");
		
		XomLoader.classloadJar();
		
		MasterFrame.laf();
		
		log.finest("finest");
		log.finer("finer");
		log.fine("fine");
		
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

}
