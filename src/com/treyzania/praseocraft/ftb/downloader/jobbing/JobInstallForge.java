package com.treyzania.praseocraft.ftb.downloader.jobbing;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.treyzania.praseocraft.ftb.downloader.Util;
import com.treyzania.praseocraft.ftb.downloader.Pcdl;

public class JobInstallForge extends Job {

	@SuppressWarnings("unused")
	private static final byte[] BUFFER = new byte[4096 * 1024]; // 4 KB buffer.

	public String mcJarName;
	public String forgeZipName;
	public String destZipName;

	public JobInstallForge(Joblist jl, String mcJarName, String forgeZipLoc, String destZipName) {

		super(jl);

		this.mcJarName = mcJarName;
		this.forgeZipName = forgeZipLoc;
		this.destZipName = destZipName;

	}

	/**
	 * No more stealing!
	 */
	@SuppressWarnings({ "unused", "resource" })
	@Override
	public boolean runJob() {

		boolean out = true;

		Pcdl.log.fine("JOBS: Attempting to install MCForge.");

		// For these 3 things here, I was reeeeealy tire when I wrote them, and
		// I don't know hoe they work, so I'm just messing with things until it
		// works.
		String forgeJarZipThingy = this.forgeZipName;
		String mcJar = this.mcJarName;
		String moddedJar = this.destZipName;

		Pcdl.log.finer("Forge Zip: " + forgeJarZipThingy);
		Pcdl.log.finer("Original MC Jar: " + mcJar);
		Pcdl.log.finer("Modded Jar: " + moddedJar);

		File tempModdedJar = new File(Util.fs_sysPath(moddedJar));
		if (!tempModdedJar.exists()) {
			tempModdedJar.getParentFile().mkdirs();
			try {
				tempModdedJar.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {

			// The MC Jar.
			File src = new File(Util.fs_sysPath(mcJar));

			// The Forge Jar.
			File mod = new File(Util.fs_sysPath(forgeJarZipThingy));

			// The final, runnable Forge MC. Also, this var is a.k.a. "Final".
			File fnl = new File(Util.fs_sysPath(moddedJar));

			ZipInputStream zissrc = new ZipInputStream(new FileInputStream(src));
			ZipInputStream zismod = new ZipInputStream(new FileInputStream(mod));
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(fnl));
			ZipEntry ze = null;

			// Source zip copyage.
			this.copyAndStrip(src, fnl);

			// Forge zip copyage.
			this.copyAndStrip(mod, fnl);

			zissrc = null;
			zismod = null;
			zos = null;
			ze = null;

		} catch (Exception e) {

			e.printStackTrace();

			Pcdl.log.severe(e.getMessage());
			out = false;

		}

		return out;

	}

	@SuppressWarnings("unused")
	private int[] readFromZE(ZipInputStream zis, ZipEntry ze) {

		System.out.println("ZipEntry: " + ze.toString() + ", size: "
				+ ze.getSize());
		int[] ba = new int[(int) ze.getSize()];

		try {

			int tries = 0;

			while (zis.available() != 0) {

				ba[tries] = zis.read();

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return ba;

	}

	void substitute(ZipInputStream zis, ZipOutputStream zos) {

		try {

			for (ZipEntry ze = zis.getNextEntry(); ze != null; ze = zis
					.getNextEntry()) {

				if (true) {

					zos.putNextEntry(ze);
					byte[] buffer = new byte[1024];

					System.out.println("copying: " + ze.getName());

					for (int read = zis.read(buffer); read != -1; read = zis
							.read(buffer)) {
						zos.write(buffer, 0, read);
					}

					zos.closeEntry();

				}

			}

			zos.close();
			zis.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	private void copyAndStrip(File sourceJar, File targetJar) throws IOException {
		
		ZipFile in = new ZipFile(sourceJar);
		ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(targetJar)));

		for (ZipEntry e : Collections.list(in.entries())) {
			
			if (e.isDirectory()) {
				
				out.putNextEntry(e);
				
			} else if (!e.getName().startsWith("META-INF")) {
				
				ZipEntry n = new ZipEntry(e.getName());
				n.setTime(e.getTime());
				out.putNextEntry(n);
				out.write(readEntry(in, e));
				
			}
			
		}

		in.close();
		out.close();
		
	}

	private static byte[] readEntry(ZipFile inFile, ZipEntry entry)	throws IOException {
		return readFully(inFile.getInputStream(entry));
	}

	private static byte[] readFully(InputStream stream) throws IOException {
		
		byte[] data = new byte[4096];
		ByteArrayOutputStream entryBuffer = new ByteArrayOutputStream();
		int len;
		
		do {
			
			len = stream.read(data);
			
			if (len > 0) {
				entryBuffer.write(data, 0, len);
			}
			
		} while (len != -1);
		
		return entryBuffer.toByteArray();
		
	}

}
