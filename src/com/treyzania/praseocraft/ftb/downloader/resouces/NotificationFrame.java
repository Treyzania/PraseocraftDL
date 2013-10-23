package com.treyzania.praseocraft.ftb.downloader.resouces;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.treyzania.praseocraft.ftb.downloader.listeners.CloseButtonPush;

public class NotificationFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4300639063444545737L;
	
	private static final String spaces = "                    ";
	final String msg;
	
	JPanel msgPanel;
	JLabel msgLabel;
	
	JPanel okPanel;
	JButton okButton;

	private boolean exitedByButton;
	
	public NotificationFrame(String msg) {
		
		this.setLayout(new FlowLayout());
		
		this.msg = msg;
		
		this.msgPanel = new JPanel();
		this.msgLabel = new JLabel(this.msg);
		
		this.okPanel = new JPanel();
		this.okButton = new JButton(spaces + "OK!" + spaces);
		
		okButton.addActionListener(new CloseButtonPush(this));
		
		this.setTitle("Notification");
		this.setFocusable(true);
		this.setEnabled(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(300, 150);
		
		msgPanel.add(msgLabel);
		okPanel.add(okButton);
		
		this.add(msgPanel);
		this.add(okPanel);
		
		this.setVisible(true);
		
	}
	
	@Override
	public void dispose() {
		
		this.exitedByButton = true;
		
		try {
			Thread.sleep(20); // Sleep to allow waitForExit() calls to properly return.  I think this is necessary.
		} catch (InterruptedException e) {}
		
		super.dispose();
		
	}
	
	public void waitForExit() {
		
		while (!exitedByButton) {
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {}
			
		}
		
		return;
		
	}
	
}
