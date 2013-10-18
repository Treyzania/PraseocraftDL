package com.treyzania.praseocraft.ftb.downloader.jobbing;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import nu.xom.Element;

import com.treyzania.praseocraft.ftb.downloader.PCDL;

public class JobWrite extends Job {

	public Element e;
	
	public JobWrite(Joblist jl, Element ele) {
		
		super(jl);
		
		this.e = ele;
		
	}

	@Override
	public boolean runJob() {
		
		boolean out = true;
		
		String file = e.getAttributeValue("file") + PCDL.thisPath;
		String content = e.getValue();
		
		File f = new File(file);
		FileOutputStream fos = null;
		DataOutputStream dos = null;
		
		PCDL.log.info("JOBS: Writing to file \'" + file + "\'.");
		
		try {
			
			// Check to see if the file needs to be created.
			if (!f.exists()) f.createNewFile();
			
			// Instantiate the output streams.
			fos = new FileOutputStream(f);
			dos = new DataOutputStream(fos);
			
			// Write the content.
			dos.writeBytes(content);
			dos.flush();
			
			// Close them for closure.
			dos.close();
			fos.close();
			
		} catch (Exception e) {
			
			PCDL.log.warning(e.getMessage());
			out = false;
			
		} finally {
			
			// Do I have to do this anyways?
			f = null;
			fos = null;
			dos = null;
			
		}
		
		return out;
		
	}
	
}
