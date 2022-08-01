package uebung09.adversarialsearch;

import java.util.List;

public interface Game {

    State getInitialState();

    State getInitialStateEasy();

    State getInitialStateMedium();

    State getInitialStateHard();

    int getPlayer(State s);

    List<String> getActions(State s);

    State getResult(State s, String action);

    boolean isTerminalState(State s);

    double getUtility(State s, int player);

    int getWinner(State s);
}
