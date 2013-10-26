package com.treyzania.praseocraft.ftb.downloader.jobbing;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;

import com.treyzania.praseocraft.ftb.downloader.PackFile;
import com.treyzania.praseocraft.ftb.downloader.Pcdl;
import com.treyzania.praseocraft.ftb.downloader.Util;

public class JobCreateVersionData extends Job {

	private String forgeJson = "";
	
	static {
		
	}
	
	public JobCreateVersionData(Joblist jl, PackFile pf) {
		
		super(jl);
		
		URL fileUrl = JobCreateVersionData.class.getResource("forgeVersionData");  
		File fvd = new File(fileUrl.getPath());
		char[] data = new char[(int) fvd.length()];
		String tempJson;
		
		FileInputStream fis = null;
		
		try {
			
			fis = new FileInputStream(fvd);
			
			for (int i = 0; i < data.length; i++) {
				data[i] = (char) fis.read();
			}
			
		} catch (Exception e) {
			Pcdl.log.severe("Error with Forge handling!  Can not succeed!");
		} finally {
			fis = null;
		}
		
		tempJson = new String(data);
		this.forgeJson = tempJson.replace("###PACKID###", pf.generateVersionName());
		
		
	}

	@SuppressWarnings("resource")
	@Override
	public boolean runJob() {
		
		boolean out = true;
		
		PackFile pf = Pcdl.packfile;
		String abstractPathName = Util.fs_sysPath(Util.getMinecraftDir() + "/verisons/" + pf.generateVersionName() + "/" + pf.generateVersionName());
		
		File verJson = new File(abstractPathName + ".json");
		DataOutputStream dos = null;
		
		try {
			
			dos = new DataOutputStream(new FileOutputStream(verJson));			
			dos.writeUTF(forgeJson);
			
		} catch (Exception e) {
			Pcdl.log.severe("Cannot continue, manual intervention required!  Error message: " + e.getMessage());
			out = false;
		} finally {
			dos = null;
		}
		
		return out;
		
	}
	
}
