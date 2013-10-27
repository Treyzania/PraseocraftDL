package com.treyzania.zanidl.resouces;

import java.util.ArrayList;

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
	
	public Worker(String name, Joblist jl, Domain domainOfOperation) {
		
		this.name = name;
		this.list = jl;
		this.jobExecutor = new Thread(this, "WorkerThread-" + this.name);
		this.dom = domainOfOperation;
		
		ZaniDL.log.finer("Worker \'" + this.name + "\'" + " created!");
		
	}
	
	@Override
	public void run() {
		
		ArrayList<Job> fails = new ArrayList<Job>();
		
		ZaniDL.log.info("Worker \'" + this.name + "\'" + " started!");
		
		int failures = 0;
		
		while (!list.isEmpty()) {
			
			Job j = list.dequeueJob();
			
			if (Domain.Calc.isCompatible(this.dom, j.getDomain())) {
				
				boolean success = j.runJob();
				
				synchronized (ZaniDL.frame.progressBar) {
					
					int oldValue = ZaniDL.frame.progressBar.getValue();
					int newValue = oldValue + 1;
					
					ZaniDL.frame.progressBar.setValue(newValue);
					
				}
				
				if (!success) {
					fails.add(j);
					failures++;
				}
				
			}
			
		}
		
		// Build a failure profile.
		StringBuilder sb = new StringBuilder();
		sb.append("Jobs failed: {");
		for (Job j : fails) { sb.append(j.getClass().getSimpleName() + ", "); } // Meh, a one-liner.
		sb.append("}.");
		
		// And print out the counters and such.
		ZaniDL.log.info("Worker \'" + this.name + "\'" + " finshed!  Failure count: " + failures + " failure(s).");
		if (failures > 0) ZaniDL.log.info(sb.toString());
		
		isFinished = true;
		
	}
	
}
