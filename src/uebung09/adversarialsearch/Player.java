package uebung09.adversarialsearch;

/**
 * The interface that defines the properties of a Player
 *
 * @author Florian Mittag
 */
public interface Player {

    String selectMove(Game g, State s);
}
