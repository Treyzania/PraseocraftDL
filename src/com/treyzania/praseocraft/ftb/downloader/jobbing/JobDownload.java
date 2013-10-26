package com.treyzania.praseocraft.ftb.downloader.jobbing;

import com.treyzania.praseocraft.ftb.downloader.Domain;
import com.treyzania.praseocraft.ftb.downloader.Pcdl;
import com.treyzania.praseocraft.ftb.downloader.Util;
import com.treyzania.praseocraft.ftb.downloader.parsing.FileOp;

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
		
		Pcdl.log.fine("JOBS: Downloading file \'" + file.src + "\'.");
		
		if (ft == Domain.GENERIC) {
			doDownload = true;
		} else {
			if (ft == Domain.CLIENT && Pcdl.dlMode == "client") {
				doDownload = true;
			} else if (ft == Domain.SERVER && Pcdl.dlMode == "server") {
				doDownload = true;
			} else {
				Job.announceSkip(this, "Incorrect group type for download type.");
			}
			
		}
		
		if (doDownload) {
			
			good = Util.download(file.src, Util.fs_sysPath(Pcdl.packfile.generatePackPath() + "/" + file.loc));
			
		}
		
		return good;
		
	}
	
}
