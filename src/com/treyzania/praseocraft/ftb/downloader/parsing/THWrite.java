package com.treyzania.praseocraft.ftb.downloader.parsing;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import com.treyzania.praseocraft.ftb.downloader.PCDL;

import nu.xom.Element;

public class THWrite extends TagHandler {

	@Override
	public String getHandledTag() {
		
		return "write";
		
	}

	@Override
	public boolean handleTag(PackFile pf, Element element) { // TODO Move this contents to its own Job, then make this method add the Job to the PF.
		
		boolean out = true;
		
		String file = element.getAttributeValue("file");
		String content = element.getValue();
		
		File f = new File(file);
		FileOutputStream fos = null;
		DataOutputStream dos = null;
		
		try {
			
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
