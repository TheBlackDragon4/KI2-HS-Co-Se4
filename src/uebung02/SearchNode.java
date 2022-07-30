package uebung02;

public class SearchNode {

	// Elternelement -> Startzustand
	private SearchNode parent;

	// Zustand -> M�gliche Aktionen
	private State state;

	// Folgezust�nde f�r jede der Aktionen
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

	// Gibt den entsprechenden Parent Knoten zur�ck
	public SearchNode getParent() {
		return parent;
	}

	public String getAction() {
		return action;
	}

	public double getPathCosts() {
		return pathCosts;
	}

	public State getState() {
		return state;
	}

    public boolean contains(State state) {
		// Wenn der zurückgegebene Wert ein state ist, -> Rückgabe true ansonsten -> Rückgabe false
		if(this.state.equals(state)){
			return true;
		}
		// Überschreibung Fortführen mit dem nächsten Elternknoten (Wenn einer vorhanden ist)
		else if(parent != null){
			return parent.contains(state);
		}
		return false;
    }
}
