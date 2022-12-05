package pij.main;

import java.util.*;

public class HumanPlayer extends Player {

	/**
	 * Prints the tile rack.
	 */
	public void printRack() {
		String separator = "";
		for (Tile tile : getRack()) {
			System.out.print(separator + "[" + tile.getText()+ "]");
			separator = ", ";
		}
		System.out.println();
	}

	@Override
	public Move turn(Bag bag) {
		draw(bag);
		
		String input = "";
		Move move = null;
		
		do {
			printRack();
			System.out.println(
					"Please enter your move with letter sequence, position, "
					+ "and direction(d for down, r for right) separated by commas. "
					+ "Entering just two commas passes.");
			input = System.console().readLine();
			move = new Move(input, this);

		} while (!move.isValid());
		return move;
	}
	
}
