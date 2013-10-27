package com.treyzania.zanidl.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.treyzania.zanidl.PackFile;
import com.treyzania.zanidl.ZaniDl;
import com.treyzania.zanidl.resouces.MasterFrame;

public class DownloadStartListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {

		ZaniDl.log.info("Download sequence started!");
		ZaniDl.log.info("Preparing to start downloads...");
		
		MasterFrame frame = ZaniDl.frame;
		
		String addr = frame.addrField.getText().trim(); // Gotta trim them because we are working with humans!
		String ver = frame.verField.getText().trim();
		
		// Make the pack!
		PackFile pf = new PackFile(addr, ver); 
		ZaniDl.packfile = pf;
		
		pf.pfexe.start();
		
	}

}
