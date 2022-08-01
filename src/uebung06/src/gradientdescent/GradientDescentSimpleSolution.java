package uebung06.src.gradientdescent;
import java.util.Random;


public class GradientDescentSimpleSolution<T> {


  public static final Random rnd = new Random();
  public static final int MAX_RANGE = 10;
  public static final double epsilon = 0.000001;

  protected Function<T> func;

  /**
   * @param args
   */
  public static void main(String[] args) {

    // Example:
    GradientDescentSimpleSolution<Double> gdMystery = new GradientDescentSimpleSolution<Double>(new MysteryFunction());

    double x0 = getRandomDouble();
    double alpha = 0.000000000001;
    double result = gdMystery.findMinimum(x0, alpha);

    System.out.println("x=" + result + " ; f(x)=" + gdMystery.func.value(result));


    GradientDescentSimpleSolution<Vector> gdMultiDim = new GradientDescentSimpleSolution<>(new MultiDimFunction());
    Vector v0 = getRandomVector(gdMultiDim.func.numDimensions());
    Vector resultV = gdMultiDim.findMinimum(v0, alpha);

    System.out.println("v=");
    resultV.transpose().print(8,7);
    System.out.println("f(v)=" + gdMultiDim.func.value(resultV));
  }


  public GradientDescentSimpleSolution(Function<T> func) {
    this.func = func;
  }
  
  
  public T findMinimum(T x0, double alpha) {
    T result = null;

    // TODO: do gradient descent here, using a line search

    if( x0 instanceof Double ) {
      result = (T)findMinimumDouble((Double)x0, alpha);
    } else if( x0 instanceof Vector ) {
      result = (T)findMinimumVector((Vector)x0, alpha);
    }
    
    return result;
  }

  public Double findMinimumDouble(double x0, double alpha) {
    double result = Double.NaN;
    Function<Double> funcD = (Function<Double>)func;


    double x = x0;

    while( true ) {
      double gradient = funcD.gradient(x);


      double a = alpha;
      while( funcD.value(x - a * gradient) < funcD.value(x) ) {
        a = a * 2;
      }
      a = a / 2;

      if( Math.abs(a * gradient) < 10e-6 ) {
        break;
      }
      x = x - a * gradient;
      System.out.println(x);
    }

    return x;
  }

  public Vector findMinimumVector(Vector x0, double alpha) {
    double result = Double.NaN;
    Function<Vector> funcV = (Function<Vector>)func;


    Vector x = x0;

    while( true ) {
      Vector gradient = funcV.gradient(x);


      double a = alpha;
      while( funcV.value(x.minus(gradient.times(a))) < funcV.value(x) ) {
        a = a * 2;
      }
      a = a / 2;

      if( Math.pow(gradient.normF(), 2) < 10e-6 ) {
        break;
      }
      x = x.minus(gradient.times(a));
//      System.out.println(x);
      x.transpose().print(8,7);
//      System.out.println(gradient.normF());
    }

    return x;
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
