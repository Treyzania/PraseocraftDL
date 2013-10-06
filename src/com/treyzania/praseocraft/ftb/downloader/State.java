package com.treyzania.praseocraft.ftb.downloader;

public class State {

	public String text;
	
	public static final State STATRTING = new State("Starting...");
	public static final State IDLE = new State("Idle");
	public static final State DOWNLOADING_XML = new State("Downloading XML resouce sheet...");
	public static final State GENERATING_JOB_LIST = new State("Generating download job list...");
	public static final State DOWNLOADING_RESOURCES = new State("Downloading resources...");
	public static final State COMPILING_RESOUCES = new State("Compiling (installing) resouces...");
	public static final State COMPLETE = new State("Downloads complete!");
	
	State(String name) {
		
		this.text = name;
		
	}
	
}
