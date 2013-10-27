package com.treyzania.zanidl;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.treyzania.zanidl.resouces.LogFormatter;
import com.treyzania.zanidl.resouces.MasterFrame;

public class ZaniDL {
	
	public static final String VERSION = "v1.01";
	public static MasterFrame frame;
	public static PackFile packfile;
	
	public static Logger log = Logger.getLogger("ZiDL");
	public static Handler consoleHandler;;
	public static Handler fileHandler;
	
	public static boolean makeWindow = true;
	
	@Deprecated
	public static String dlMode = "";
	
	public static void main(String[] args) {
		
		setupLogs();
		logTest();
		
		spewArgs(args);
		
		//boolean isPreset = checkForPresets(args);
		
		log.info("Thank you for using ZaniDl FTB Pack Installer!");
		log.fine("Installer starting up...");
		
		MasterFrame.laf();
		
		frame = new MasterFrame(makeWindow);
		frame.setTitle("ZaniDL");
		
		log.fine("\t...done!");
		
	}
	
	@SuppressWarnings({ "unused", "resource" })
	public static boolean checkForPresets(String[] args) {
		
		boolean out = false;
		
		if (args.length > 0) {
			
			if (args[0] == "--install" || args[0] == "-I") { // Quick install.
				
				out = true;
				makeWindow = false;
				
				String xmlLoc = args[1];
				String _d = args[2];
				String packVer = args[3];
				Domain domain = Domain.Calc.parseDomain(_d);
				Scanner in = new Scanner(System.in);
				
				log.info("Hey!  Looks like you want to install the pack right away!");
				log.info("\tXML Location: " + xmlLoc);
				log.info("\tMode of Installation: " + _d);
				log.info("Is this right? [y|n]");
				String right = in.next().toLowerCase();
				
				if (right == "y") {
					
					packfile = new PackFile(xmlLoc, packVer);
					
				}
				
			} else { // Print usage.
				
				System.out.println("Usage: java -jar <this_jar>.jar [params]");
				System.out.println("\t--install, -I");
				System.out.println("\t\tInstalls immediately."
						+ "\n\t\tPARAMS: <XML_location> <[client|server]> <pack_version>");
				
			}
			
		}
		
		return out;
		
	}
	
	private static void spewArgs(String[] args) {
		
		for (String arg : args) {
			
			log.fine("Argument: " + arg);
			
		}
		
	}
	
	private static void setupLogs() {
		
		log.setUseParentHandlers(false); // Wow.  This was extremely important that fixed a HUUUUUGE problem.
		
		File logsfolder = new File(Util.fs_sysPath(Util.getTempDir() + "/logs/"));
		if (!logsfolder.exists()) {
			logsfolder.mkdirs();
		}
		
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
	
	public static void dumpSystemProperties() {
		
		Properties p = System.getProperties();
		Collection<Object> c = p.values();
		for (int i = 0; i < c.size(); i++) {
			
			System.out.println(p.stringPropertyNames().toArray()[i] + ": " + c.toArray()[i]);
			
		}
		
	}
	
}
