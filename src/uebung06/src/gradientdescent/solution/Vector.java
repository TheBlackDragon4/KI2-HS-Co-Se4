package uebung06.src.gradientdescent.solution;

import uebung06.src.Jama.Matrix;

/* Implements a Vector as a Matrix with a single column. */
public class Vector extends Matrix {
	private static final long serialVersionUID = 1L;

	/* A simple constructor that makes a vector of a certain size. */
	public Vector(int dim) {
		super(dim,1);
	}
	
	/* A constructor that makes a vector from a double[]. */
	public Vector(double[] data) {
		super(data,data.length);
	}
	
	/* A helper constructor that converts a matrix into a Vector. */
	public Vector(Matrix m) {
		this(m.getColumnPackedCopy());
		assert m.getColumnDimension() == 1;
	}

	/* Returns the inner product between this vector and the vector v.
	 * (v must be of the same dimension as this vector) 
	 */
	public double inner(Vector v) {
		assert v.dim() == this.dim();
		return this.transpose().times(v).get(0,0);
	}
	
	/* Implements A*v; that is, it return the result of matrix
	 * multiplication with the matrix A and this vector.
	 * (A must have the same number of columns as this vector has rows)
	 */
	public Vector mult(Matrix A) {
		assert A.getColumnDimension() == this.dim();
		return new Vector(A.times(this));
	}
	
	/* Returns a NEW vector that is the result of adding this vector to v. */
	public Vector plus(Vector v) { return new Vector(super.plus(v)); }
	/* Returns a NEW vector that is the result of subtracting v from this vector. */
	public Vector minus(Vector v) { return new Vector(super.minus(v)); }
	/* Returns a NEW vector that is the result of multiplying this vector by the scalar s */
	public Vector times(double s) { return new Vector(super.times(s)); }
	
	public int dim() { return this.getRowDimension(); }
	public double get(int i) { assert i >= 0 && i < this.dim(); return this.get(i,0); }
	public void set(int i,double val) { assert i >= 0 && i < this.dim(); this.set(i,0,val); } 
	
	public String toString() {
		String ret = "";
		if(this.dim() >= 1) { 
			ret += "<" + this.get(0,0);
			for(int i = 1; i < this.dim(); i++) { ret += ", " + this.get(i,0); }
			ret += ">";
		}
		return ret;
	}
}
