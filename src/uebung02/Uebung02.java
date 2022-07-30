package uebung02;

import uebung02.untyped.Problem;
import uebung02.untyped.SearchAlgorithms;
import uebung02.untyped.SearchNode;
import uebung02.untyped.staubsauger.StaubsaugerProblem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Uebung02 {

    public static void main(String[] args) {

        Problem prob = new StaubsaugerProblem(3, 1);

        System.out.println("BFS:");
        SearchNode solutionBFS = SearchAlgorithms.breadthFirstSearch(prob, true);
        printSolution(solutionBFS);

        System.out.println("\nDFS:");
        SearchNode solutionDFS = SearchAlgorithms.depthFirstSearch(prob);
        printSolution(solutionDFS);
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
