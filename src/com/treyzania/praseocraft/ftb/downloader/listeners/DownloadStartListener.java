package com.treyzania.praseocraft.ftb.downloader.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.treyzania.praseocraft.ftb.downloader.Pcdl;
import com.treyzania.praseocraft.ftb.downloader.PackFile;
import com.treyzania.praseocraft.ftb.downloader.resouces.MasterFrame;

public class DownloadStartListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {

		Pcdl.log.info("Download sequence started!");
		Pcdl.log.info("Preparing to start downloads...");
		
		MasterFrame frame = Pcdl.frame;
		
		String addr = frame.addrField.getText().trim(); // Gotta trim them because we are working with humans!
		String ver = frame.verField.getText().trim();
		
		// Make the pack!
		PackFile pf = new PackFile(addr, ver); 
		Pcdl.packfile = pf;
		
		pf.pfexe.start();
		
	}

}
