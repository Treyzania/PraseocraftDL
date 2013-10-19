package com.treyzania.praseocraft.ftb.downloader.jobbing;

import com.treyzania.praseocraft.ftb.downloader.Downloader;
import com.treyzania.praseocraft.ftb.downloader.Domain;
import com.treyzania.praseocraft.ftb.downloader.PCDL;
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
		
		String dest = file.action == "zip" ? "/tmp/" + file.loc : "/" + PCDL.packFolder + "/" + file.loc;
		
		boolean doDownload = false;
		boolean good = true;
		
		if (ft == Domain.GENERIC) {
			doDownload = true;
		} else {
			if (ft == Domain.CILENT && PCDL.dlMode == "client") {
				doDownload = true;
			} else if (ft == Domain.SERVER && PCDL.dlMode == "server") {
				doDownload = true;
			} else {
				Job.announceSkip(this, "Incorrect group type for download type.");
			}
			
		}
		
		if (doDownload) {
			
			good = Downloader.download(file.src, "/" + PCDL.packFolder + "/" + file.loc);
			
		}
		
		if (file.isActionUnzip()) {
			
			pool.addJob(new JobUnzip(pool, dest, "/" + PCDL.packFolder + "/" + file.loc));
			
		}
		
		return good;
		
	}
	
}
