package uebung02.typed;

public interface State<P extends Problem<?, ?>> {
    P getProblem();
}
