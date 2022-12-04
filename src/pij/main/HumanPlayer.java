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
	public String turn(Bag bag) {
		super.turn(bag);
		printRack();
		String move = "";
		
		boolean go = true;
		while (go) {
			go = false;
			System.out.println(
					"Please enter your move with letter sequence, position, "
					+ "and direction(d for down, r for right) separated by commas. "
					+ "Entering just two commas passes.");
			move = System.console().readLine();
			String[] movesToTest = move.split(",");
			if (movesToTest.length != 3) {
				System.out.print("B");
				go = true;
				continue;
			}
			String x = movesToTest[1].substring(0,1).toLowerCase();
			String y = movesToTest[1].substring(1);
			System.out.println(y);
			int yLength = y.length();
			if (x.toUpperCase() == x.toLowerCase()) {
				System.out.print("A");
				go = true;
				continue;
			}	
			if (yLength > 2 || yLength < 1 || !Character.isDigit(y.charAt(0)) || !Character.isDigit(y.charAt(yLength - 1))) {
				System.out.print("C");
				go = true;
				continue;
			}
			if (!Validator.inputValidation(movesToTest[2].toLowerCase(), new String[] {"r", "d"})) {
				System.out.print("D");
				go = true;
				continue;
			}
			char[] tiles = movesToTest[0].toCharArray();
			int counter = 0;
			int target = tiles.length;
			ArrayList<LetterTile> list = new ArrayList<>(getRack());
			for (char c : tiles) {
				for (LetterTile t : list) {
					System.out.println(t.getChar());
					if (t.getChar() == c) {
						
						list.remove(t);
						counter++;
						break;
					}
				}
			}
			System.out.print(move);
			if (counter != target) {
				System.out.print("E");
				go = true;
				continue;
			}
		}
		return move;
	}
	
}
