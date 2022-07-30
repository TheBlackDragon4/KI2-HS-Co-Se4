package uebung01;

public class SearchNode {

	// Elternelement -> Startzustand
	private SearchNode parent;

	// Zustand -> Mögliche Aktionen
	private State state;

	// Folgezustände für jede der Aktionen
	private String action;

	// Zielzustand oder die Definition
	private double pathCosts;

	// Konstruktor der Klasse SearchNode
	public SearchNode(SearchNode parent, State state, String action, double pathCosts) {
		this.parent = parent;
		this.state = state;
		this.action = action;
		this.pathCosts = pathCosts;
	}

}
