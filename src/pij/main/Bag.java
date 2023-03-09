package pij.main;

import java.util.ArrayList;
import java.util.List;

/**
 * The pool of letter tiles to be used in a game of ScraBBKle.
 * <p></>
 * This class uses the standard Scrabble values for tiles and the standard quantities of each tile
 * as found at <a href="https://scrabble.hasbro.com/en-us/faq">...</a> on 06/03/2023.
 *
 * @author Roland Crompton
 */
public class Bag {
	/**
	 * A two-dimensional array of the standard letter tiles and their values in Scrabble.
	 */
	private static final String[][] ALPHABET = new String[][] { { "A", "1" }, { "B", "3" }, { "C", "3" }, { "D", "2" },
			{ "E", "1" }, { "F", "4" }, { "G", "2" }, { "H", "4" }, { "I", "1" }, { "J", "8" }, { "K", "5" },
			{ "L", "1" }, { "M", "3" }, { "N", "1" }, { "O", "1" }, { "P", "3" }, { "Q", "10" }, { "R", "1" },
			{ "S", "1" }, { "T", "1" }, { "U", "1" }, { "V", "4" }, { "W", "4" }, { "X", "8" }, { "Y", "4" },
			{ "Z", "10" } };

	/** ArrayList storing the contents of the bag. */
	private final List<CharacterTile> bag = new ArrayList<>();

	/**
	 * Standard constructor for a game of ScraBBKle using the standard Scrabble quantities of each letter.
	 * See the website linked above for the quantities of each letter.
	 */
	public Bag() {
		this(new int[]
				{9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1, 2});
	}

	/**
	 * Customisable constructor. Takes an integer array 'quantities' of up to 27 values.
	 * The bag will be instantiated with x of each letter where x is the value
	 * at quantities[n] where n is that letter's position in the alphabet minus 1.
	 * The number of wildcard tiles will be the value at quantities[27].
	 * <p></>
	 * If their are fewer than 27 values in quantities, there will be 0 of tiles of that position.
	 *
	 * @param quantities the array determining the quantity of each tile to be used.
	 */
	public Bag(int[] quantities) {
		int i = 0;
		// Add letter tiles
		for (; i < quantities.length && i < ALPHABET.length; i++) {
			tileGenerator(ALPHABET[i][0], Integer.parseInt(ALPHABET[i][1]), quantities[i]);
		}
		// Add wildcard tiles
		if (i != quantities.length) {
			for (int j = 0; j < quantities[i]; j++) {
				bag.add(new WildTile());
			}
		}
	}

	/**
	 * Returns a random tile from the bag, and deletes that tile. Returns null if
	 * the bag is empty.
	 *
	 * @return a random AbstractBoardTile or null if bag is empty.
	 */
	public CharacterTile draw() {
		int size = bag.size();
		if (size == 0)
			return null;
		int randomNumber = (int) (Math.random() * size);
		CharacterTile tile = bag.get(randomNumber);
		bag.remove(randomNumber);
		return tile;
	}

	/**
	 * Tests if the bag is empty.
	 * @return true if the bag is empty. False otherwise.
	 */
	public boolean isEmpty() {
		return bag.isEmpty();
	}

	/**
	 * Adds tiles to bag. Helps the constructor with repeatedly adding identical tiles.
	 *
	 * @param tileText the text of the tile to be generated
	 * @param tileValue the value of the tile to be generated
	 * @param quantity the amount of the tile to be generated
	 */
	private void tileGenerator(String tileText, int tileValue, int quantity) {
		for (int i = 0; i < quantity; i++) {
			bag.add(new LetterTile(tileText, tileValue));
		}
	}

}
