package com.treyzania.zanidl.resouces;

import java.util.ArrayList;
import java.util.HashMap;

import com.treyzania.zanidl.Domain;
import com.treyzania.zanidl.ZaniDL;
import com.treyzania.zanidl.jobbing.Job;
import com.treyzania.zanidl.jobbing.Joblist;

public class Worker implements Runnable {

	public final String name;
	public final Domain dom;
	public Joblist list;
	public Thread jobExecutor;
	
	public boolean isFinished = false;
	
	public HashMap<Job, ErrorProfile> errors;
	
	public Worker(String name, Joblist jl, Domain domainOfOperation) {
		
		this.name = name;
		this.list = jl;
		this.jobExecutor = new Thread(this, "WorkerThread-" + this.name);
		this.dom = domainOfOperation;
		
		ZaniDL.log.finer("Worker \'" + this.name + "\'" + " created!");
		
	}
	
	@Override
	public void run() {
		
		HashMap<Job, ErrorProfile> fails = new HashMap<Job, ErrorProfile>();
		
		ZaniDL.log.info("Worker \'" + this.name + "\'" + " started!");
		
		int failures = 0;
		
		while (!list.isEmpty()) {
			
			Job j = list.dequeueJob();
			
			if (Domain.Calc.isCompatible(this.dom, j.getDomain())) {
				
				ErrorProfile ep = new ErrorProfile();
				
				boolean success = j.runJob(ep);
				
				synchronized (ZaniDL.frame.progressBar) {
					
					int oldValue = ZaniDL.frame.progressBar.getValue();
					int newValue = oldValue + 1;
					
					ZaniDL.frame.progressBar.setValue(newValue);
					
				}
				
				if (!success) {
					fails.put(j, ep);
					failures++;
				}
				
			}
			
		}
		
		// Build a failure profile.
		StringBuilder sb = new StringBuilder();
		sb.append("Jobs failed: {");
		for (Job j : fails.keySet()) { sb.append(j.getClass().getSimpleName() + ", "); } // Meh, a one-liner.
		sb.append("}.");
		
		// And print out the counters and such.
		ZaniDL.log.info("Worker \'" + this.name + "\'" + " finshed!  Failure count: " + failures + " failure(s).");
		if (failures > 0) ZaniDL.log.info(sb.toString());
		for (int i = 0; i < fails.size(); i++) {
			
			Job j = (Job) fails.keySet().toArray()[i];
			ErrorProfile ep = fails.get(j);
			
			if (ep.errors.size() > 0) {
				
				ZaniDL.log.fine("Job " + j.getClass().getSimpleName() + " failed for:");

				for (String reason : ep.errors) {
					ZaniDL.log.fine("\t" + reason);
				} 
				
				ZaniDL.log.fine("\n"); // Spacing.
				
			}
			
		}
		
		this.errors = fails;
		isFinished = true;
		
	}
	
}
