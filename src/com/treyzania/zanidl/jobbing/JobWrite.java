package com.treyzania.zanidl.jobbing;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import nu.xom.Element;

import com.treyzania.zanidl.ZaniDL;
import com.treyzania.zanidl.Util;

public class JobWrite extends Job {

	public Element e;
	
	public JobWrite(Joblist jl, Element ele) {
		
		super(jl);
		
		this.e = ele;
		
	}

	@Override
	public boolean runJob() {
		
		boolean out = true;
		
		String file = Util.getMinecraftDir() + "/" + e.getAttributeValue("file");
		String content = e.getValue();
		
		File f = new File(Util.fs_sysPath(file));
		FileOutputStream fos = null;
		DataOutputStream dos = null;
				
		// Replace bad characters with better ones.
		String newContent = content
				.replace("\n", "")
				.replace("\t", "")
				.replace("\\n", System.lineSeparator())
				.replace("\\t", "\t")
				;
		
		try {
			
			ZaniDL.log.finer("File exsistence check: {" + f + "}: " + f.exists());
			
			// Check to see if the file needs to be created.
			if (!f.exists()) {
				f.getParentFile().mkdirs();
				f.createNewFile();
			}
			
			// Instantiate the output streams.
			fos = new FileOutputStream(f);
			dos = new DataOutputStream(fos);
			
			// Write the content.
			dos.writeBytes(newContent);
			dos.flush();
			
			// Close them for closure.
			dos.close();
			fos.close();
			
		} catch (Exception e) {
			
			ZaniDL.log.warning(e.getMessage());
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
