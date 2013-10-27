package com.treyzania.zanidl.jobbing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import com.treyzania.zanidl.PackFile;
import com.treyzania.zanidl.ZaniDl;
import com.treyzania.zanidl.Util;

public class JobGetMinecraft extends Job {

	public PackFile pf;
	public String to;

	public JobGetMinecraft(Joblist jl, PackFile pf, String to) {

		super(jl);

		this.pf = pf;
		this.to = to;

	}

	@SuppressWarnings({ "resource" })
	@Override
	public boolean runJob() {

		boolean out = true;
		
		String mcVer = pf.getMCVersion();

		ZaniDl.log.fine("JOBS: Attempting to copy the vanilla Minecraft Jar file.  (No promises.)");
		
		File src;
		File dest;
		FileInputStream fis;
		FileOutputStream fos;

		try {

			src = new File(Util.getMinecraftDir() + "/versions/" + mcVer + "/" + mcVer + ".jar");
			dest = new File(to);
			
			ZaniDl.log.fine("Data movement: " + src.getPath() + " -> " + dest.getPath());
			
			if (!dest.exists()) {
				dest.getParentFile().mkdirs();
				dest.createNewFile();
			}

			fis = new FileInputStream(src);
			fos = new FileOutputStream(dest);

			FileChannel sfc = fis.getChannel();
			FileChannel dfc = fos.getChannel();

			try {
				sfc.transferTo(0, sfc.size(), dfc);
			} catch (IOException e) {
				throw e;
			} finally {
				if (sfc != null) sfc.close();
				if (dfc != null) dfc.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
			ZaniDl.log.severe(e.getMessage());
			out = false;
		} finally {
			fis = null;
			fos = null;
		}

		return out;

	}

}
