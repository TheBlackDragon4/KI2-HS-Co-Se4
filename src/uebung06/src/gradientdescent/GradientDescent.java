package uebung06.src.gradientdescent;
import java.util.Random;


public class GradientDescent<T> {

  
  public static final Random rnd = new Random();
  public static final int MAX_RANGE = 10;
  public static final double epsilon = 0.000001;

  protected Function<T> func;
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    
    // Example:
    GradientDescent<Double> gdMystery = new GradientDescent<Double>(new MysteryFunction());
    
    double x0 = getRandomDouble();
    double alpha = 0.000000000001;
    gdMystery.findMinimum(x0, alpha);
  }


  public GradientDescent(Function<T> func) {
    this.func = func;
  }
  
  
  public T findMinimum(T x0, double alpha) {
    T result = null;

    // TODO: do gradient descent here, using a line search
    
    return result;
  }
  
  
  /**
   * @return a random Double value from -MAX_RANGE/2 to MAX_RANGE/2
   */
  public static double getRandomDouble() {
    return (rnd.nextDouble() * MAX_RANGE) - (MAX_RANGE / 2);
  }
  
  /**
   * @param d dimension
   * @return a Vector with random Double values from -MAX_RANGE/2 to MAX_RANGE/2
   */
  public static Vector getRandomVector(int d) {
    Vector v = new Vector(d);
    for( int i = 0; i < d; i++ ) {
      v.set(i, 0, getRandomDouble());
    }
    return v;
  }  

}
