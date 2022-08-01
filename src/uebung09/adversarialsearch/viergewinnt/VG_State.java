package uebung09.adversarialsearch.viergewinnt;

import uebung09.adversarialsearch.State;
import uebung09.adversarialsearch.Uebung09;

/**
 * Implements the board state for the Vier Gewinnt game.
 *
 * @author Blaine Nelson
 */
public class VG_State implements State {
    private int width;        // width of board
    private int height;        // height of board
    private int[] nextIndex;            // next available row in each column
    private int[] boardStateNCounts;    // current position of discs on the board along with other board meta-information

    private int currentPlayer;    // current player (1 or 2)
    private int winner;            // winner of the game (< 0 means game not done, 0 means draw, 1 & 2 indicate the winning player)
    private int consecToWin;    // number of consecutive discs needed to win
    private int depth;            // depth in the game (i.e., number of discs placed.


    /**
     * Indices used for meta-information.
     **/
    private static final int NUM_STATES = 5;
    private static final int STATE = 0;
    private static final int FROM_LEFT = 1;
    private static final int FROM_BOTTOM = 2;
    private static final int FROM_POS_DIAG = 3;
    private static final int FROM_NEG_DIAG = 4;

    /**
     * Constructor for an empty Vier Gewinnt board.
     **/
    public VG_State(int height, int width, int numNeededToWin) {
        assert height > 0 && width > 0;
        this.height = height;
        this.width = width;
        this.nextIndex = new int[width];
        for( int i = 0; i < this.width; i++ ) this.nextIndex[i] = 0;
        this.boardStateNCounts = new int[width * height * VG_State.NUM_STATES];
        for( int j = 0; j < this.boardStateNCounts.length; j++ ) this.boardStateNCounts[j] = 0;
        this.winner = -1;
        this.consecToWin = numNeededToWin;
        this.depth = 0;
        this.currentPlayer = 1;
    }

    /**
     * Method used by clone (creates a basic/unstable game state... do not use)
     **/
    private VG_State() {
    }

    /**
     * Helper method returns the depth in the game.
     **/
    @Override
    public int getDepth() {
        return this.depth;
    }

    /**
     * Helper returns the winner of the game (< 0 means game not done, 0 means draw, 1 & 2 indicate the winning player)
     **/
    public int getWinner() {
        return this.winner;
    }

    /**
     * Helper returns the current player.
     **/
    public int getPlayer() {
        return this.currentPlayer;
    }

    /**
     * Helper returns the number of columns in the game.
     **/
    public int numColumns() {
        return this.width;
    }

    /**
     * Helper method that returns the deepest possible depth.
     **/
    public int maxDepth() {
        return this.width * this.height;
    }

    /**
     * Helper method that indicates whether a disc can be placed within a column.
     **/
    public boolean isColumnFree(int c) {
        return (this.nextIndex[c] < this.height);
    }
    /** Helper method that indicates whether or not the board is completely full. **/
//	public boolean boardFull() { 
//		for(int c = 0; c < this.width; c++) 
//			if(isColumnFree(c)) return false; 
//		return true; 
//	}

    /**
     * Places a disc into the Vier Gewinnt board.
     **/
    public int placeDisc(int pos) {
        if( pos < 0 || pos >= this.width || this.nextIndex[pos] == this.height )
            return -1;

        this.addPiece(this.nextIndex[pos], pos, this.currentPlayer);
        this.nextIndex[pos]++;
        this.depth++;

        if( this.depth == maxDepth() && this.winner < 0 ) this.winner = 0;
        this.currentPlayer = 3 - this.currentPlayer;
        Uebung09.state_counter++;
//		if(Uebung7.state_counter % 1000000 == 0)
//			System.out.println("Millions of states: " + Uebung7.state_counter / 1000000);
        return 0;
    }

    /**
     * Clones a Vier Gewinnt board.
     **/
    public Object clone() {
        VG_State ret = new VG_State();

        ret.height = this.height;
        ret.width = this.width;
        ret.nextIndex = new int[ret.width];
        System.arraycopy(this.nextIndex, 0, ret.nextIndex, 0, this.nextIndex.length);
        ret.boardStateNCounts = new int[this.boardStateNCounts.length];
        System.arraycopy(this.boardStateNCounts, 0, ret.boardStateNCounts, 0, this.boardStateNCounts.length);
        ret.winner = this.winner;
        ret.consecToWin = this.consecToWin;
        ret.depth = this.depth;
        ret.currentPlayer = this.currentPlayer;

        return ret;
    }

