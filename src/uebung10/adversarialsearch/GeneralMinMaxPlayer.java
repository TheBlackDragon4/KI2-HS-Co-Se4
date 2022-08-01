package uebung10.adversarialsearch;


import java.util.List;

public class GeneralMinMaxPlayer implements Player {

    public int myPlayerNo;

    public GeneralMinMaxPlayer(int playerNo) {
        this.myPlayerNo = playerNo;
    }

    @Override
    public String selectMove(Game g, State s) {
//        return getMinimaxDecision(g, s);
        return getMinimaxAlphaBetaDecision(g, s);
    }

    public String getMinimaxDecision(Game g, State s) {
        List<String> actions = g.getActions(s);

        // we want to maximize our move
        String bestAction = null;
        double bestValue = Double.NEGATIVE_INFINITY;

        for( String a : actions ) {
            double value = getMinValue(g, g.getResult(s, a));
            System.out.println("Move " + a + ": " + value);
            if( value > bestValue ) {
                bestValue = value;
                bestAction = a;
            }
        }
        return bestAction;
    }


    public double getMinValue(Game g, State s) {
        if( g.isTerminalState(s) ) {
            return g.getUtility(s, myPlayerNo);
        }
        double v = Double.POSITIVE_INFINITY;
        List<String> actions = g.getActions(s);
        for( String a : actions ) {
            v = Math.min(v, getMaxValue(g, g.getResult(s, a)));
        }
        return v;
    }

    public double getMaxValue(Game g, State s) {
        if( g.isTerminalState(s) ) {
            return g.getUtility(s, myPlayerNo);
        }
        double v = Double.NEGATIVE_INFINITY;
        List<String> actions = g.getActions(s);
        for( String a : actions ) {
            v = Math.max(v, getMinValue(g, g.getResult(s, a)));
        }
        return v;
    }

    public String getMinimaxAlphaBetaDecision(Game g, State s) {
        List<String> actions = g.getActions(s);

        // we want to maximize our move
        String bestAction = null;
        double bestValue = Double.NEGATIVE_INFINITY;

        double alpha = Double.NEGATIVE_INFINITY;
        double beta = Double.POSITIVE_INFINITY;

        for( String a : actions ) {
            double value = getMinValue(g, g.getResult(s, a), alpha, beta);
            System.out.println("Move " + a + ": " + value);
            if( value > bestValue ) {
                bestValue = value;
                bestAction = a;
                alpha = Math.max(alpha, bestValue);
            }
            if( bestValue >= beta ) {
                return bestAction;
            }
        }
        // only the move is relevant, not the value
        return bestAction;
    }


    public double getMinValue(Game g, State s, double alpha, double beta) {
        if( g.isTerminalState(s) ) {
            return g.getUtility(s, myPlayerNo);
        }
        double v = Double.POSITIVE_INFINITY;
        List<String> actions = g.getActions(s);
        for( String a : actions ) {
            double v2 = getMaxValue(g, g.getResult(s, a), alpha, beta);
            if( v2 < v ) {
                v = v2;
                beta = Math.min(beta, v);
            }
            if( v <= alpha ) {
                return v;
            }
        }
        // only the value is relevant for "deeper" states, not the move
        return v;
    }

    public double getMaxValue(Game g, State s, double alpha, double beta) {
        if( g.isTerminalState(s) ) {
            //System.out.println(s.getDepth());
            return g.getUtility(s, myPlayerNo);
        }
        double v = Double.NEGATIVE_INFINITY;
        List<String> actions = g.getActions(s);
        for( String a : actions ) {
            double v2 = getMinValue(g, g.getResult(s, a), alpha, beta);
            if ( v2 > v ) {
                v = v2;
                alpha = Math.max(alpha, v);
            }
            if( v >= beta ) {
                return v;
            }
        }
        // only the value is relevant for "deeper" states, not the move
        return v;
    }
}
