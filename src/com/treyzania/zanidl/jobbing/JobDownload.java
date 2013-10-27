package com.treyzania.zanidl.jobbing;

import com.treyzania.zanidl.Domain;
import com.treyzania.zanidl.ZaniDL_;
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
		
		ZaniDL_.log.fine("JOBS: Downloading file \'" + file.src + "\'.");
		
		if (ft == Domain.GENERIC) {
			doDownload = true;
		} else {
			if (ft == Domain.CLIENT && ZaniDL_.dlMode == "client") {
				doDownload = true;
			} else if (ft == Domain.SERVER && ZaniDL_.dlMode == "server") {
				doDownload = true;
			} else {
				Job.announceSkip(this, "Incorrect group type for download type.");
			}
			
		}
		
		if (doDownload) {
			
			good = Util.download(file.src, Util.fs_sysPath(ZaniDL_.packfile.generatePackPath() + "/" + file.loc));
			
		}
		
		return good;
		
	}
	
}
