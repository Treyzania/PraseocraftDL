package com.treyzania.zanidl.jobbing;

import java.io.IOException;
import java.util.zip.ZipException;

import com.treyzania.zanidl.ZaniDL;
import com.treyzania.zanidl.Util;
import com.treyzania.zanidl.resouces.ErrorProfile;

public class JobUnzip extends Job {

	public String source;
	public String destinaion;
	
	public JobUnzip(Joblist jl, String src, String dest) {
		
		super(jl);
		
		this.source = src;
		this.destinaion = dest;
		
	}
	
	@Override
	public boolean runJob(ErrorProfile errorProfile) {
		
		boolean good = true;
		
		ZaniDL.log.fine("JOBS: Unzipping \'" + this.source + "\' to \'" + this.destinaion + "\'.");
		
		try {
			Util.extract(Util.fs_sysPath(this.source), Util.fs_sysPath(this.destinaion));
		} catch (ZipException e) {
			e.printStackTrace();
			good = false;
		} catch (IOException e) {
			e.printStackTrace();
			good = false;
		}
		
		return good;
		
	}
	
}
