package com.treyzania.zanidl.resouces;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ConsoleFrame extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7597777056600962139L;
	
	public JPanel titlePanel;
	public JPanel console;
	
	public JLabel titleLabel;
	
	public ConsoleFrame() {
		
		titlePanel = new JPanel();
		titleLabel = new JLabel("Console");
		titlePanel.add(this.titleLabel);
		
		console = new ConsolePanel(50, 20);
		
		this.add(this.titlePanel);
		this.add(this.console);
		
		this.setBounds(0, 0, 450, 425);
		this.setFocusable(true);
		this.setResizable(false);
		this.setVisible(true);
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		this.revalidate();
		this.repaint();
		
		testConsoleWin();
		
	}
	
	private void testConsoleWin() {
		
		ConsolePanel p = (ConsolePanel) this.console;
		
		p.addLine("Console Initialized.");
		
	}
	
}
