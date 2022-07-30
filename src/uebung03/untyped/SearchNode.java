package ki2.untyped;

public class SearchNode {

    private SearchNode parent;

    private State state;

    private String action;

    private double pathCosts;

    public SearchNode(SearchNode parent, State state, String action, double pathCosts) {
        this.parent = parent;
        this.state = state;
        this.action = action;
        this.pathCosts = pathCosts;
    }

    public SearchNode getParent() {
        return parent;
    }

    public State getState() {
        return state;
    }

    public String getAction() {
        return action;
    }

    public double getPathCost() {
        return pathCosts;
    }

    public String toString() {
        return (parent == null ? "[null]" : parent.getState().toString()) + " -> " + getAction() + " -> " + getState();
    }

    /**
     * Check if the given state was already visited on this path
     *
     * @param state
     * @return
     */
    public boolean contains(State state) {
        // If the given state is this state, return true
        if( this.state.equals(state) ) {
            return true;
        }
        // otherwise, continue checking the parent node (if there is one)
        else if( parent != null ) {
            return parent.contains(state);
        }
        return false;
    }
}
