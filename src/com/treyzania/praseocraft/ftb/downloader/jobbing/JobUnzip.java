package com.treyzania.praseocraft.ftb.downloader.jobbing;

import java.io.IOException;
import java.util.zip.ZipException;

import com.treyzania.praseocraft.ftb.downloader.Pcdl;
import com.treyzania.praseocraft.ftb.downloader.resouces.Unzipper;

public class JobUnzip extends Job {

	public String source;
	public String destinaion;
	
	public JobUnzip(Joblist jl, String src, String dest) {
		
		super(jl);
		
		this.source = src;
		this.destinaion = dest;
		
	}
	
	@Override
	public boolean runJob() {
		
		boolean good = true;
		
		try {
			Unzipper.extract(Pcdl.fs_sysPath(this.source), Pcdl.fs_sysPath(this.destinaion));
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
