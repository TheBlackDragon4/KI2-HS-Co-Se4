package uebung02.typed;

import java.util.List;

public interface Problem<P extends Problem<P, S>, S extends State<P>> {

    S getInitialState();

    List<String> getActions(S s);

    S getResult(S s, String action);

    boolean isGoalState(S s);

    double getCost(S s, String action, S succ);
}
