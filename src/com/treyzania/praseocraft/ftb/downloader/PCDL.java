package com.treyzania.praseocraft.ftb.downloader;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.treyzania.praseocraft.ftb.downloader.resouces.LogFormatter;
import com.treyzania.praseocraft.ftb.downloader.resouces.MasterFrame;

public class Pcdl {
	
	public static final String VERSION = "v0.1";
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
		
		setupLogs();
		logTest();
		
		tmpDir = new File("tmp");
		packDir = new File(packFolder);
		tmpDir.mkdir();
		
		//NotificationFrame np = new NotificationFrame("This is a secret message.");
		//np.waitForExit();
		
		Properties p = System.getProperties();
		Collection<Object> c = p.values();
		for (int i = 0; i < c.size(); i++) {
			
			//System.out.println(p.stringPropertyNames().toArray()[i] + ": " + c.toArray()[i]);
			
		}
		
		log.info("Thank you for using Treyzania's Prasocraft FTB Pack Installer!");
		log.fine("Installer starting up...");
		
		LibLoader.classloadJar();
		
		MasterFrame.laf();
		
		frame = new MasterFrame();
		frame.setTitle("Praseocraft FTB Pack Downloader");
		
		log.fine("\t...done!");
		
	}
	
	private static void setupLogs() {
		
		log.setUseParentHandlers(false); // Wow.  This was extremely important that fixed a HUUUUUGE problem.
		
		new File(Util.fs_sysPath(Util.getTempDir() + "/logs/")).getParentFile().mkdirs();
		
		consoleHandler = new ConsoleHandler();
		try { fileHandler = new FileHandler(Util.fs_sysPath(Util.getTempDir() + "/logs/pcdl_log-" + Long.toString(System.currentTimeMillis()) + ".log"));
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
		
		log.setLevel(Level.ALL);
		consoleHandler.setLevel(Level.ALL);
		fileHandler.setLevel(Level.ALL);
		
		consoleHandler.setFormatter(new LogFormatter());
		fileHandler.setFormatter(new LogFormatter());
		
		log.addHandler(consoleHandler);
		log.addHandler(fileHandler);
		
	}
	
	private static void logTest() {
		
		String logTestPrefix = "{LOGTEST}";
		log.info(logTestPrefix + "Beginning log test...");
		log.severe(logTestPrefix + "Severe");
		log.warning(logTestPrefix + "Warning");
		log.info(logTestPrefix + "Info");
		log.fine(logTestPrefix + "Fine");
		log.finer(logTestPrefix + "Finer");
		log.finest(logTestPrefix + "Finest");
		log.info(logTestPrefix + "Log test finished.");
		
	}
	
}
