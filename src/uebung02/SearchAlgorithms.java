package uebung02;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SearchAlgorithms {

	// Algorithmus für die "Breitensuche"
	public static SearchNode breadthFirstSearch(Problem prob, boolean graphSearch) {
		// Explored speichert die erforschten / erreichten Knoten
		HashSet<State> explored = new HashSet<>();
		// Frontier ist die Menge der expandierten Knoten
		List<SearchNode> frontier = new ArrayList<>();

		SearchNode start = new SearchNode(null, prob.getInitialState(), null, 0);

		// Sucht nach dem Zielknoten
		if (prob.isGoalState(start.getState())) {
			return start; // Knoten wird zurückgegeben
		}

		frontier.add(start); // Fügt den Startknoten an Frontier an
		explored.add(start.getState()); // Speichert aktuellen Knoten als Erforschten Knoten

		while (!frontier.isEmpty()) {
			SearchNode node = frontier.remove(0);

			// Liste wird hier expandiert durch Erzeugung eines neuesn SearchNode Objektes
			// in neuer Funktion
			for (SearchNode child : expand(node)) {
				State state = child.getState(); // Schreibt Status des Kindknotens in aktuellen State

				if (prob.isGoalState(state)) { // Prüft auf Zielerreichung oder nicht
					return child; // Gibt den aktuellen Kindknoten zurück
				}

				// Prüft darauf, ob der aktuelle Kindknoten in erreichten Knoten (explored)
				// enthalten ist.
				// Ohne Graphensuche
//				if (!explored.contains(state)) {
//					explored.add(state);
//					frontier.add(child);
//				}

				// Mit Graphensuche
				if (graphSearch) {
					// Graphensuche: Bereits besuchte Zustände werden hier ignoriert
					if (!explored.contains(state)) {
						explored.add(state);
						frontier.add(child);
					}
				} else {
					// Baumsuche: Hier werden immer die Folgezustände an die Frontier angehängt
					frontier.add(child); // Kindknoten werden an Frontier angehängt
				}

			}

		}

		return null;
	}


	public static SearchNode depthFirstSearch(Problem prob){
		// Erstellt ein neues Listenobjekt mit Namen Frontier
		// Alle Elemente in Frontier haben wir schon besucht
		List<SearchNode> frontier = new ArrayList<SearchNode>();

		SearchNode start = new SearchNode(null, prob.getInitialState(), null, 0);

		if(prob.isGoalState(start.getState())){
			return start;
		}

		// Prüft ob frontier gefüllt oder leer ist
		while(!frontier.isEmpty()){
			SearchNode node = frontier.remove(frontier.size() - 1);
			System.out.println("Current node: " + node);

			if(prob.isGoalState(node.getState())){
				return node;
			}

			for (SearchNode child : expand(node)){
				State state = child.getState();
				if(!node.contains(state)){
					frontier.add(child);
				}
			}
		}

		System.out.println("No solution found");
		return null;
	}
	

	// Algorthmus Teil für die Expansion des SearchNode Liste
	private static List<SearchNode> expand(SearchNode node) {

		List<SearchNode> liste = new ArrayList<>();

		State s = node.getState();
		Problem prob = s.getProblem();

		for (String string : prob.getActions(s)) {
			State succState = prob.getResults(s, string);
			// node.getPathCosts() + // Zeit die Addition der Pfadkosten von node + Problem
			SearchNode succ = new SearchNode(node, succState, string,
					node.getPathCosts() + prob.getCosts(s, string, succState));
			liste.add(succ);
		}

		return liste;
	}

}
