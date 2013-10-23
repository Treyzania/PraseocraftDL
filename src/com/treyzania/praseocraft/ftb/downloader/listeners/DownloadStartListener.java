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
		
		//System.out.println("FRAME_WIDTH = " + PCDL.frame.getWidth()
		//		+ ", FRAME_HEIGHT = " + PCDL.frame.getHeight());
		
		//int barValue = PCDL.frame.progressBar.getValue();
		//PCDL.frame.progressBar.setValue(barValue + 10);
		
		System.out.println("addr: \"" + frame.addrField.getText() + "\", ver: \"" + frame.verField.getText() + "\"");
		
		PackFile pf = new PackFile(frame.addrField.getText().trim(), frame.verField.getText().trim()); // Gotta trim them because we are working with humans!
		Pcdl.packfile = pf;
		
		pf.pfexe.start();
		
	}

}
