/*
 * Created on Dec 8, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package uebung06.src.gradientdescent.solution.Graphs;

import javax.swing.*;

/**
 * Project:     AdverseLearning
 * File:        DrawingCanvas.java
 * Last edited: Dec 8, 2004
 * @author Blaine Nelson
 */
public class DrawingCanvas extends JComponent
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 366322761529442739L;
	protected double rightMargin;
	protected double leftMargin;
	protected double topMargin;
	protected double bottomMargin;
	
	public DrawingCanvas()
	{
		super();
//		this.setDoubleBuffered(true);
		
		this.rightMargin = 0;
		this.leftMargin = 0;
		this.topMargin = 0;
		this.bottomMargin = 0;
		
		this.setSize(400,400);
	}
	
	public void setOffsets( double r, double l, double t, double b )
	{
		if(r < 0 || l < 0 || t < 0 || b < 0)
			throw new IllegalArgumentException("Cannot have negative borders");
		
		if(r+l > 1)
			throw new IllegalArgumentException("RL Border exceeds canvas");
			
		if(t+b > 1)
			throw new IllegalArgumentException("TB Border exceeds canvas");
		
		this.rightMargin = r;
		this.leftMargin = l;
		this.topMargin = t;
		this.bottomMargin = b;
	}
	
	public double getLeft() { return this.leftMargin*getWidth(); }
	public double getRight() { return this.rightMargin*getWidth(); }
	public double getTop() { return this.topMargin*getHeight(); }
	public double getBottom() { return this.bottomMargin*getHeight(); }
}
