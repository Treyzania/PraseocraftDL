package com.treyzania.zanidl.jobbing;

import com.treyzania.zanidl.Domain;
import com.treyzania.zanidl.ZaniDL;
import com.treyzania.zanidl.resouces.MetaCollector;

public class Job {

	final long creation;
	public final Joblist pool;
	
	public MetaCollector metadata;
	
	Domain mDomain;
	
	public Job(Joblist jl) {

		this.creation = System.currentTimeMillis();
		this.pool = jl;
		this.mDomain = Domain.GENERIC;
		
		this.metadata = new MetaCollector();
		
	}
	
	public void setDomain(Domain d) {
		this.mDomain = d;
	}
	
	public void setDomain(String d) {
		this.mDomain = Domain.Calc.parseDomain(d);
	}
	
	public Domain getDomain() {
		return this.mDomain;
	}
	
	public boolean runJob() {
		
		// Nothing.
		
		return true;
		
	}
	
	public static void announceSkip(Job j, String msg) {
		
		ZaniDL.log.warning("JOB SKIPPED: \"" + j.toString() + "\" FOR " + msg);
		
	}

}
