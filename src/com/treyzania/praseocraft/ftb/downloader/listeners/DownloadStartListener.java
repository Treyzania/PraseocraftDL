package com.treyzania.praseocraft.ftb.downloader.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.treyzania.praseocraft.ftb.downloader.PCDL;

public class DownloadStartListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {

		System.out.println("FRAME_WIDTH = " + PCDL.frame.getWidth()
				+ ", FRAME_HEIGHT = " + PCDL.frame.getHeight());
		
		int barValue = PCDL.frame.progressBar.getValue();
		PCDL.frame.progressBar.setValue(barValue + 10);
		
	}

}
