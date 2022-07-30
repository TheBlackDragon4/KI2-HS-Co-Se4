package ki2.untyped.staubsauger;

import ki2.untyped.Problem;
import ki2.untyped.State;

import java.util.Arrays;
import java.util.List;

public class StaubsaugerProblem implements Problem {

    private List<String> actions = List.of("L", "R", "V");

    private int numRooms;
    private int startingPos;

    public StaubsaugerProblem(int n, int pos) {
        this.numRooms = n;
        this.startingPos = pos;

        if( n < 2 ) {
            throw new IllegalArgumentException("n must be >= 2");
        }
        if( pos < 0 || pos >= n ) {
            throw new IllegalArgumentException("pos must be between 0 and n-1");
        }
    }

    @Override
    public State getInitialState() {
        boolean[] isDirty = new boolean[numRooms];
        Arrays.fill(isDirty, true);

        return new StaubsaugerState(this, startingPos, isDirty);
    }

    @Override
    public List<String> getActions(State s) {
        return actions;
    }

    @Override
    public State getResult(State s, String action) {
        StaubsaugerState state = (StaubsaugerState)s;

        // Variables to cache the values for the new state
        int newPos = 0;
        boolean[] newIsDirty = Arrays.copyOf(state.getIsDirty(), numRooms);

        if( "V".equalsIgnoreCase(action) ) {
            newPos = state.getPos();
            newIsDirty[state.getPos()] = false;
        } else if ( "L".equalsIgnoreCase(action) ) {
            newPos = Math.max(0, state.getPos() - 1);
        } else if ( "R".equalsIgnoreCase(action) ) {
            newPos = Math.min(numRooms - 1, state.getPos() + 1);
        }

        // build new state
        StaubsaugerState succState = new StaubsaugerState(this, newPos, newIsDirty);
        return succState;
    }

    @Override
    public boolean isGoalState(State s) {
        StaubsaugerState state = (StaubsaugerState)s;

        // Goal is reached if every room is clean
        boolean[] isDirty = state.getIsDirty();
        boolean allClean = true;
        // Check every room
        for( int i = 0; i < numRooms; i++ ) {
            // If one room is dirty, not all are clean
            if( isDirty[i] ) {
                allClean = false;
            }
        }

        return allClean;
    }

    @Override
    public double getCost(State s, String action, State succ) {
        // all actions cost 1
        return 1;
    }
}
