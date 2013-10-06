package com.treyzania.praseocraft.ftb.downloader.jobbing;

public class Job {

	public final long creation;
	public final Joblist pool;

	public Job(Joblist jl) {

		this.creation = System.currentTimeMillis();
		this.pool = jl;

	}
	
	public boolean runJob() {
		
		// Nothing.
		
		return true;
		
	}
	
	public static void announceSkip(Job j, String msg) {
		
		System.out.println("JOB SKIPPED: \"" + j.toString() + "\" FOR " + msg);
		
	}

}
