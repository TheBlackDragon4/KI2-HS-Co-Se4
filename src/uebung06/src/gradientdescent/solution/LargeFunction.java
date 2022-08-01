package uebung06.src.gradientdescent.solution;

import uebung06.src.Jama.Matrix;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class LargeFunction implements Function<Vector> 
{
	private Matrix Qmat = null;
	private Vector Fvec = null;
	private double Csac = Double.NaN;
	
	public LargeFunction() {
		
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader("mat.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.Qmat = Matrix.read(in); 
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			in = new BufferedReader(new FileReader("vec.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.Fvec = new Vector(Matrix.read(in)); 
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			in = new BufferedReader(new FileReader("scalar.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.Csac = Matrix.read(in).get(0,0); 
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* The gradient of the function is Q*x0 - F */
	@Override
	public Vector gradient(Vector x0) { return x0.mult(this.Qmat).minus(Fvec); }

	/* The function 1/2*x0*Q*x0 - x0*F + 1/2*C */ 
	@Override
	public double value(Vector x0) { return 0.5 * x0.inner(x0.mult(this.Qmat)) - x0.inner(Fvec) + 0.5*Csac + 42; }
	
	@Override
	public Object hessian(Vector x0) { return this.Qmat; }
	
	public int numDimensions() { return 1000; }
}
