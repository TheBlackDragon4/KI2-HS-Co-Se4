package uebung06.src.gradientdescent;

import uebung06.src.Jama.Matrix;

public class Vector extends Matrix {
	private static final long serialVersionUID = 1L;

	public Vector(int dim) {
		super(dim,1);
	}
	
	public Vector(double[] data) {
		super(data,data.length);
	}
	
	public Vector(Matrix m) {
		this(m.getColumnPackedCopy());
		assert m.getColumnDimension() == 1;
	}

	public double inner(Vector v) {
		assert v.getRowDimension() == this.getRowDimension();
		return this.transpose().times(v).get(0,0);
	}
	
	/* Implements A*v */
	public Vector mult(Matrix A) {
		assert A.getColumnDimension() == this.getRowDimension();
		return new Vector(A.times(this));
	}
	
	public Vector plus(Vector v) { return new Vector(super.plus(v)); }
	public Vector minus(Vector v) { return new Vector(super.minus(v)); }
	public Vector times(double s) { return new Vector(super.times(s)); }
	
	public double sumElements() {
		double ret = 0.0;
		for(int i=0; i < this.getRowDimension(); i++) {
			ret += this.get(i,0);
		}
		return ret;
	}
	
	public static Vector allOnes(int dim) {
		Vector v = new Vector(dim);
		for(int i = 0; i < dim; i++) {
			v.set(i,0,1.0);
		}
		return v;
	}
}
