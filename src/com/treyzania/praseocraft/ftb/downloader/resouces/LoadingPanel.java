package com.treyzania.praseocraft.ftb.downloader.resouces;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.Box;

public class LoadingPanel extends DLPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8599561623521059486L;
	
	public LoadingPanel() {
		
		super();
		
		this.add(Box.createRigidArea(new Dimension(256, 64)));
		
	}
	
	@Override
	public void paint(Graphics g) {
		
		super.paint(g);
		//Graphics2D g2d = (Graphics2D) g;
		
		//PCDL.loadingBar.render(g2d);
		
	}
	
}
