package com.treyzania.zanidl.jobbing;

import com.treyzania.zanidl.Domain;
import com.treyzania.zanidl.ZaniDl;
import com.treyzania.zanidl.Util;
import com.treyzania.zanidl.parsing.FileOp;

public class JobDownload extends Job {

	public final FileOp file;
	public boolean isToUnzip = false;
	
	public JobDownload(Joblist jl, FileOp file) {
		
		super(jl);
		
		this.file = file;
		
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean runJob() {
		
		Domain ft = file.type;
		
		boolean doDownload = false;
		boolean good = true;
		
		ZaniDl.log.fine("JOBS: Downloading file \'" + file.src + "\'.");
		
		if (ft == Domain.GENERIC) {
			doDownload = true;
		} else {
			if (ft == Domain.CLIENT && ZaniDl.dlMode == "client") {
				doDownload = true;
			} else if (ft == Domain.SERVER && ZaniDl.dlMode == "server") {
				doDownload = true;
			} else {
				Job.announceSkip(this, "Incorrect group type for download type.");
			}
			
		}
		
		if (doDownload) {
			
			good = Util.download(file.src, Util.fs_sysPath(ZaniDl.packfile.generatePackPath() + "/" + file.loc));
			
		}
		
		return good;
		
	}
	
}
