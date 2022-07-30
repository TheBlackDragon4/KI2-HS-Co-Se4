package uebung03;

import uebung03.untyped.solution.DistanceUtil;
import uebung03.untyped.solution.SearchAlgorithms;
import uebung03.untyped.SearchNode;
import uebung03.untyped.route.GermanyRouteProblem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Uebung03 {

    public static void main(String[] args) {

        //GermanyRouteProblem prob = new GermanyRouteProblem("Aachen", "Leipzig");
        //GermanyRouteProblem prob = new GermanyRouteProblem("Hamburg", "München");
        GermanyRouteProblem prob = new GermanyRouteProblem("Stuttgart", "Leipzig");

        //SearchNode solution = SearchAlgorithms.breadthFirstSearch(prob, true);
        //SearchNode solution = SearchAlgorithms.depthFirstSearch(prob);
        //SearchNode solution = SearchAlgorithms.depthFirstSearch(prob, 4);
        //SearchNode solution = SearchAlgorithms.iterativeDFS(prob);

        // Uniform Cost Search
        //SearchNode solution = SearchAlgorithms.bestFirstSearch(prob, (node) -> node.getPathCost()); // TODO

        // Greedy Best-First Search
        SearchNode solution = SearchAlgorithms.bestFirstSearch(prob,
                (node) -> prob.getDistance(node.getState().toString(), "Freiburg"));

        // A* Search
        //SearchNode solution = SearchAlgorithms.bestFirstSearch(prob,
        //        (node) -> node.getPathCost() + prob.getDistance(node.getState().toString(), "Freiburg"));

        printSolution(solution);
    }

    public static void printSolution(SearchNode solution) {
        List<SearchNode> nodes = new ArrayList<>();
        SearchNode curr = solution;
        while(curr != null ) {
            nodes.add(curr);
            curr = curr.getParent();
        }

        Collections.reverse(nodes);

        for( SearchNode n : nodes ) {
            //System.out.println(n.getAction() + " -> " + n.getState());
            System.out.println(n.getPathCost() + " -> " + n.getState());
        }
    }
}
