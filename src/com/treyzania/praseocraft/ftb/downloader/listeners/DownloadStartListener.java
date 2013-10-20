package com.treyzania.praseocraft.ftb.downloader.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.treyzania.praseocraft.ftb.downloader.PCDL;
import com.treyzania.praseocraft.ftb.downloader.PackFile;
import com.treyzania.praseocraft.ftb.downloader.resouces.Domain;
import com.treyzania.praseocraft.ftb.downloader.resouces.MasterFrame;

public class DownloadStartListener implements ActionListener {

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent arg0) {

		PCDL.log.info("Download sequence started!");
		PCDL.log.info("Preparing to start downloads...");
		
		MasterFrame frame = PCDL.frame;
		
		//System.out.println("FRAME_WIDTH = " + PCDL.frame.getWidth()
		//		+ ", FRAME_HEIGHT = " + PCDL.frame.getHeight());
		
		//int barValue = PCDL.frame.progressBar.getValue();
		//PCDL.frame.progressBar.setValue(barValue + 10);
		
		System.out.println("addr: \"" + frame.addrField.getText() + "\", ver: \"" + frame.verField.getText() + "\"");
		
		PackFile pf = new PackFile(frame.addrField.getText().trim(), frame.verField.getText()); // Gotta trim them because we are working with humans!
		PCDL.packfile = pf;
		
		PCDL.log.info("Pack Location: " + frame.addrField.getText());
		
		pf.buildDocument();
		PCDL.log.info("Document built successfully!");
		
		Domain d = null;
		String dString = "";
		
		if (frame.buttonClient.isSelected()) {
			
			d = Domain.CILENT;
			dString = "client";
			
		} else if (frame.buttonServer.isSelected()) {
			
			d = Domain.SERVER;
			dString = "server";
			
		} // There will always be one selected because the Client button is selected by default.
		PCDL.log.info("Dowload Type: " + d.toString());
		
		PCDL.dlMode = dString;
		pf.readJobs(d);
		PCDL.log.info("Job list created and organized successfully. (Hopefully...)");
		
		frame.progressBar.setMaximum(pf.joblist.getJobsRemaining());
		
		PCDL.log.info("Starting workers...");
		pf.startWorkers();
		
	}

}
