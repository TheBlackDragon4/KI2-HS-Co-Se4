package ki2.untyped;

import java.util.List;

public interface Problem {

    State getInitialState();

    List<String> getActions(State s);

    State getResult(State s, String action);

    boolean isGoalState(State s);

    double getCost(State s, String action, State succ);
}
