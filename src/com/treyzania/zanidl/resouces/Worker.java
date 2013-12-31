package com.treyzania.zanidl.resouces;

import com.treyzania.zanidl.Domain;
import com.treyzania.zanidl.ZaniDL;
import com.treyzania.zanidl.jobbing.Job;
import com.treyzania.zanidl.jobbing.Joblist;

public class Worker implements Runnable {
	
	public static final String[] WORKER_NAMES = {"Alpha", "Bravo", "Charlie", "Delta", "Echo", "Foxtrot", "Golf", "Hotel"};

	public final String name;
	public final Domain dom;
	public Joblist list;
	public Thread jobExecutor;
	
	public boolean isFinished = false;
	
	public Worker(String name, Joblist jl, Domain domainOfOperation) {
		
		this.name = name;
		this.list = jl;
		this.jobExecutor = new Thread(this, "WorkerThread-" + this.name);
		this.dom = domainOfOperation;
		
		ZaniDL.log.finer("Worker \'" + this.name + "\'" + " created!");
		
	}
	
	@Override
	public void run() {
		
		ZaniDL.log.info("Worker \'" + this.name + "\'" + " started!");
		
		int failures = 0;
		
		while (!list.isEmpty()) {
			
			Job j = list.dequeueJob();
			
			if (Domain.Calc.isCompatible(this.dom, j.getDomain())) {
				
				boolean success = j.runJob();
				
				synchronized (ZaniDL.masFrame.progressBar) {
					
					int oldValue = ZaniDL.masFrame.progressBar.getValue();
					int newValue = oldValue + 1;
					
					ZaniDL.masFrame.progressBar.setValue(newValue);
					
				}
				
				if (!success) {
					failures++;
				}
				
			}
			
		}
		
		// Build a failure profile.
		StringBuilder sb = new StringBuilder();
		sb.append("Jobs failed: {");
		//for (Job j : fails.keySet()) { sb.append(j.getClass().getSimpleName() + ", "); } // Meh, a one-liner.
		sb.append("}.");
		
		// And print out the counters and such.
		ZaniDL.log.info("Worker \'" + this.name + "\'" + " finshed!  Failure count: " + failures + " failure(s).");
		
		isFinished = true;
		
	}
	
}
