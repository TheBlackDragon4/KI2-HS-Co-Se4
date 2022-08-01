package uebung06.src.gradientdescent;

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
}
