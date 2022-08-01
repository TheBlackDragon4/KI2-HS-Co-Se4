package uebung06.src.gradientdescent.solution;

import uebung06.src.Jama.Matrix;
import uebung06.src.gradientdescent.solution.Graphs.CartesianPlane;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;


public class GradientDescentVector extends GradientDescent<Vector> {
	
	double xmin = 0.0;
	double ymin = 0.0;
	double xmax = 1.0;
	double ymax = 100.0;
	
	public GradientDescentVector(Function<Vector> func,int mode) {
		super(func,mode);
	}

	private class GradStep implements Function<Double> {
		  private Vector basis;
		  private Vector step;
		  private Function<Vector> func;
		  public GradStep(Function<Vector> func, Vector origen, Vector grad) {
			  this.func = func;
			  this.basis = origen;
			  this.step = grad;
		  }
		  
		  @Override
		  public double value(Double s) { 
			  assert s >= 0.0;
			  return this.func.value(this.basis.minus(this.step.times(s))); 
		  }

		  @Override
		  public Double gradient(Double x0) {
			  // TODO Auto-generated method stub
			  return null;
		  }

		  @Override
		  public int numDimensions() { return 1; }
		  
		  public Vector takeStep(Double s) {
			  assert s >= 0.0;
			  return this.basis.minus(this.step.times(s));
		  }

		@Override
		public Object hessian(Double x0) {
			// TODO Auto-generated method stub
			return null;
		}
	  }

	@Override
	public Vector findMinimum(Vector x0, double alpha) {
	    Vector x = x0;
	    Vector grad = null;
	    
	    double[] d = new double[this.func.numDimensions()];
	    Arrays.fill(d, Double.MAX_VALUE);
	    
	    this.numSteps = 0;
	    double newVal = this.func.value(x);
	    
	    this.xmin = 0;
	    this.xmax = 1;
	    this.plotPoints = new ArrayList<Vector>();
	    this.plotPoints.add(new Vector(new double[]{this.numSteps,newVal}));
	    
	    this.ymax = newVal;
	    this.ymin = ymax - 1;
	    
	    double s = alpha;
	    
	    while( grad == null || grad.times(s).normF() > epsilon ) {
	    	this.numSteps++;
	    	grad = this.func.gradient(x);
	    	
	    	if(this.gradStepMode==GradientDescent.NEWTON_RHAPSON_STEP) {
	    		x = x.minus(grad.mult(((Matrix)this.func.hessian(x)).inverse()));
	    	} else {
	    		GradStep stepfunc = new GradStep(this.func,x,grad);

	    		double b = alpha / 2.0;
	    		do {
	    			b *= 2.0;
	    		} while( stepfunc.value(b) < this.func.value(x));

	    		if(this.gradStepMode==GradientDescent.STATIC_STEP)
	    			s = alpha;
	    		else if(this.gradStepMode==GradientDescent.DOUBLING_STEP)
	    			s = b / 2.0;
	    		else if(this.gradStepMode==GradientDescent.GOLDEN_SECTION_STEP)
	    			s = goldenSectionSearch(stepfunc,0.0,b/2.0,b,0.001);

	    		x = stepfunc.takeStep(s);
	    	}
	    	newVal = this.func.value(x);
	    	this.plotPoints.add(new Vector(new double[]{this.numSteps(),newVal}));
	    	this.xmax = this.numSteps;
	    	if(newVal < this.ymin) this.ymin = newVal;
	    	this.repaint();
	    }
	    
	    this.repaint();
	    
	    return x;
	}

	@Override
	public void plotFunc(Graphics g, CartesianPlane p) {
		p.setPlaneBounds(this.xmin, this.ymin, this.xmax, this.ymax);
	}

	@Override
	public Vector findMinimum(double alpha) {
		return findMinimum(GradientDescent.getRandomVector(this.func.numDimensions()), alpha);
	}
	
	
}
