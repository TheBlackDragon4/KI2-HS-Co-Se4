package uebung05.hillclimbing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class NQueensProblem implements ProblemState<NQueensProblem>{

	private int n;
	private int[] positions;

	public NQueensProblem(int n) {
		this.n = n;
		this.positions = new int[n];
		Arrays.fill(this.positions, 1);
	}

	public NQueensProblem(int n, int[] positions) {
		this(n);
		setPositions(positions);
	}
	
	protected void setPositions(int[] newPos) {
		if( newPos.length != n ) {
			throw new IllegalArgumentException();
		}
		System.arraycopy(newPos, 0, this.positions, 0, n);
	}
	
	private void setPosition(int col, int row) {
		positions[col] = row;
	}
	
	public double evaluate() {
		return numNonConflictingPairs();
	}
	
	public int numNonConflictingPairs() {
		int count = (n * (n - 1))/2;
		// Iteriere �ber alle Paare von Damen
		for( int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				if( positions[i] == positions[j]) {
					// gleiche Zeile
					count--;
				} else if( Math.abs(i - j) == Math.abs(positions[i] - positions[j])) {
					// auf einer Diagonalen
					count--;
				}
			}
		}
		return count;
	}
	
	public Collection<NQueensProblem> getSuccessors() {
		List<NQueensProblem> succs = new ArrayList<>(n*(n-1));
		for( int i = 0; i < n ; i++ ) {
			for( int j = 1; j <= n; j++ ) {
				if( j != positions[i] ) {
					NQueensProblem state = new NQueensProblem(n, positions);
					state.setPosition(i, j);
					succs.add(state);
				}
			}
		}
		return succs;
	}
	
	public String toString() {
		return Arrays.toString(positions);
	}
}
