package com.treyzania.zanidl;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.treyzania.zanidl.resouces.ConsoleFrame;
import com.treyzania.zanidl.resouces.ConsoleGuiHandler;
import com.treyzania.zanidl.resouces.ConsolePanel;
import com.treyzania.zanidl.resouces.LogFormatter;
import com.treyzania.zanidl.resouces.MasterFrame;

public class ZaniDL {
	
	public static final String VERSION = "v1.2.00";
	public static MasterFrame masFrame;
	public static ConsoleFrame conFrame;
	public static PackFile packfile;
	
	public static Logger log = Logger.getLogger("ZiDL");
	public static Handler consoleHandler;
	public static Handler fileHandler;
	public static Handler guiConsoleHandler;
	
	public static boolean makeWindow = true; // Why do I still have this here?
	
	@Deprecated
	public static String dlMode = "";
	
	public static void main(String[] args) {
		
		setupLogs();
		logTest();
		
		spewArgs(args);
		
		log.info("Thank you for using ZaniDl FTB Pack Installer!");
		log.fine("Installer starting up...");
		
		MasterFrame.laf();
		
		masFrame = new MasterFrame(makeWindow);
		masFrame.setTitle("ZaniDL");
		
		conFrame = new ConsoleFrame();
		conFrame.setTitle("Console");
		
		masFrame.toFront();
		
		log.fine("\t...done!"); // Not technically done, yet.
		
		addConsoleLog();
		
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
		
		try {
			fileHandler = new FileHandler(Util.fs_sysPath(Util.getTempDir() + "/logs/pcdl_log-" + Long.toString(System.currentTimeMillis()) + ".log"));
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
		
		log.setLevel(Level.FINER);
		consoleHandler.setLevel(Level.ALL);
		fileHandler.setLevel(Level.ALL);
		
		consoleHandler.setFormatter(new LogFormatter());
		fileHandler.setFormatter(new LogFormatter());
		
		log.addHandler(consoleHandler);
		log.addHandler(fileHandler);
		
	}
	
	private static void addConsoleLog() {
		
		guiConsoleHandler = new ConsoleGuiHandler((ConsolePanel) conFrame.console);
		guiConsoleHandler.setLevel(Level.ALL);
		guiConsoleHandler.setFormatter(new LogFormatter());
		
		log.addHandler(guiConsoleHandler);
		
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
