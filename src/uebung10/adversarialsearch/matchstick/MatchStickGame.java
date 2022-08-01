package uebung10.adversarialsearch.matchstick;

import uebung10.adversarialsearch.Game;
import uebung10.adversarialsearch.State;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MatchStickGame implements Game {

    public MatchStickState castState(State s) {
        if( s instanceof MatchStickState state ) {
            return state;
        }
        return null;
    }

    @Override
    public State getInitialState() {
        return getInitialStateEasy();
    }

    @Override
    public State getInitialStateEasy() {
        return new MatchStickState(new int[]{2, 3}, 1, 0);
    }

    @Override
    public State getInitialStateMedium() {
        return new MatchStickState(new int[]{2, 3, 4}, 1, 0);
    }

    @Override
    public State getInitialStateHard() {
        return new MatchStickState(new int[]{2, 3, 4, 5}, 1, 0);
    }

    @Override
    public int getPlayer(State s) {
        if( s instanceof MatchStickState state ) {
            return state.playerNo;
        }
        return 0;
    }

    @Override
    public List<String> getActions(State s) {
        List<String> actions = new ArrayList<>();
        if( s instanceof MatchStickState state ) {
            for( int stackIdx = 0; stackIdx < state.stacks.length; stackIdx++ ) {
                if( state.stacks[stackIdx] > 0 ) {
                    for( int num = 1; num <= state.stacks[stackIdx]; num++ ) {
                        actions.add((stackIdx+1) + ":" + num);
                    }
                }
            }
        }
        return actions;
    }

    @Override
    public State getResult(State s, String action) {
        if( s instanceof MatchStickState state ) {
            String[] parts = action.split(":");
            if( parts.length != 2 ) {
                return null;
            }
            int stackIdx;
            int numMatches;
            try {
                int[] partsInt = Arrays.stream(parts).mapToInt(x -> Integer.valueOf(x)).toArray();
                stackIdx = partsInt[0] - 1;
                numMatches = partsInt[1];
            }
            catch(NumberFormatException e) {
                return null;
            }
            if( stackIdx < 0 ||stackIdx >= state.stacks.length
                || numMatches < 1 || numMatches > state.stacks[stackIdx] ) {
                return null;
            }

            // All checks done, make move
            int[] newStacks = Arrays.copyOf(state.stacks, state.stacks.length);
            newStacks[stackIdx] -= numMatches;

            return new MatchStickState(newStacks, 3 - getPlayer(state), state.getDepth() + 1);
        }
        return null;
    }

    @Override
    public boolean isTerminalState(State s) {
        if( s instanceof MatchStickState state ) {
            // State is terminal state if all stacks have zero matches
            return Arrays.stream(state.stacks).allMatch(value -> value <= 0);
        }
        return false;
    }

    @Override
    public double getUtility(State s, int player) {
        if( isTerminalState(s) ) {
            // Return +1 if the active player is the querying player, -1 otherwise
            return getPlayer(s) == player ? 1 : -1;
        }
        return 0;
    }

    @Override
    public int getWinner(State s) {
        if( isTerminalState(s) ) {
            return getPlayer(s);
        }
        return 0;
    }
}
