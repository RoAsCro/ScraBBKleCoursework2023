package pij.main;

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
	public void turn(Bag bag) {
		super.turn(bag);
		printRack();
	}
	
}
