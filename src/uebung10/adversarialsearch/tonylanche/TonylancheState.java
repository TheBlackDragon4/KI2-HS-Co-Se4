package uebung10.adversarialsearch.tonylanche;

import uebung10.adversarialsearch.State;
import uebung10.adversarialsearch.Uebung10;
import uebung10.adversarialsearch.tonylanche.Tonylanche.Pos;

import java.util.*;

public class TonylancheState implements State, Cloneable {

    protected int rows;
    protected int cols;
    protected char[][] board;
    protected int current_player;
    protected Map<Character, Pos> posOfStone;

    protected int depth;

    public TonylancheState(int rows, int cols, Set<Pos> stonePositions, int player) {
        this.depth = 0;
        this.rows = rows;
        this.cols = cols;
        current_player = player;
        board = new char[rows][cols];

        posOfStone = new TreeMap<Character, Pos>();
        TreeSet<Pos> sortedPos = new TreeSet<Pos>(stonePositions);
        char stone = 'A';
        for( Pos pos : sortedPos ) {
            posOfStone.put(stone, pos);
            stone++;
        }

        // clear the board
        for( int i = 0; i < board.length; i++ ) {
            for( int j = 0; j < board[i].length; j++ ) {
                board[i][j] = '.';
            }
        }
        for( Character c : posOfStone.keySet() ) {
            Pos pos = posOfStone.get(c);
            board[pos.row][pos.col] = c;
        }
    }

    private TonylancheState() {
        // intentionally left blank, for cloning
    }

    public Object clone() {
        TonylancheState cloneTs = new TonylancheState();
        cloneTs.depth = depth;
        cloneTs.rows = rows;
        cloneTs.cols = cols;
        cloneTs.board = new char[rows][];
        for( int i = 0; i < rows; i++ ) {
            cloneTs.board[i] = Arrays.copyOf(board[i], cols);
        }
        cloneTs.posOfStone = new TreeMap<Character, Pos>(posOfStone);
        cloneTs.current_player = current_player;
        return cloneTs;
    }

    public char[][] getBoard() {
        return board;
    }

    /**
     * Returns the number of fields a stone can be moved to the left
     *
     * @param stone
     * @return
     */
    protected int freeFieldsLeftOfStone(char stone) {
        Pos p = posOfStone.get(stone);
        int col = p.col - 1;
        int free = 0;

        while( col >= 0 && board[p.row][col] == '.' ) {
            free++;
            col--;
        }

        return free;
    }

    public Map<Character, Pos> getPositionsOfStones() {
        return Collections.unmodifiableMap(posOfStone);
    }

    public void moveStone(char stone, int numSteps) {
        Pos p = posOfStone.get(stone);
        Pos q = (new Tonylanche()).new Pos(p.row, p.col - numSteps);
        board[p.row][p.col] = '.';
        board[q.row][q.col] = stone;
        posOfStone.put(stone, q);

        // change current player
        current_player = 3 - current_player;
        depth++;
        Uebung10.state_counter++;
    }

    public int getPlayer() {
        return current_player;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for( int i = 0; i < board.length; i++ ) {
            for( int j = 0; j < board[i].length; j++ ) {
                sb.append(board[i][j]);
            }
            sb.append('\n');
        }
        sb.append("Player ").append(current_player).append("'s turn\n");
        return sb.toString();
    }

    @Override
    public int getDepth() {
        return depth;
    }

}
