package uebung02;

import java.util.List;

public interface Problem {
	// Angabe der 5 Dinge die ein Problem definieren wurde hier deklariert

	// Menge S (Zuständen) inklusive den Startzustand - Gibt den Status zurück
	public State getInitialState();

	// Gib mit die Liste von verschiedenen Aktionen zurück (Je nachdem was für ein
	// Problem behandelt werden muss)
	List<String> getActions(State s);

	// Gib mir das Result der Aktion zurück -> Übergabe ist der State s und die
	// jeweilige Aktion
	public State getResults(State s, String action);

	// Prüft ob ich schon am Ziel bin oder nicht -> Boolean Wert true oder false
	public boolean isGoalState(State s);

	// Pfadkosten mit State, Aktion und Schrittkosten (Successor)
	// Pfadkosten eines Pfades sind die Summe der Schrittkosten der einzelnen
	// Aktionen des Pfades
	// Schrittkosten müssen nach jeder Aktion mit Counter um 1 erhöht werden.
	public double getCosts(State s, String action, State succ);

}
