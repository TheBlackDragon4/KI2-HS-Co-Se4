package uebung06.src.gradientdescent.solution;


/** Interface that defines a real-valued function. 
 * Every function must implement the following methods:
 *   - value() returns the value of the function at a point.
 *   - gradient() returns the gradient of the function at a point.
 *   - hessian() returns the hessian of the function at a point.
 *   - numDimensions() returns the dimension of the function's input.  
 **/
public interface Function<T> {
	public double value(T x0);
	public T gradient(T x0);
	public Object hessian(T x0);
	public int numDimensions();
}
