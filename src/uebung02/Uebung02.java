package uebung02;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Uebung02 {

	public static void main(String[] args) {

		Problem prob = new StaubsaugerProblem(5, 2);

		// Ausgabe aller Informationen für BestFirstSearch
		System.out.println("BFS: ");
		// Graphensuche sorgt dafür, dass jeder Knoten wirklich nur einmal besucht wird und nicht öfters
		SearchNode solutionBFS = SearchAlgorithms.breadthFirstSearch(prob, true);
		printSolution(solutionBFS);

		System.out.println("\nDFS: ");
		SearchNode solutionDFS = SearchAlgorithms.depthFirstSearch(prob);
		printSolution(solutionDFS);
	}

	public static void printSolution(SearchNode solution) {
		// Erzeugung einer neuen Liste für die Nodes
		List<SearchNode> nodes = new ArrayList<>();
		SearchNode curr = solution;

		while(curr != null ) {
			nodes.add(curr);
			curr = curr.getParent();
		}

		Collections.reverse(nodes);

		for( SearchNode n : nodes ) {
			//System.out.println(n.getAction() + " -> " + n.getState());
			System.out.println(n.getPathCosts() + " -> " + n.getState());
		}
	}


}
