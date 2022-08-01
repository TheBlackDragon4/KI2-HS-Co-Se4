package uebung06.src.gradientdescent;

public interface Function<T> {
	public double value(T x0);
	public T gradient(T x0);
	public int numDimensions();
}
