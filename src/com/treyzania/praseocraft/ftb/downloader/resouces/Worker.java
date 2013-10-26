package com.treyzania.praseocraft.ftb.downloader.resouces;

import java.util.ArrayList;

import com.treyzania.praseocraft.ftb.downloader.Domain;
import com.treyzania.praseocraft.ftb.downloader.Pcdl;
import com.treyzania.praseocraft.ftb.downloader.jobbing.Job;
import com.treyzania.praseocraft.ftb.downloader.jobbing.Joblist;

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
		
		Pcdl.log.finer("Worker \'" + this.name + "\'" + " created!");
		
	}
	
	@Override
	public void run() {
		
		ArrayList<Job> fails = new ArrayList<Job>();
		
		Pcdl.log.info("Worker \'" + this.name + "\'" + " started!");
		
		int failures = 0;
		
		while (!list.isEmpty()) {
			
			Job j = list.dequeueJob();
			
			if (Domain.Calc.isCompatible(this.dom, j.getDomain())) {
				
				boolean success = j.runJob();
				
				synchronized (Pcdl.frame.progressBar) {
					
					int oldValue = Pcdl.frame.progressBar.getValue();
					int newValue = oldValue + 1;
					
					Pcdl.frame.progressBar.setValue(newValue);
					
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
		Pcdl.log.info("Worker \'" + this.name + "\'" + " finshed!  Failure count: " + failures + " failure(s).");
		if (failures > 0) Pcdl.log.info(sb.toString());
		
		isFinished = true;
		
	}
	
}
