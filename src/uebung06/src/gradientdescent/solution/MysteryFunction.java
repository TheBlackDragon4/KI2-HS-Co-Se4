package uebung06.src.gradientdescent.solution;


public class MysteryFunction implements Function<Double> {

	@Override
	public Double gradient(Double x0) {
		return 12.0*x0 +
			16.0*x0*x0 +
			-11.0*x0*x0*x0 +
			-20.0*x0*x0*x0*x0 +
			-2.0*x0*x0*x0*x0*x0 +
			4.0*x0*x0*x0*x0*x0*x0 +
			1.0*x0*x0*x0*x0*x0*x0*x0;
	}

	@Override
	public double value(Double x0) {
		return 6.0*x0*x0 + 
			16.0/3.0 *x0*x0*x0 + 
			-11.0/4*x0*x0*x0*x0 +
			-4.0*x0*x0*x0*x0*x0
			-1.0/3.0*x0*x0*x0*x0*x0*x0+
			4.0/7.0*x0*x0*x0*x0*x0*x0*x0+
			1.0/8.0*x0*x0*x0*x0*x0*x0*x0*x0;
	}
	
	public int numDimensions() {
	  return 1;
	}

	@Override
	public Object hessian(Double x0) {
		return 12.0 +
		32.0*x0 +
		-33.0*x0*x0 +
		-80.0*x0*x0*x0 +
		-10.0*x0*x0*x0*x0 +
		24.0*x0*x0*x0*x0*x0 +
		7.0*x0*x0*x0*x0*x0*x0;
	}
}
