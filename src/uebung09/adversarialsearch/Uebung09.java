package uebung09.adversarialsearch;

import uebung09.adversarialsearch.viergewinnt.VierGewinnt;

/**
 * Main class for launching a game.
 *
 * @author Florian Mittga
 */
public class Uebung09 {

    public static int state_counter = 0;

    /**
     * @param args
     */
    public static void main(String[] args) {
        VierGewinnt game = new VierGewinnt();

        //State s = game.getInitialState();
        //State s = game.getInitialStateEasy();
        State s = game.getInitialStateMedium();     // Entspricht Aufgabe 2 von Übung 9
        //State s = game.getInitialStateHard();

        Player p1 = new HumanPlayer();
        Player p2 = new HumanPlayer();
        //Player p1 = new GeneralMinMaxPlayer(1);
        //Player p2 = new GeneralMinMaxPlayer(2);

        // While the game is not finished
        while( !game.isTerminalState(s) ) {
            // print state
            System.out.println(s);
            // print available actions
            System.out.println(game.getActions(s));

            String action = null;
            State nextState = null;
            state_counter = 0;

            // repeat until a valid move is selected (for human players)
            while( nextState == null ) {

                // player chooses move
                if( game.getPlayer(s) == 1 ) {
                    if( p1 instanceof HumanPlayer ) {
                        System.out.println("Player 1, it's your move\n");
                    }
                    action = p1.selectMove(game, s);
                } else {
                    if( p2 instanceof HumanPlayer ) {
                        System.out.println("Player 2, it's your move\n");
                    }
                    action = p2.selectMove(game, s);
                }
                nextState = game.getResult(s, action);

            }
            System.out.println("\nMove: " + action + " after " + (state_counter - 1) + " examined states\n");

            // go to next state by executing the chosen move
            s = nextState;
        }

        // Declare that the game is over and the winner is...
        System.out.println(s);
        int winner = game.getWinner(s);
        if( winner != 0 )
            System.out.println("\n\nPlayer " + winner + " wins!");
        else
            System.out.println("\n\nThe game is a draw.");

    }

}
