package uebung05.hillclimbing;

public class Uebung05 {
    public static void main(String[] args) {
        NQueensProblem prob = new NQueensProblem(8);
        System.out.println(prob);

        // Alle Nachbarzustände erzeugen und anzeigen
        var succs = prob.getSuccessors();
        for( var state : succs ) {
            System.out.println(state + " - " + state.numNonConflictingPairs());
        }

        // TODO: Hill Climbing

        // TODO: Simulated Annealing

        ProblemState current = prob;

        ProblemState bestState = null;
        double bestValue = Double.NEGATIVE_INFINITY;

        while(true) {

            for (ProblemState state : prob.getSuccessors()) {
                double v = prob.evaluate(); // Werte Evaluieren und Vergeleichen

                if (v > bestValue) {
                    bestState = state; // Hier steht ein komplett neuer Zustand
                    bestValue = v;
                }

            }

            if( bestState == null){
                break;
            }
            current = bestState;

            System.out.println(bestState + " - " + bestState.evaluate());

        }

        // Siehe Weiteres Vorlesungsaufzeichnung + evtl. LösungsUpload




    }

}
