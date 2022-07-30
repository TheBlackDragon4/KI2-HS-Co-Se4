package uebung05.hillclimbing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class HillClimbing {

    public static void main(String[] args){

        NQueensProblem prob1 = new NQueensProblem(8, new int[] {5,3,1,8,8,6,8,1});
        NQueensProblem prob2 = new NQueensProblem(8, new int[] {7,2,6,1,1,8,5,3});
        NQueensProblem prob3 = new NQueensProblem(8, new int[] {7,8,2,6,6,1,7,2});

        NQueensProblem prob0 = new NQueensProblem(8);

        NQueensProblem prob = prob1; // Wahl des Problems

        // HillClimbing.hillClimbing(prob);
        // HillClimbing.hillClimbingSideway(prob, 25);
        HillClimbing.simulatedAnnealing(prob);

    }

    private static <P extends ProblemState<P>> P hillClimbing(P state) {

        P currState = state;
        System.out.println("Start hillclimbing from: " + state + " " + state.evaluate());

        List<P> improvements = new ArrayList<>();
        Random rnd = new Random();

        do{
            double currValue = currState.evaluate();
            double bestValue = currValue;

            Collection<P> succs = currState.getSuccessors();
            for(var next : succs){
                double v = next.evaluate();
                if(v > bestValue){
                    bestValue = v;
                }
            }

            improvements.clear();
            if( bestValue > currValue ) {
                for( var next : succs ) {
                    double v = next.evaluate();
                    if( v == bestValue ) {
                        improvements.add(next);
                    }
                }
            }

            if( !improvements.isEmpty() ) {
                currState = improvements.get(rnd.nextInt(improvements.size()));
                System.out.println("Next best state: " + currState.toString() + " - " + currState.evaluate());
            }
        } while( !improvements.isEmpty() );

        return currState;

    }

    public static <P extends ProblemState<P>> P hillClimbSideways(P state, int max) {

        P currState = state;
        System.out.println("Start hillclimbing from: " + state + " " + state.evaluate());

        List<P> improvements = new ArrayList<>();
        int counter = 0;
        Random rnd = new Random();

        do {
            double currValue = currState.evaluate();
            double bestValue = currValue;

            Collection<P> succs = currState.getSuccessors();
            for( var next : succs ) {
                double v = next.evaluate();
                if( v > bestValue ) {
                    bestValue = v;
                }
            }

            improvements.clear();
            if( bestValue >= currValue ) {
                for( var next : succs ) {
                    double v = next.evaluate();
                    if( v == bestValue ) {
                        improvements.add(next);
                    }
                }
            }

            if( !improvements.isEmpty() ) {
                currState = improvements.get(rnd.nextInt(improvements.size()));
                System.out.println("Next best state: " + currState.toString() + " - " + currState.evaluate());
            }

            if( bestValue == currValue) {
                counter++;
            } else {
                counter = 0;
            }
        } while( !improvements.isEmpty() && counter < max);

        if( counter >= max ) {
            System.out.println("Abbruch wegen counter >= max (" + max + ")");
        }

        return currState;
    }

    public static <P extends ProblemState<P>> P simulatedAnnealing(P state) {

        P currState = state;
        System.out.println("Start simulated annealing from: " + state + " " + state.evaluate());
        double bestValue = 0;

        double T = Double.POSITIVE_INFINITY;
        double tLimit = 0.01;
        int counter = 0;
        Random rnd = new Random();

        do {
            counter++;
            T = 100.0 * Math.pow(1.8, -counter);
//            System.out.println("Temperatur = " + T);

            double currValue = currState.evaluate();
            if( currValue > bestValue ) {
                bestValue = currValue;
            }

            List<P> succs = new ArrayList<>(currState.getSuccessors());
            P next = succs.get(rnd.nextInt(succs.size()));

            // Wir suchen hier ein Maximum, also müssen wir den Wert invertieren
            double diffE = -(currValue - next.evaluate());
            if( diffE > 0) {
                currState = next;
                System.out.println("Next best state: " + currState.toString() + " - " + currState.evaluate());
            } else {
                double p = Math.exp(diffE/T);
                //System.out.println("p = " + p);
                if( rnd.nextDouble() <= p ) {
                    currState = next;
                    System.out.println("Next best state: " + currState.toString() + " - " + currState.evaluate());
                }
            }
        } while( T > tLimit );

        System.out.println("Best value was " + bestValue);
        return currState;
    }

}
