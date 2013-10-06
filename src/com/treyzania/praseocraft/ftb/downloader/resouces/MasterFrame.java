package com.treyzania.praseocraft.ftb.downloader.resouces;

import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.treyzania.praseocraft.ftb.downloader.PCDL;
import com.treyzania.praseocraft.ftb.downloader.State;
import com.treyzania.praseocraft.ftb.downloader.listeners.DownloadStartListener;

public class MasterFrame extends JFrame implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6884348438866037613L;

	public Thread cycle;

	public JPanel titles;
	public JPanel version;
	public JPanel radio;
	public JPanel download;
	public JPanel bar;
	public JPanel status;

	public JTextField verField = new JTextField(10);

	public ButtonGroup dlType;
	public JRadioButton buttonClient;
	public JRadioButton buttonServer;
	public JButton dlButton;
	public JProgressBar progressBar;
	
	public JLabel dlStatus;

	public MasterFrame() {

		this.cycle = new Thread("MF-CYCLER");

		this.titles = new DLPanel();
		this.version = new DLPanel();
		this.radio = new DLPanel();
		this.download = new DLPanel();
		this.bar = new DLPanel();
		this.status = new DLPanel();

		titles.add(new JLabel("Praseocraft FTB Pack Installer " + PCDL.VERSION));
		//titles.add(new JLabel("Written by Treyzania"));
		titles.setVisible(true);

		version.add(new JLabel("Pack version:"));
		version.add(verField);
		version.setVisible(true);

		verField.setText("I don\'t work!");
		
		dlType = new ButtonGroup();
		buttonClient = new JRadioButton("Client"); buttonClient.setSelected(true);
		buttonServer = new JRadioButton("Server");
		dlType.add(this.buttonClient);
		dlType.add(this.buttonServer);
		radio.add(this.buttonClient);
		radio.add(this.buttonServer);
		radio.setVisible(true);

		dlButton = new JButton("Download and Install");
		dlButton.addActionListener(new DownloadStartListener());
		download.add(dlButton);
		download.setVisible(true);

		dlStatus = new JLabel("Hello, I am a JLabel.");
		status.add(this.dlStatus);
		status.setVisible(true);

		progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		bar.add(progressBar);
		
		this.setLayout(new FlowLayout());
		//this.setLayout(new CardLayout());
		
		this.add(this.titles);
		this.add(this.version);
		this.add(this.radio);
		this.add(this.download);
		this.add(this.bar);
		this.add(this.status);
		
		this.setBounds(0, 0, 500, 500);
		this.setFocusable(true);
		this.setVisible(true);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.revalidate();
		this.repaint();
		
		cycle.start();
		
	}

	public void updateState(State s) {
		
		dlStatus.setText(s.text);
		
	}
	
	@Override
	public void run() {

		this.revalidate();
		this.repaint();

		// It's an infinite loop!
		while (true) {

			titles.repaint();
			version.repaint();
			radio.repaint();
			download.repaint();
			bar.repaint();
			status.repaint();

			this.repaint();

		}

	}

}
