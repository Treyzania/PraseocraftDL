package com.treyzania.zanidl.jobbing;

import java.util.ArrayList;

public class Joblist {

	public ArrayList<Job> jobs;
	
	public Joblist() {
		
		this.jobs = new ArrayList<Job>();
		
	}
	
	public void addJob(Job job) {
		
		jobs.add(job);
		
	}
	
	public Job dequeueJob() {
		
		Job j = null;
		
		if (jobs.size() > 0) {
			j = jobs.remove(0);
		}
		
		return j;
		
	}
	
	public int getJobsRemaining() {
		
		return this.jobs.size();
		
	}
	
	public boolean isEmpty() {
		
		return (getJobsRemaining() == 0);
		
	}
	
}
