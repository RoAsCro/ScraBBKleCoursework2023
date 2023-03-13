package pij.main.Players;

import pij.main.*;

import java.util.ArrayList;

/**
 * 
 * The human player.
 * 
 * @author Roland Crompton
 *
 */
public class HumanPlayer extends Player {

	private static final String NAME = "Human";

	public HumanPlayer(Board board) {
		super(board);
	}

	/**
	 * Prints the tile rack.
	 */
	private void printRack() {
		String separator = "";
		for (CharacterTile tile : getRack()) {

			System.out.print(separator + "[" + tile.toString() + "]");
			separator = ", ";
		}
		System.out.println();
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public Move turn(Bag bag) {
		String input;
		Move move = new Move(this, getBoard());
		do {
			getBoard().print();
			printRack();
			System.out.println("Please enter your move with letter sequence, position, "
					+ "and direction(d for down, r for right) separated by commas. "
					+ "Entering just two commas passes.");
			input = System.console().readLine();

		} while (
				!(validateInput(input, move) &&
						move.tryMove()));
		draw(bag);
		return move;
	}

	/**
	 * Checks if the input String has been formatted correctly and uses Tiles the Player
	 * actually has. Then sets this Move's fields accordingly.
	 *
	 * @param input the String representing the move the Player wants to make
	 * @return true if the input String is valid, false otherwise
	 */
	public boolean validateInput(String input, Move move) {
		if (input.equals(",,")) {
			return true;
		}

		String[] movesToTest = input.split(",");

		if (movesToTest.length != 3) {
			return false;
		}
		String letters = movesToTest[0];
		String location = movesToTest[1];
		String direction = movesToTest[2];

		if (location.length() < 2 || location.length() > 3) {
			return false;
		}

		String x = location.substring(0,1);
		String y = location.substring(1);

		int yLength = y.length();
		// Check the x coordinate is a letter, th y coordinate is a number,
		// the direction is either right or down, and there is at least one letter being played
		if (!Character.isLetter(x.charAt(0)) || !Character.isDigit(y.charAt(0))
				|| !Character.isDigit(y.charAt(yLength - 1))
				|| (!direction.equals("r") && !direction.equals("d"))
				|| letters.length() < 1) {
			return false;
		}
		char[] chars = letters.toCharArray();
		// Check the player has the required tiles
		ArrayList<CharacterTile> moveTiles = new ArrayList<>();
		ArrayList<CharacterTile> playerRack = new ArrayList<>(getRack());
		for (char c : chars) {
			CharacterTile characterTile = playerRack.stream().filter(t->t.matchChar(c)).findFirst().orElse(null);
			if (characterTile == null) {
				System.out.println("X");
				return false;
			}
			playerRack.remove(characterTile);
			moveTiles.add(characterTile);
		}

		move.setAll(new Coordinate(x.charAt(0), Integer.parseInt(y)), direction.charAt(0), moveTiles);
		return true;
	}

}
