package uebung10.adversarialsearch.matchstick;

import uebung10.adversarialsearch.State;

import java.util.Arrays;

public class MatchStickState implements State {

    final int[] stacks;
    final int playerNo;
    final int depth;

    public MatchStickState(int[] stacks, int playerNo, int depth) {
        this.stacks = Arrays.copyOf(stacks, stacks.length);
        this.playerNo = playerNo;
        this.depth = depth;
    }

    @Override
    public int getDepth() {
        return depth;
    }

    @Override
    public String toString() {
        String s = "";
        for( int idx = 0; idx < stacks.length; idx++ ) {
            s += (idx+1) + ":" + stacks[idx] + "   ";
        }
        return s;
    }
}
