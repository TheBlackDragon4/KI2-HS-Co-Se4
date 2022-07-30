package ki2.untyped;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;

public class SearchAlgorithms {

    public static SearchNode breadthFirstSearch(Problem prob, boolean graphSearch) {
        HashSet<State> explored = new HashSet<>();
        List<SearchNode> frontier = new ArrayList<>();

        SearchNode start = new SearchNode(null, prob.getInitialState(), null, 0);

        if (prob.isGoalState(start.getState())) {
            return start;
        }

        frontier.add(start);
        explored.add(start.getState());

        while (!frontier.isEmpty()) {
            SearchNode node = frontier.remove(0);

            for (SearchNode child : expand(node)) {
                State state = child.getState();
                if (prob.isGoalState(state)) {
                    return child;
                }

                if (graphSearch) {
                    // Graphensuche: Bereits besuchte Zustände ignorieren
                    if (!explored.contains(state)) {
                        explored.add(state);
                        frontier.add(child);
                    }
                } else {
                    // Baumsuche: Immer den Folgezustand zur Frontier hinzufügen
                    frontier.add(child);
                }
            }
        }
        return null;
    }

    public static SearchNode depthFirstSearch(Problem prob) {
        List<SearchNode> frontier = new ArrayList<>();

        SearchNode start = new SearchNode(null, prob.getInitialState(), null, 0);

        if (prob.isGoalState(start.getState())) {
            return start;
        }

        frontier.add(start);

        while (!frontier.isEmpty()) {
            SearchNode node = frontier.remove(frontier.size() - 1);
            System.out.println("Current node: " + node);
            if (prob.isGoalState(node.getState())) {
                return node;
            }

            for (SearchNode child : expand(node)) {
                State state = child.getState();
                if (!node.contains(state)) {
                    frontier.add(child);
                }
            }
        }
        System.out.println("No solution found");
        return null;
    }

    public static SearchNode depthFirstSearch(Problem prob, int limit) {

        // TODO: implement

        return null;
    }

    public static SearchNode iterativeDFS(Problem prob) {

        // TODO: implement

        return null;
    }

    public static SearchNode bestFirstSearch(Problem prob, Function<SearchNode, Double> f) {

        // TODO: Implement

        return null;
    }


    protected static List<SearchNode> expand(SearchNode node) {
        List<SearchNode> list = new ArrayList<>();

        State s = node.getState();
        Problem prob = s.getProblem();

        for (String a : prob.getActions(s)) {
            State succState = prob.getResult(s, a);
            SearchNode succ = new SearchNode(node, succState, a, node.getPathCost() + prob.getCost(s, a, succState));
            list.add(succ);
        }

        return list;
    }
}
