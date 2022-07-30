package uebung02;

import java.util.Arrays;

public class StaubsaugerState implements State {

	private StaubsaugerProblem prob;

	private int pos; // Position

	private boolean[] isDirty; // Gibt an welches Feld Schmutzig oder Sauber ist

	public StaubsaugerState(StaubsaugerProblem prob, int pos, boolean[] isDirty) {
		this.prob = prob;

		this.pos = pos;
		this.isDirty = Arrays.copyOf(isDirty, isDirty.length);
	}

	@Override
	public Problem getProblem() {
		// TODO Auto-generated method stub
		return prob;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		for (int i = 0; i < isDirty.length; i++) {
			if (i > 0) {
				sb.append('|');
			}
			if (isDirty[i]) {
				sb.append('~'); // Ist Schmutzig
			} else {
				sb.append(' ');
			}
			if (i == pos) {
				sb.append('X'); // Ist Agent
			} else {
				sb.append(' ');
			}

		}
		sb.append(']');

		return sb.toString();
	}

	protected int getPos() {
		// TODO Auto-generated method stub
		return pos;
	}

	protected boolean[] getIsDirty() {
		// TODO Auto-generated method stub
		return isDirty;
	}

}
