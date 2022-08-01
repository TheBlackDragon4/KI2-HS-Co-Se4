package uebung06.src.gradientdescent.solution;


import uebung06.src.gradientdescent.solution.Graphs.CartesianPlane;
import uebung06.src.gradientdescent.solution.Graphs.Graph;


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Project:     AdverseLearning
 * File:        GUI.java
 * Last edited: Dec 8, 2004
 * @author Blaine Nelson
 */
public class GUI<T> extends JFrame
{
	private static final long serialVersionUID = 5127569674218883906L;

	protected CartesianPlane drawingPlane = null;

	public static void main(String[] args)
	{
		
		
		Function<Double> func1d = new MysteryFunction();
		GradientDescentDouble solver1 = new GradientDescentDouble(func1d,GradientDescent.STATIC_STEP);
		
		
		Function<Vector> func20d = new MultiDimFunction();
		GradientDescentVector solver2 = new GradientDescentVector(func20d,GradientDescent.DOUBLING_STEP);
		
		Function<Vector> funcBig = new LargeFunction();
		GradientDescentVector solver3 = new GradientDescentVector(funcBig,GradientDescent.DOUBLING_STEP);
		
		// Switch the solver here to see the behavior of Gradient Descent on different functions.
		GUI newGU = new GUI(solver2);
		System.out.println(newGU.getName() + " Started");
	}
	
	
	
	JButton solveButton;
	JSlider kernelWidth;
	JLabel kernVal;
	
	JSlider noise;
	JLabel noiseVal;
	
	JLabel sampleSize;
	JLabel supportSize;
	JLabel margin;
	JLabel radius;
	
	
	public GUI(GradientDescent<T> solver)
	{
		super();
		
		
		
		
		JPanel content = new JPanel(new BorderLayout());
		
		this.drawingPlane = new CartesianPlane(-5.0,-20.0,5.0,20.0,1000,1000);
		drawingPlane.setSize(400,400);
		drawingPlane.setBackground(Color.WHITE);
		Graph axes = new Graph();
		axes.showGrid(false);
		axes.showTicks(false);
		drawingPlane.addPlotter(solver);
		drawingPlane.addPlotter(axes);
		

		content.add(this.drawingPlane,BorderLayout.CENTER);
		
		
		JPanel svmControl = new JPanel(new BorderLayout());
		Arnold arny = new Arnold(solver);
		solveButton = new JButton("Solve");
		solveButton.addActionListener(arny);
		
		JPanel sliderPanel = new JPanel(new GridLayout(3,1));
		
		
		JPanel svmStat = new JPanel(new GridLayout(2,1));
		
		JPanel sas = new JPanel(new GridLayout(1,2));
		sas.add(new JLabel("# Iterations: "));
		sampleSize = new JLabel("0");
		sas.add(sampleSize);
		svmStat.add(sas);
		
		JPanel sus = new JPanel(new GridLayout(1,2));
		sus.add(new JLabel("Min of f: "));
		supportSize = new JLabel("Not Defined");
		sus.add(supportSize);
		svmStat.add(sus);
		
//		JPanel mas = new JPanel(new GridLayout(1,2));
//		mas.add(new JLabel("Margin: "));
//		margin = new JLabel("Not Defined");
//		mas.add(margin);
//		svmStat.add(mas);
//		
//		JPanel ras = new JPanel(new GridLayout(1,2));
//		ras.add(new JLabel("Radius: "));
//		radius = new JLabel("Not Defined");
//		ras.add(radius);
//		svmStat.add(ras);
		
		
		svmControl.add(new JLabel("Gradient Descent Control Panel"), BorderLayout.NORTH);
		svmControl.add(svmStat,BorderLayout.EAST);
		svmControl.add(sliderPanel,BorderLayout.CENTER);
		svmControl.add(solveButton,BorderLayout.WEST);
		
		content.add(svmControl,BorderLayout.SOUTH);
		
		this.setContentPane(content);
		
		this.pack();
		this.setVisible(true);
		this.setSize(500, 500);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public class Arnold implements ActionListener, ChangeListener
	{
		GradientDescent graph;
		
		public Arnold(GradientDescent svmg)
		{
			this.graph = svmg;
		}
		
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0) 
		{
			// TODO Auto-generated method stub
			if(arg0.getSource().equals(solveButton))
			{
				Object ans = this.graph.findMinimum(0.0001);
				sampleSize.setText("" + this.graph.numSteps());
				supportSize.setText("" + this.graph.queryFunc(ans));
				graph.repaint();
			}
		}

		/* (non-Javadoc)
		 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
		 */
		public void stateChanged(ChangeEvent arg0) 
		{
			
		}
		
	}

}
