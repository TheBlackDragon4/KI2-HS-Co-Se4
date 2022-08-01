package uebung06.src.gradientdescent.solution;

import uebung06.src.gradientdescent.solution.Graphs.CartesianPlane;
import uebung06.src.gradientdescent.solution.Graphs.OptionsPanel;
import uebung06.src.gradientdescent.solution.Graphs.PlanePlotter;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;


public abstract class GradientDescent<T> extends PlanePlotter {
	public static final Random rnd = new Random();
	public static final int MAX_RANGE = 10;
	public static final double epsilon = 0.000001;

	protected Function<T> func;
	protected ArrayList<Vector> plotPoints;
	protected int gradStepMode;
	protected int numSteps = 0;
	
	public Color traceColor = Color.RED;
	
	public static final int STATIC_STEP = 1;
	public static final int DOUBLING_STEP = 2;
	public static final int GOLDEN_SECTION_STEP = 3;
	public static final int NEWTON_RHAPSON_STEP = 4;
	

  public GradientDescent(Function<T> func, int mode) {
	  super();
	  this.func = func;
	  this.gradStepMode = mode;
	  this.plotPoints = new ArrayList<Vector>();
  }
  
  public int numSteps() { return this.numSteps; }
  
  
  public abstract T findMinimum(double alpha);
  public abstract T findMinimum(T x0, double alpha);
  public abstract void plotFunc(Graphics g, CartesianPlane p);
  
  public double queryFunc(T x) {
	  return this.func.value(x);
  }
  
  /**
   * @return a random Double value from -MAX_RANGE/2 to MAX_RANGE/2
   */
  public static double getRandomDouble() {
    return (rnd.nextDouble() * MAX_RANGE) - (MAX_RANGE / 2);
  }
  
  /**
   * @param d dimension
   * @return a Vector with random Double values from -MAX_RANGE/2 to MAX_RANGE/2
   */
  public static Vector getRandomVector(int d) {
    Vector v = new Vector(d);
    for( int i = 0; i < d; i++ ) {
      v.set(i, 0, getRandomDouble());
    }
    return v;
  }
  
  static double phi = (1 + Math.sqrt(5)) / 2;
  static double resphi = 2 - phi;
  
  public double goldenSectionSearch(Function<Double> func, double a, double b, double c, double tau) {
	  double x;
	  do {
		  if (c - b > b - a)	x = b + resphi * (c - b);
		  else 					x = b - resphi * (b - a);
		  
		  if (Math.abs(c - a) < tau * (Math.abs(b) + Math.abs(c))) return (c + a) / 2; 
		  if (func.value(x) < func.value(b)) 
		  {
			  if (c - b > b - a) { a=b; b=x; }
			  else { c=b; b=x; }
		  }
		  else {
			  if (c - b > b - a) { c=x; }
			  else { a=x; }
		  }
	  } while (true);
  }
  
	@Override
	public String getName() {
		return "Gradient Descent Options";
	}

	@Override
	public OptionsPanel getOptions() {
		// TODO Auto-generated method stub
		return null;
	}
  

	protected void paint(Graphics g, CartesianPlane p) {
		this.plotFunc(g,p);
		
		if(this.plotPoints.size() >= 2) {
			Vector last = this.plotPoints.get(0);
		
			g.setColor(this.traceColor);
			for(int i = 1; i < this.plotPoints.size(); i++)
			{
				Vector next = this.plotPoints.get(i);
				g.drawLine((int)p.doubleXPixel(last.get(0)),(int)p.doubleYPixel(last.get(1)),(int)p.doubleXPixel(next.get(0)),(int)p.doubleYPixel(next.get(1)));
				last = next;
			}
		}
	}

}
