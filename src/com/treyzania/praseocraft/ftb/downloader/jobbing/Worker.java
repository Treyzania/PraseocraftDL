package com.treyzania.praseocraft.ftb.downloader.jobbing;

public class Worker implements Runnable {

	public final String name;
	public Joblist list;
	public Thread jobExecutor;

	public Worker(String name) {
		
		this.name = name;
		
		System.out.println("WORKER \'" + this.name + "\'" + "CREATED!");
		
	}
	
	@Override
	public void run() {
		
		System.out.println("WORKER \'" + this.name + "\'" + "STARTED!");
		
		int failures = 0;
		
		while (!list.isEmpty()) {
			
			Job j = list.dequeueJob();
			System.out.println("WORKER \'" + this.name + "\' EXECUTING JOB: \'" + j.toString() + "\'");
			boolean success = j.runJob();
			
			if (!success) failures++;
			
		}
		
		System.out.println("WORKER \'" + this.name + "\'" + "FINISHED!  FAIL COUNT: " + failures + " FAILURES");
		
	}
	
}
