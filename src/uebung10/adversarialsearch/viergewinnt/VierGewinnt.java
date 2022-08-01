package uebung10.adversarialsearch.viergewinnt;

import uebung10.adversarialsearch.Game;
import uebung10.adversarialsearch.State;

import java.util.ArrayList;
import java.util.List;

public class VierGewinnt implements Game {


    public VierGewinnt() {
    }

    public VG_State castState(State s) {
        assert s instanceof VG_State;
        return (VG_State) s;
    }

    @Override
    public List<String> getActions(State s) {
        List<String> ret = new ArrayList<String>();
        VG_State vstate = (VG_State) s;
        for( int c = 0; c < vstate.numColumns(); c++ )
            if( vstate.isColumnFree(c) ) {
                ret.add(String.valueOf(c));
            }
        return ret;
    }

    @Override
    public State getInitialState() {
        return getInitialState(7, 7, 4);
    }

    @Override
    public State getInitialStateEasy() {
        return getInitialState(4, 4, 3);
    }

    @Override
    public State getInitialStateMedium() {
        return getInitialState(6, 5, 3);
    }

    @Override
    public State getInitialStateHard() {
        return getInitialState(6, 4, 4);
    }

    public State getInitialState(int height, int width, int numToWin) {
        System.out.println("Vier Gewinnt:\n-------------\n  * place " + numToWin + " pieces in a straight line to win!\n");
        return new VG_State(height, width, numToWin);
    }

    @Override
    public int getPlayer(State s) {
        return ((VG_State) s).getPlayer();
    }

    @Override
    public State getResult(State s, String action) {
        VG_State vstate = (VG_State) (((VG_State) s).clone());
        int retCode = action == null ? -1 : vstate.placeDisc(Integer.valueOf(action));
        if( retCode != 0 )
            return null;
        return vstate;
    }

    @Override
    public double getUtility(State s, int player) {
        VG_State vgs = (VG_State) s;
        int gameState = vgs.getWinner();
        if( gameState == 0 ) return 0;
        return (gameState == player) ? +1 * (vgs.maxDepth() + 1 - vgs.getDepth()) : -1 * (vgs.maxDepth() + 1 - vgs.getDepth());
    }

    @Override
    public boolean isTerminalState(State s) {
        return ((VG_State) s).getWinner() >= 0;
    }

    @Override
    public int getWinner(State s) {
        return ((VG_State) s).getWinner();
    }

}
