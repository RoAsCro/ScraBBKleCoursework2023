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
		printRack();
		String input = "";
		Move move = null;
		
		while (move == null || !move.isValid()) {
			System.out.println(
					"Please enter your move with letter sequence, position, "
					+ "and direction(d for down, r for right) separated by commas. "
					+ "Entering just two commas passes.");
			input = System.console().readLine();
			ArrayList<LetterTile> rack = new ArrayList<>(getRack());
			move = new Move(input, rack);

		}
		return move;
	}
	
}
