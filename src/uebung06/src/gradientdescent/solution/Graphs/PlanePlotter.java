package uebung06.src.gradientdescent.solution.Graphs;

import java.awt.*;
import java.util.ArrayList;

public abstract class PlanePlotter 
{
	private boolean isValid;
	protected ArrayList<CartesianPlane> views;
	
	public PlanePlotter() 
	{
		super();
		this.isValid = false;
		this.views = new ArrayList<CartesianPlane>();
	}
	
	public void addView(CartesianPlane plane)
	{
		views.add(plane);
	}
	
	public void inValidate() { this.isValid = false; }
	public boolean valid() { return this.isValid; }
	
	public void repaint()
	{
		for(CartesianPlane p : views)
			p.repaint();
	}
	
	public void paintPlot(Graphics g, CartesianPlane p)
	{
		paint(g,p);
		this.isValid = true;
	}

	protected abstract void paint( Graphics g, CartesianPlane p );
	public abstract OptionsPanel getOptions();
	public abstract String getName();
}