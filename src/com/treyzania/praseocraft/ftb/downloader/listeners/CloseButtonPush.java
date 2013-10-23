package com.treyzania.praseocraft.ftb.downloader.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.treyzania.praseocraft.ftb.downloader.resouces.NotificationFrame;

public class CloseButtonPush implements ActionListener {

	private NotificationFrame mFrame;
	
	public CloseButtonPush(NotificationFrame frame) {
		
		this.mFrame = frame;
		
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		
		/*
		 * Not perfect, but gets the job done.
		 * It should also let any of the waitForExit() calls to return.
		 */
		
		mFrame.dispose();
		
	}

}
