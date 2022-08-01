package uebung06.src.gradientdescent.solution;

import uebung06.src.gradientdescent.solution.Graphs.CartesianPlane;

import java.awt.*;
import java.util.ArrayList;


public class GradientDescentDouble extends GradientDescent<Double> {
	
	public GradientDescentDouble(Function<Double> func, int mode) {
		super(func,mode);
	}

	@Override
	public Double findMinimum(Double x0, double alpha) {
		System.out.println("\nSTART: " + x0);
	    double x = x0.doubleValue();
	    double grad;
	    
	    this.plotPoints = new ArrayList<Vector>();
	    this.plotPoints.add(new Vector(new double[]{x,this.func.value(x)}));
	    
	    double diff = Double.MAX_VALUE;
	    
	    while( Math.abs(diff) > epsilon ) {
	    	grad = func.gradient(x).doubleValue();
	    	GradStep stepfunc = new GradStep(this.func,x,grad);
	    	
	    	double s = alpha;
	    	if(this.gradStepMode == GradientDescent.NEWTON_RHAPSON_STEP) {
	    		s = 1.0 / (Double)this.func.hessian(x);
	    	} else {
	    		double b = alpha / 2.0;
	    		do {
	    			b *= 2.0;
	    		} while( stepfunc.value(b) < func.value(x));
	    	
	    		if(this.gradStepMode == GradientDescent.DOUBLING_STEP)
	    			s = b/2.0;
	    		else if(this.gradStepMode == GradientDescent.GOLDEN_SECTION_STEP)
	    			s = goldenSectionSearch(stepfunc,0.0,b/2.0,b,0.001);
	    	}
	    	
	    	x = stepfunc.takeStep(s);
	    	diff = -s * grad;
	    	
	    	
	    	this.plotPoints.add(new Vector(new double[]{x,this.func.value(x)}));
	    	this.repaint();
	    }

	    
	    return x;
	}
	
	private class GradStep implements Function<Double> {
		  private double basis;
		  private double step;
		  private Function<Double> func;
		  public GradStep(Function<Double> func, double origen, double grad) {
			  this.func = func;
			  this.basis = origen;
			  this.step = grad;
		  }
		  
		  @Override
		  public double value(Double s) { 
			  assert s >= 0.0;
			  return this.func.value(this.basis - s* this.step); 
		  }

		  @Override
		  public Double gradient(Double x0) {
			  // TODO Auto-generated method stub
			  return null;
		  }

		  @Override
		  public int numDimensions() { return 1; }
		  
		  public Double takeStep(Double s) {
			  assert s >= 0.0;
			  return this.basis - s* this.step;
		  }

		@Override
		public Object hessian(Double x0) {
			// TODO Auto-generated method stub
			return null;
		}
	  }

	@Override
	public void plotFunc(Graphics g, CartesianPlane p)
	{
		double x0 = Double.NEGATIVE_INFINITY;
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(3));
		for(double x = p.getXMin(); x < p.getXMax(); x+= p.xPixelSize()) {
		
			if(!Double.isInfinite(x0)) {
				g2.drawLine((int)p.doubleXPixel(x0),(int)p.doubleYPixel(this.func.value(x0)),(int)p.doubleXPixel(x),(int)p.doubleYPixel(this.func.value(x)));
			}
			x0 = x;
		}
	}

	@Override
	public Double findMinimum(double alpha) {
		return findMinimum(GradientDescent.getRandomDouble(), alpha);
	}
	
	
}
