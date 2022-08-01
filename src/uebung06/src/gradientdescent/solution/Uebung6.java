package uebung06.src.gradientdescent.solution;

public class Uebung6 {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		double a = 0.0000000000001;
	  
		System.out.println("Minimizing the 1-D Function.");
		Function<Double> funcD = new MysteryFunction();
		GradientDescentDouble solver1 = new GradientDescentDouble(funcD,GradientDescent.DOUBLING_STEP);
		for( int i = 0; i < 10; i++ ) {
			double x0 = GradientDescent.getRandomDouble();
			double r = solver1.findMinimum(x0, a);
			System.out.println("Minimum attained: " + funcD.value(r) + " at " + r);
		}

	    a = 0.00001;
	    
	    System.out.println();
	    System.out.println("Minimizing the 20-D Function.");
	    Function<Vector> funcV = new MultiDimFunction();
	    GradientDescentVector solver2 = new GradientDescentVector(funcV,GradientDescent.DOUBLING_STEP);
	    for( int i = 0; i < 10; i++ ) {
	    	System.out.println("Iteration " + (i+1));
	    	Vector x0 = GradientDescent.getRandomVector(funcV.numDimensions());
	    	Vector r = solver2.findMinimum(x0, a);
	    	System.out.println("\tMinimum attained: " + funcV.value(r) + " at " + r.toString());
	    	System.out.println("\tNumber of gradient steps: " + solver2.numSteps());
	    }
  }
  

}
