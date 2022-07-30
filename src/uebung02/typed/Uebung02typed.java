package uebung02.typed;

import uebung02.typed.staubsauger.StaubsaugerProblem;
import uebung02.typed.staubsauger.StaubsaugerState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Uebung02typed {

    public static void main(String[] args) {

        StaubsaugerProblem prob = new StaubsaugerProblem(3, 1);

        System.out.println("BFS:");
        SearchNode<StaubsaugerState> solutionBFS = SearchAlgorithms.breadthFirstSearch(prob, true);
        printSolution(solutionBFS);

        System.out.println("\nDFS:");
        SearchNode<StaubsaugerState> solutionDFS = SearchAlgorithms.depthFirstSearch(prob);
        printSolution(solutionDFS);
    }

    public static <P extends Problem<P, S>, S extends State<P>> void printSolution(SearchNode<S> solution) {
        List<SearchNode<S>> nodes = new ArrayList<>();
        SearchNode<S> curr = solution;
        while(curr != null ) {
            nodes.add(curr);
            curr = curr.getParent();
        }

        Collections.reverse(nodes);

        for( SearchNode<S> n : nodes ) {
            //System.out.println(n.getAction() + " -> " + n.getState());
            System.out.println(n.getPathCost() + " -> " + n.getState());
        }
    }
}
