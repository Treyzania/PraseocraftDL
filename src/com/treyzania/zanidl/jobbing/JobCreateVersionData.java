package com.treyzania.zanidl.jobbing;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import com.treyzania.zanidl.PackFile;
import com.treyzania.zanidl.ZaniDL;
import com.treyzania.zanidl.Util;

public class JobCreateVersionData extends Job {
	
	private String forgeJson = "";
	
	private static final int BUFFER_SIZE = 1024 * 10;
	private static final int ZERO = 0;
	private final byte[] dataBuffer = new byte[BUFFER_SIZE];
	
	public PackFile pf;
	
	public JobCreateVersionData(Joblist jl, PackFile pf) {
		
		super(jl);
		
		this.pf = pf;
		
	}
	
	@SuppressWarnings("resource")
	@Override
	public boolean runJob() {
		
		boolean out = true;
		
		URL fileUrl = null;
		try {
			fileUrl = new URL("http://pastebin.com/raw.php?i=866SSjUn");
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		
		String tempJson;
		
		//File fvd = null;
		
		//FileInputStream fis = null;
		
		//System.out.println("Generic version data length: " + fvd.length());
		
		// Begin copy.
		final StringBuilder sb = new StringBuilder();
		
		try {
			
			final BufferedInputStream in = new BufferedInputStream(fileUrl.openStream());
			
			int bytesRead = ZERO;

			while ((bytesRead = in.read(dataBuffer, ZERO, BUFFER_SIZE)) >= ZERO) {
				sb.append(new String(dataBuffer, ZERO, bytesRead));
			}
			
		} catch (UnknownHostException e) {
			;
		} catch (IOException e) {
			;
		}
		// End copy.
		tempJson = sb.toString();
		
		this.forgeJson = tempJson.replace("###PACKID###", pf.generateLauncherVersionName());
		
		ZaniDL.log.finest("Json Data Length: " + forgeJson.length());
		
		PackFile pf = ZaniDL.packfile;
		String abstractPathName = Util.fs_sysPath(Util.getMinecraftDir()
				+ "/versions/" + pf.generateLauncherVersionName() + "/"
				+ pf.generateLauncherVersionName());
		
		File verJson = new File(abstractPathName + ".json");
		FileOutputStream fos = null;
		
		System.out.println(abstractPathName + ".json");
		
		// Make sure that it exists!
		if (!verJson.exists()) {
			verJson.getParentFile().mkdirs();
			try {
				verJson.createNewFile();
			} catch (IOException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		
		try {
			
			fos = new FileOutputStream(verJson);
			
			for (int i = 0; i < this.forgeJson.length(); i++) { // No DataOutputStream doesn't work.
				fos.write(this.forgeJson.charAt(i));
			}
			
		} catch (Exception e) {
			ZaniDL.log.severe("Cannot continue, manual intervention required!  Error message: "
					+ e.getMessage());
			out = false;
		} finally {
			fos = null;
		}
		
		return out;
		
	}
	
}
