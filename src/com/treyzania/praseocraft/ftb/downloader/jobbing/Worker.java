package com.treyzania.praseocraft.ftb.downloader.jobbing;

import com.treyzania.praseocraft.ftb.downloader.PCDL;

public class Worker implements Runnable {

	public final String name;
	public Joblist list;
	public Thread jobExecutor;

	public Worker(String name, Joblist jl) {
		
		this.name = name;
		jobExecutor = new Thread("WT_Thread-" + this.name);
		
		PCDL.log.finer("WORKER \'" + this.name + "\'" + "CREATED!");
		
	}
	
	@Override
	public void run() {
		
		PCDL.log.fine("WORKER \'" + this.name + "\'" + "STARTED!");
		
		int failures = 0;
		
		while (!list.isEmpty()) {
			
			Job j = list.dequeueJob();
			PCDL.log.finer("WORKER \'" + this.name + "\' EXECUTING JOB: \'" + j.toString() + "\'");
			boolean success = j.runJob();
			
			if (!success) failures++;
			
		}
		
		PCDL.log.fine("WORKER \'" + this.name + "\'" + "FINISHED!  FAIL COUNT: " + failures + " FAILURES");
		
	}
	
}
