package uebung10.adversarialsearch.tonylanche;

import uebung10.adversarialsearch.Game;
import uebung10.adversarialsearch.State;

import java.util.*;

/**
 * @author Florian Mittag
 */

public class Tonylanche implements Game {

    public TonylancheState castState(State s) {
        assert s instanceof Tonylanche;
        TonylancheState ts = (TonylancheState) s;
        return ts;
    }

    @Override
    public State getInitialState() {
        return getRandomInitialState(2, 8, 5);
    }

    public State getRandomInitialState(int rows, int cols, int stones) {

        Random r = new Random();

        Set<Pos> stonePos = new HashSet<Pos>();
        while( stonePos.size() < stones ) {
            int row = (int) Math.floor(r.nextDouble() * rows);
            int col = (int) Math.floor(r.nextDouble() * cols);
            Pos p = new Pos(row, col);
            stonePos.add(p);
        }
        TonylancheState s0 = new TonylancheState(rows, cols, stonePos, 1);
        return s0;
    }

    public State getInitialStateEasy() {
        Set<Pos> stonePos = new HashSet<Pos>();

        /*
         * ...A..B.
         * ..C.D..E
         */

        stonePos.add(new Pos(0, 3));
        stonePos.add(new Pos(0, 6));
        stonePos.add(new Pos(1, 2));
        stonePos.add(new Pos(1, 7));
        TonylancheState s0 = new TonylancheState(2, 8, stonePos, 1);

        return s0;
    }

    public State getInitialStateMedium() {
        Set<Pos> stonePos = new HashSet<Pos>();

        /*
         * .A...B.
         * ..C.D.E
         * ...FG..
         */
        stonePos.add(new Pos(0, 1));
        stonePos.add(new Pos(0, 5));
        stonePos.add(new Pos(1, 2));
        stonePos.add(new Pos(1, 4));
        stonePos.add(new Pos(1, 6));
        stonePos.add(new Pos(2, 3));
        stonePos.add(new Pos(2, 4));
        TonylancheState s0 = new TonylancheState(3, 7, stonePos, 1);

        return s0;
    }

    public State getInitialStateHard() {
        Set<Pos> stonePos = new HashSet<Pos>();

        /*
         * ..AB...C..
         * ......D...
         * ..E.....F.
         * .G.H.I...J
         */
        stonePos.add(new Pos(0, 2));
        stonePos.add(new Pos(0, 3));
        stonePos.add(new Pos(0, 7));
        stonePos.add(new Pos(1, 6));
        stonePos.add(new Pos(2, 2));
        stonePos.add(new Pos(2, 9));
        stonePos.add(new Pos(3, 1));
        stonePos.add(new Pos(3, 3));
        stonePos.add(new Pos(3, 5));
        stonePos.add(new Pos(3, 9));
        TonylancheState s0 = new TonylancheState(4, 10, stonePos, 1);
        return s0;
    }


    @Override
    public int getPlayer(State s) {
        return castState(s).getPlayer();
    }

    @Override
    public int getWinner(State s) {
        return 3 - getPlayer(s);
    }

    @Override
    public List<String> getActions(State s) {
        List<String> actions = new ArrayList<String>();
        TonylancheState ts = castState(s);

        for( Character stone : ts.getPositionsOfStones().keySet() ) {
            int free = ts.freeFieldsLeftOfStone(stone);
            for( int i = 1; i <= free; i++ ) {
                actions.add(Character.toString(stone) + i);
            }
        }
        return actions;
    }

    @Override
    public State getResult(State s, String action) {
        TonylancheState ts = castState(s);

        if( action == null || action.length() < 2 ) {
            System.out.println("Invalid action");
            return null;
        }

        // Parse the action string
        char stone = action.toUpperCase().charAt(0);
        if( !ts.getPositionsOfStones().containsKey(stone) ) {
            System.out.println("Invalid stone '" + stone + "'");
            return null;
        }
        int numSteps = 0;
        try {
            numSteps = Integer.parseInt(action.substring(1));
        } catch( NumberFormatException e ) {
            System.out.println("Invalid number format '" + action.substring(1) + "'");
            return null;
        }

        // format is okay, now check if it is valid

        // allow only positive distances
        if( numSteps < 1 ) {
            System.out.println("Invalid action, a stone has to be moved by at least one field");
            return null;
        }

        // see, if it can be moved the number of steps
        if( numSteps > ts.freeFieldsLeftOfStone(stone) ) {
            System.out.println("Invalid action, stone cannot be moved that many fields");
            return null;
        }

        // move is valid, generate a new state as result of this move
        TonylancheState nextTs = (TonylancheState) ts.clone();
        nextTs.moveStone(stone, numSteps);

        return nextTs;
    }


    @Override
    public boolean isTerminalState(State s) {
        TonylancheState ts = castState(s);
        for( Character stone : ts.getPositionsOfStones().keySet() ) {
            if( ts.freeFieldsLeftOfStone(stone) > 0 ) {
                return false;
            }
        }
        return true;
    }

    @Override
    public double getUtility(State s, int p) {
        if( isTerminalState(s) ) {
            // if the current player has to moves left (terminal state), he is the loser
            return (p == getPlayer(s)) ? -1 : +1;
        }
        return 0;
    }


    public class Pos implements Comparable<Pos> {
        public final int row;
        public final int col;

        public Pos(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public int compareTo(Pos o) {
            if( this.row == o.row ) {
                return this.col - o.col;
            } else {
                return this.row - o.row;
            }
        }

        @Override
        public String toString() {
            return "(" + row + "," + col + ")";
        }

        @Override
        public boolean equals(Object o) {
            if( o == null ) {
                return false;
            }
            if( o.getClass() != Pos.class ) {
                return false;
            }
            Pos q = (Pos) o;
            return (q.col == col) && (q.row == row);
        }

        @Override
        public int hashCode() {
            int code = 7;
            code += 11 * col;
            code += 11 * 13 * row;
            return code;
        }
    }

}
