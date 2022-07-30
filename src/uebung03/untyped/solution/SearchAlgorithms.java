package uebung03.untyped.solution;

import uebung03.untyped.Problem;
import uebung03.untyped.SearchNode;
import uebung03.untyped.State;

import java.util.*;
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
        return depthFirstSearch(prob, Integer.MAX_VALUE);
    }

    public static SearchNode depthFirstSearch(Problem prob, int limit) {

        // TODO: implement
        List<SearchNode> frontier = new ArrayList<>();

        SearchNode start = new SearchNode(null, prob.getInitialState(), null, 0);

        if(prob.isGoalState(start.getState())) {
            return start;
        }

        frontier.add(start);

        while (!frontier.isEmpty()) {
            SearchNode node = frontier.remove(frontier.size() - 1);
            System.out.println("Current node: " + node);
            if(prob.isGoalState(node.getState())) {
                return node;
            }

            if(getDepth(node) < limit) {
                for (SearchNode child : expand(node)) {
                    State state = child.getState();
                    if (!node.contains(state)){
                        frontier.add(child);
                    }
                }
            }
        }
        System.out.println("No solution found");
        return null;
    }

    public static SearchNode iterativeDFS(Problem prob) {

        // TODO: implement
        for(int l = 0 ; l < 1000; l++){
            System.out.println("\nStarting depth-limit DFS with limit " + l);
            SearchNode solution = depthFirstSearch(prob, l);
            if(solution != null){
                return solution;
            }
        }
        return null;
    }

    public static SearchNode bestFirstSearch(Problem prob, Function<SearchNode, Double> f) {

        // TODO: Implement
        HashMap<State, SearchNode> explored = new HashMap<>();
        PriorityQueue<SearchNode> frontier = new PriorityQueue<SearchNode>((o1, o2) -> (int)Math.round(f.apply(o1) - f.apply(o2)));

        SearchNode start = new SearchNode(null, prob.getInitialState(), null, 0);

        if(prob.isGoalState(start.getState())) {
            return start;
        }

        frontier.add(start);
        explored.put(start.getState(), start);

        while (!frontier.isEmpty()){
            for(SearchNode n : frontier) {
                System.out.println("* " + n.getState() + ": f(n) = " + f.apply(n));
            }
            SearchNode node = frontier.remove();
            System.out.println("Expand node: " + node);

            if(prob.isGoalState(node.getState())){
                return node;
            }

            for (SearchNode child : expand(node)) {
                State state = child.getState();

                if (!explored.containsKey(state) || child.getPathCost() < explored.get(state).getPathCost()) {
                    if(!explored.containsKey(state)) {
                        System.out.println("Path to " + state + " (new) with cost: " + child.getPathCost());
                    } else {
                        System.out.println("Path to " + state + " (better)  with cost: " + child.getPathCost());
                    }
                    explored.put(state, child);
                    frontier.add(child);
                }
            }
        }

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

    protected static int getDepth(SearchNode node) {
        if(node.getParent() == null) {
            return 0;
        }
        return getDepth(node.getParent()) + 1;
    }
}
