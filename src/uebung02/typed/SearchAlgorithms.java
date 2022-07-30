package uebung02.typed;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SearchAlgorithms {

    public static <P extends Problem<P, S>, S extends State<P>> SearchNode<S> breadthFirstSearch(P prob, boolean graphSearch) {
        HashSet<S> explored = new HashSet<>();
        List<SearchNode<S>> frontier = new ArrayList<>();

        SearchNode<S> start = new SearchNode<>(null, prob.getInitialState(), null, 0);

        if (prob.isGoalState(start.getState())) {
            return start;
        }

        frontier.add(start);
        explored.add(start.getState());

        while (!frontier.isEmpty()) {
            SearchNode<S> node = frontier.remove(0);

            for (SearchNode<S> child : expand(node)) {
                S state = child.getState();
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

    public static <P extends Problem<P, S>, S extends State<P>> SearchNode<S> depthFirstSearch(P prob) {
        List<SearchNode<S>> frontier = new ArrayList<>();

        SearchNode<S> start = new SearchNode<>(null, prob.getInitialState(), null, 0);

        if (prob.isGoalState(start.getState())) {
            return start;
        }

        frontier.add(start);

        while (!frontier.isEmpty()) {
            SearchNode<S> node = frontier.remove(frontier.size() - 1);
            System.out.println("Current node: " + node);
            if (prob.isGoalState(node.getState())) {
                return node;
            }

            for (SearchNode<S> child : expand(node)) {
                S state = child.getState();
                if (!node.contains(state)) {
                    frontier.add(child);
                }
            }
        }
        System.out.println("No solution found");
        return null;
    }


    protected static <P extends Problem<P, S>, S extends State<P>> List<SearchNode<S>> expand(SearchNode<S> node) {
        List<SearchNode<S>> list = new ArrayList<>();

        S s = node.getState();
        P prob = s.getProblem();

        for (String a : prob.getActions(s)) {
            S succState = prob.getResult(s, a);
            SearchNode<S> succ = new SearchNode<>(node, succState, a, node.getPathCost() + prob.getCost(s, a, succState));
            list.add(succ);
        }

        return list;
    }
}
