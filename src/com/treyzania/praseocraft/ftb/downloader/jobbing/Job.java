package com.treyzania.praseocraft.ftb.downloader.jobbing;

import com.treyzania.praseocraft.ftb.downloader.Pcdl;
import com.treyzania.praseocraft.ftb.downloader.resouces.MetaCollector;

public class Job {

	public final long creation;
	public final Joblist pool;
	
	public MetaCollector metadata;
	
	public Job(Joblist jl) {

		this.creation = System.currentTimeMillis();
		this.pool = jl;
		
		this.metadata = new MetaCollector();
		
	}
	
	public boolean runJob() {
		
		// Nothing.
		
		return true;
		
	}
	
	public static void announceSkip(Job j, String msg) {
		
		Pcdl.log.warning("JOB SKIPPED: \"" + j.toString() + "\" FOR " + msg);
		
	}

}
