package uebung06.src.gradientdescent.solution.Graphs;

/**
 *Exception class for the CartesianPlane Class.
 *
 * @author Blaine Nelson
 * @version 11/11/1999 (prog 6)
 */
public class PlaneException extends RuntimeException
{
	private static final long serialVersionUID = -6041563686751894009L;

	/**
     *Default exception constructor to return the default string message.
     */
    public PlaneException()
    {
        super( "The bounds for the plane are illegal" );
    }

    /**
     *Exception constructor which is given a message as an argument.
     *
     * @param message the message passed to the constructor
     */
    public PlaneException( String message )
    {
        super( message );
    }
}
