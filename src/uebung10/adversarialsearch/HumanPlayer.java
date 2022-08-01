package uebung10.adversarialsearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * A simple player that gets input from the console.
 *
 * @author Florian Mittag
 */
public class HumanPlayer implements Player {

    @Override
    public String selectMove(Game g, State s) {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String str = null;

        try {
            str = br.readLine();
        } catch( IOException ioe ) {
            // won't happen too often from the keyboard
        }

        return str;
    }

}
