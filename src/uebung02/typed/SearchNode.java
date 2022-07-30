package uebung02.typed;

public class SearchNode<S extends State<?>> {

    private SearchNode<S> parent;

    private S state;

    private String action;

    private double pathCosts;

    public SearchNode(SearchNode<S> parent, S state, String action, double pathCosts) {
        this.parent = parent;
        this.state = state;
        this.action = action;
        this.pathCosts = pathCosts;
    }

    public SearchNode<S> getParent() {
        return parent;
    }

    public S getState() {
        return state;
    }

    public String getAction() {
        return action;
    }

    public double getPathCost() {
        return pathCosts;
    }

    /**
     * Check if the given state was already visited on this path
     *
     * @param state
     * @return
     */
    public boolean contains(S state) {
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
