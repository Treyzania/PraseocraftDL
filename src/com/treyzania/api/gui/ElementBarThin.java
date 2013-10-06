package com.treyzania.api.gui;

import java.awt.Color;
import java.awt.Graphics2D;

public class ElementBarThin extends Element {

	public Color bar;
	public Color slider;
	public int max = 0;
	public int current = 0;
	public float scalingRatio = 1.0F;
	
	public Color titleColor;
	public boolean write = false;
	public String name = "";
	public boolean numbers = false;
	public boolean drawPercentage = false;
	
	public ElementBarThin(int x, int y, Color bar, Color slider) {
		
		super(x, y);
		
		this.bar = bar;
		this.slider = slider;
		
	}
	
	public void setAttribs(float ratio, boolean write, String text, Color titleColor, boolean numbers, boolean percentage) {
		
		this.scalingRatio = ratio;
		this.write = write;
		this.name = text;
		this.titleColor = titleColor;
		this.numbers = numbers;
		this.drawPercentage = percentage;
		
	}
	
	public void setValue(int value) {
		
		this.current = value;
		
	}
	
	public void setMaximum(int max) {
		
		this.max = max;
		
	}
	
	public void setScalingRatio(float ratio) {
		
		this.scalingRatio = ratio;
		
	}
	
	/*public float autoAdjustRatio(int length) {
		
		this.scalingRatio = length / max;
		return this.scalingRatio;
		
	}*/
	
	@Override
	public void render(Graphics2D g2d) {
		
		// Inits
		int pointAdj = (int) (current * scalingRatio);
		int maxAdj = (int) (max * scalingRatio);
		int percentage = (int) (( ( (float) current) / ( (float) max) ) * 100);
		String title = "";
		if (write) title = title + name;
		if (numbers && write) title = title + ": ";
		if (numbers) title = title + current + " / " + max;
		if ( (write && drawPercentage) || (numbers && drawPercentage) ) title = title + " ";
		if (drawPercentage) title = title + "( " + percentage + "% )";
		
		int barStart_X = x;
		int barStart_Y = y + 5;
		int barEnd_X = x + maxAdj;
		int barEnd_Y = y + 5;
		
		int sliderStart_X = x + pointAdj;
		int sliderStart_Y = y;
		int sliderEnd_X = x + pointAdj;
		int sliderEnd_Y = y + 11;
		
		// Renders
		g2d.setColor(this.bar);
		g2d.drawLine(barStart_X, barStart_Y, barEnd_X, barEnd_Y); // Bar
		g2d.setColor(this.slider);
		g2d.drawLine(sliderStart_X, sliderStart_Y, sliderEnd_X, sliderEnd_Y); // Slider
		
		g2d.setColor(this.titleColor);
		g2d.drawString(title, x, y - 5); // Text
		
	}
	
}
