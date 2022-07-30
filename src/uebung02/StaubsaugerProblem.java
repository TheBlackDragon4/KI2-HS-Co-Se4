package uebung02;

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

		// Variable um den Wert des neuen State zu speichern
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
		// erzeugung eines neues State
		StaubsaugerState succState = new StaubsaugerState(this, newPos, newIsDirty);
		return succState;
	}

	@Override
	public boolean isGoalState(State s) {
		// Goale Test ist in der Staubsaugerwelt die Pr�fung obn der Boden Schmutzig ist
		// oder nicht
		StaubsaugerState state = (StaubsaugerState) s;
		boolean[] isDirty = state.getIsDirty();
		boolean allIsClean = true;

		// Nun m�ssen wir jeden der R�ume auf das Ziel �berpr�fen
		for (int i = 0; i < isDirty.length; i++) {
			// Wenn nur einer der R�ume Schmutzig sein sollte, sind nicht alle R�ume Sauber
			// und somit
			// ist der Zieltest nicht erf�llt worden
			if (isDirty[i]) {
				allIsClean = false;
			}

		}
		
		return allIsClean;
	}

	@Override
	public double getCosts(State s, String action, State succ) {
		// Die Kosten in der Staubsaugerwelt sind f�r jede Aktion = 1
		// Somit hate jede Aktion Kosten von "1"
		return 1;
	}

}
