package uebung01;

import java.util.List;
import java.util.Scanner;

public class Uebung01 {

	public static void main(String[] args) {

		Problem prob = new StaubsaugerProblem(5, 0); // TODO

		State s0 = prob.getInitialState();

		System.out.println("Available action in State " + s0.toString() + ":");
		List<String> actions = prob.getActions(s0);
		for (String a : actions) {
			System.out.println(" * " + a);
		}

		System.out.println("\nSelect action:");
		String nextAction = readList();

		State succ = prob.getResults(s0, nextAction);
		System.out.println("\nNext state is: " + succ);
		System.out.println("Cost was " + prob.getCosts(s0, nextAction, succ));
	}

	protected static String readList() {

		try (Scanner sc = new Scanner(System.in)) {
			String s = sc.nextLine();
			return s;
		}

	}

}
