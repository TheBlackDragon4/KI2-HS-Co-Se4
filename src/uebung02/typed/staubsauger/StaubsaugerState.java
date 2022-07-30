package uebung02.typed.staubsauger;

import uebung02.typed.State;

import java.util.Arrays;
import java.util.Objects;

public class StaubsaugerState implements State<StaubsaugerProblem> {

    private StaubsaugerProblem prob;

    private int pos;

    private boolean[] isDirty;

    public StaubsaugerState(StaubsaugerProblem prob, int pos, boolean[] isDirty) {
        this.prob = prob;

        this.pos = pos;
        this.isDirty = Arrays.copyOf(isDirty, isDirty.length);
    }

    @Override
    public StaubsaugerProblem getProblem() {
        return prob;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < isDirty.length; i++ ) {
            if( i > 0 ) {
                sb.append('|');
            }
            if( isDirty[i] ) {
                sb.append('~');
            } else {
                sb.append(' ');
            }
            if( i == pos ) {
                sb.append('X');
            } else {
                sb.append(' ');
            }
        }
        sb.append(']');

        return sb.toString();
    }

    int getPos() {
        return pos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StaubsaugerState that = (StaubsaugerState) o;
        return pos == that.pos && prob.equals(that.prob) && Arrays.equals(isDirty, that.isDirty);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(prob, pos);
        result = 31 * result + Arrays.hashCode(isDirty);
        return result;
    }

    boolean[] getIsDirty() {
        return isDirty;
    }
}
