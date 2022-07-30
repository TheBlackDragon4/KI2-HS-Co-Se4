package uebung02;

import java.util.List;

public interface Problem {
	// Angabe der 5 Dinge die ein Problem definieren wurde hier deklariert

	// Menge S (Zust�nden) inklusive den Startzustand - Gibt den Status zur�ck
	public State getInitialState();

	// Gib mit die Liste von verschiedenen Aktionen zur�ck (Je nachdem was f�r ein
	// Problem behandelt werden muss)
	List<String> getActions(State s);

	// Gib mir das Result der Aktion zur�ck -> �bergabe ist der State s und die
	// jeweilige Aktion
	public State getResults(State s, String action);

	// Pr�ft ob ich schon am Ziel bin oder nicht -> Boolean Wert true oder false
	public boolean isGoalState(State s);

	// Pfadkosten mit State, Aktion und Schrittkosten (Successor)
	// Pfadkosten eines Pfades sind die Summe der Schrittkosten der einzelnen
	// Aktionen des Pfades
	// Schrittkosten m�ssen nach jeder Aktion mit Counter um 1 erh�ht werden.
	public double getCosts(State s, String action, State succ);

}
