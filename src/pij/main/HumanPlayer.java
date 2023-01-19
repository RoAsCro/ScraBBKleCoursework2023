package pij.main;
/**
 * 
 * The human player.
 * 
 * @author Roland Crompton
 *
 */
public class HumanPlayer extends Player {

	public HumanPlayer(Board board) {
		super(board);
	}

	/**
	 * Prints the tile rack.
	 */
	public void printRack() {
		String separator = "";
		for (Tile tile : getRack()) {
			System.out.print(separator + "[" + tile.toString() + "]");
			separator = ", ";
		}
		System.out.println();
	}

	@Override
	public Move turn(Game game) {
		draw(game.bag);

		String input = "";
		Move move = null;

		do {
			getBoard().print();
			printRack();
			System.out.println("Please enter your move with letter sequence, position, "
					+ "and direction(d for down, r for right) separated by commas. "
					+ "Entering just two commas passes.");
			input = System.console().readLine();
			move = new Move(input, this);

		} while (!move.isPass() && (!move.isValid() || !getBoard().placeWord(move)));

		return move;
	}

}
