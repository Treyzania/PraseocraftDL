package com.treyzania.zanidl.resouces;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ConsolePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1898886670999349478L;
	
	public int width;
	public int height;
	
	public JScrollPane scroll;
	public JTextArea console;
	
	public ConsolePanel(int a, int b) {
		
		this.width = b;
		this.height = a;
		
		this.console = new JTextArea(this.width, this.height);
		this.scroll = new JScrollPane(console, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		this.config(); // Fufufufufuuuu...
		this.add(scroll);
		
		this.setVisible(true);
		
	}
	
	public void addLine(String line) {
		
		console.append(line + "\n");
		
	}
	
	private void config() {
		
		console.setVisible(true);
		
		// Thanks to http://stackoverflow.com/questions/2483572/making-a-jscrollpane-automatically-scroll-all-the-way-down
		scroll.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
	        public void adjustmentValueChanged(AdjustmentEvent e) {  
	            e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
	        }
	    });
		
	}
	
}