    /**
     * String representation of a Vier Gewinnt board.
     **/
    public String toString() {
        String ret = "";
        for( int i = this.height - 1; i >= 0; i-- ) {
            ret += "|";
            for( int j = 0; j < this.width; j++ ) {
                switch( this.boardStateNCounts[this.loc_lookup(i, j) + VG_State.STATE] ) {
                    case 0:
                        ret += ".";
                        break;
                    case 1:
                        ret += "X";
                        break;
                    case 2:
                        ret += "0";
                        break;
                }
            }
            ret += "|\n";
        }
        return ret;
    }


    /*********************
     *********************
     ** PRIVATE METHODS **
     *********************
     *********************/

    private final int loc_lookup(int row, int col) {
        if( row < 0 || col < 0 || row >= this.height || col >= this.width ) return -1;
        return (VG_State.NUM_STATES * this.width) * row + (VG_State.NUM_STATES) * col;
    }

    private final void addPiece(int row, int col, int playerNum) {
        this.boardStateNCounts[loc_lookup(row, col) + VG_State.STATE] = playerNum;
        int maxLineCount = 0;

        maxLineCount = Math.max(maxLineCount, this.updateCount(row, col, VG_State.FROM_LEFT));
        maxLineCount = Math.max(maxLineCount, this.updateCount(row, col, VG_State.FROM_BOTTOM));
        maxLineCount = Math.max(maxLineCount, this.updateCount(row, col, VG_State.FROM_POS_DIAG));
        maxLineCount = Math.max(maxLineCount, this.updateCount(row, col, VG_State.FROM_NEG_DIAG));

        if( maxLineCount >= this.consecToWin ) this.winner = playerNum;
    }

    private final int updateCount(int row, int col, int direction) {
        int loc = -1;
        int pred = -1;
        int next = -1, nr = -1, nc = -1;

        loc = loc_lookup(row, col);
        switch( direction ) {
            case VG_State.FROM_LEFT:
                pred = loc_lookup(row, col - 1);
                next = loc_lookup(row, col + 1);
                nr = row;
                nc = col + 1;
                break;
            case VG_State.FROM_BOTTOM:
                pred = loc_lookup(row - 1, col);
                next = loc_lookup(row + 1, col);
                nr = row + 1;
                nc = col;
                break;
            case VG_State.FROM_POS_DIAG:
                pred = loc_lookup(row - 1, col - 1);
                next = loc_lookup(row + 1, col + 1);
                nr = row + 1;
                nc = col + 1;
                break;
            case VG_State.FROM_NEG_DIAG:
                pred = loc_lookup(row + 1, col - 1);
                next = loc_lookup(row - 1, col + 1);
                nr = row - 1;
                nc = col + 1;
                break;
        }
        this.boardStateNCounts[loc + direction] = 1;
        if( pred >= 0 && this.boardStateNCounts[loc + VG_State.STATE] == this.boardStateNCounts[pred + VG_State.STATE] ) {
            this.boardStateNCounts[loc + direction] += this.boardStateNCounts[pred + direction];
        }

        while( next >= 0 && this.boardStateNCounts[loc + VG_State.STATE] == this.boardStateNCounts[next + VG_State.STATE] ) {
            pred = loc;
            row = nr;
            col = nc;
            loc = loc_lookup(row, col);
            switch( direction ) {
                case VG_State.FROM_LEFT:
                    next = loc_lookup(row, col + 1);
                    nr = row;
                    nc = col + 1;
                    break;
                case VG_State.FROM_BOTTOM:
                    next = loc_lookup(row + 1, col);
                    nr = row + 1;
                    nc = col;
                    break;
                case VG_State.FROM_POS_DIAG:
                    next = loc_lookup(row + 1, col + 1);
                    nr = row + 1;
                    nc = col + 1;
                    break;
                case VG_State.FROM_NEG_DIAG:
                    next = loc_lookup(row - 1, col + 1);
                    nr = row - 1;
                    nc = col + 1;
                    break;
            }
            this.boardStateNCounts[loc + direction] = 1 + this.boardStateNCounts[pred + direction];
        }
        return this.boardStateNCounts[loc + direction];
    }
}
