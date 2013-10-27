package com.treyzania.zanidl.resouces;

import java.awt.*;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.treyzania.zanidl.ZaniDL;
import com.treyzania.zanidl.State;
import com.treyzania.zanidl.listeners.DownloadStartListener;

public class MasterFrame extends JFrame implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6884348438866037613L;
	
	public static final String noErrorsText = "[NO CURRENT MINOR ERRORS]";
	
	public Thread cycle;

	public JPanel title;
	public JPanel author;
	public JPanel address;
	public JPanel packVer;
	public JPanel radio;
	public JPanel download;
	public JPanel bar;
	public JPanel status;
	public JPanel jobInfo;

	public JTextField addrField = new JTextField(20);
	public JTextField verField = new JTextField(20);

	public ButtonGroup dlType;
	public JRadioButton buttonClient;
	public JRadioButton buttonServer;
	public JButton dlButton;
	public JProgressBar progressBar;

	public JLabel dlStatus;

	public MasterFrame(boolean visible) {

		this.cycle = new Thread("MF-CYCLER");

		this.title = new JPanel();
		this.author = new JPanel();
		this.address = new JPanel();
		this.packVer = new JPanel();
		this.radio = new JPanel();
		this.download = new JPanel();
		this.bar = new JPanel();
		this.status = new JPanel();
		this.jobInfo = new JPanel();

		title.add(new JLabel("ZaniDL " + ZaniDL.VERSION));
		author.add(new JLabel("Written by Treyzania"));
		title.setVisible(true);
		author.setVisible(true);

		address.add(new JLabel("Pack host: "));
		address.add(addrField);
		address.setVisible(true);

		packVer.add(new JLabel("Desired version: "));
		packVer.add(verField);
		packVer.setVisible(true);

		addrField.setText("");

		dlType = new ButtonGroup();
		buttonClient = new JRadioButton("Client");
		buttonClient.setSelected(true);
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

		dlStatus = new JLabel(noErrorsText);
		status.add(this.dlStatus);
		status.setVisible(true);

		progressBar = new JProgressBar(0, 1);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		bar.add(progressBar);

		jobInfo.add(new JLabel("weiners"));
		jobInfo.setBackground(Color.GREEN);
		jobInfo.setToolTipText("PENIS!");

		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		//this.setLayout(new CardLayout());

		this.add(this.title);
		this.add(this.author);
		this.add(this.address);
		this.add(this.packVer);
		this.add(this.radio);
		this.add(this.download);
		this.add(this.bar);
		this.add(this.status);

		// Work on this later!
		//this.add(this.jobInfo);

		this.setBounds(0, 0, 275, 325);
		this.setFocusable(true);
		this.setResizable(false);
		this.setVisible(visible);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.revalidate();
		this.repaint();

		cycle.start();

	}

	public void updateState(State s) {

		dlStatus.setText(s.text);

	}

	public void beep() {

		this.getToolkit().beep();

	}

	@Override
	public void run() {

		this.revalidate();
		this.repaint();

		// It's an infinite loop!
		while (true) {
			
			// Do I need to individually repaint these all?
			title.repaint();
			address.repaint();
			radio.repaint();
			download.repaint();
			bar.repaint();
			status.repaint();

			this.repaint();

		}

	}

	// Static methods.
	public static void laf() { // AKA "Look and Feel"

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			ZaniDL.log.fine("Look and Feel error!  (It doesn't feel right!)");
		}

	}

}
