package uebung01;

import java.util.Arrays;
import java.util.List;

public class StaubsaugerProblem implements Problem {

	// Liste der Aktionen
	private List<String> actions = List.of("L", "R", "V"); 

	private int numRooms;
	private int startingPos;

	public StaubsaugerProblem(int n, int pos) {
		this.numRooms = n;
		this.startingPos = pos;

		if (n < 2) {
			throw new IllegalArgumentException("n must be >= 2");
		}
		if (pos < 0 || pos >= n) {
			throw new IllegalArgumentException("pos must be between 0 an n-1");
		}

	}

	@Override
	public State getInitialState() {
		// TODO Auto-generated method stub
		boolean[] isDirty = new boolean[numRooms];
		Arrays.fill(isDirty, true);
		return new StaubsaugerState(this, startingPos, isDirty);
	}

	@Override
	public List<String> getActions(State s) {
		// TODO Auto-generated method stub
		return actions;
	}

	@Override
	public State getResults(State s, String action) {
		// TODO Auto-generated method stub
		StaubsaugerState state = (StaubsaugerState) s;

		int newPos = 0;
		boolean[] newIsDirty = Arrays.copyOf(state.getIsDirty(), numRooms);
		if ("V".equalsIgnoreCase(action)) {
			newPos = state.getPos();
			newIsDirty[state.getPos()] = false;
		} else if ("L".equalsIgnoreCase(action)) {
			newPos = Math.max(0, state.getPos() - 1);
		} else if ("R".equalsIgnoreCase(action)) {
			newPos = Math.min(numRooms - 1, state.getPos() + 1);
		}
		StaubsaugerState succState = new StaubsaugerState(this, newPos, newIsDirty);
		return succState;
	}

	@Override
	public boolean isGoalState(State s) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getCosts(State s, String action, State succ) {
		// TODO Auto-generated method stub
		return 0;
	}

}
