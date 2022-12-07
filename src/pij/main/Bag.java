package pij.main;

import java.util.ArrayList;

public class Bag {
	/** ArrayList storing the contents of the bag. */
	private ArrayList<LetterTile> bag = new ArrayList<>();
	private static final String[][] ALPHABET = new String[][] {
		{"A","1"}, {"B","3"}, {"C","3"}, {"D","2"}, {"E","1"}, {"F","4"}, {"G","2"}, {"H","4"}, {"I","1"}, {"J","8"}, {"K","5"}, {"L","1"},
		{"M","3"}, {"N","1"}, {"O","1"}, {"P","3"}, {"Q","10"}, {"R","1"}, {"S","1"}, {"T","1"}, {"U","1"}, {"V","4"}, {"W","4"}, {"X","8"},
		{"Y","4"}, {"Z","10"}
	};
	
	public Bag() {
		this(new int[]{9,2,2,4,12,2,3,2,9,1,1,4,2,6,8,2,1,6,4,6,4,2,2,1,2,1,2});
//		tileGenerator("A", 1, 9);
//		tileGenerator("B", 3, 2);
//		tileGenerator("C", 3, 2);
//		tileGenerator("D", 2, 4);
//		tileGenerator("E", 1, 12);
//		tileGenerator("F", 4, 2);
//		tileGenerator("G", 2, 3);
//		tileGenerator("H", 4, 2);
//		tileGenerator("I", 1, 9);
//		tileGenerator("J", 8, 1);
//		tileGenerator("K", 5, 1);
//		tileGenerator("L", 1, 4);
//		tileGenerator("M", 3, 2);
//		tileGenerator("N", 1, 6);
//		tileGenerator("O", 1, 8);
//		tileGenerator("P", 3, 2);
//		tileGenerator("Q", 10, 1);
//		tileGenerator("R", 1, 6);
//		tileGenerator("S", 1, 4);
//		tileGenerator("T", 1, 6);
//		tileGenerator("U", 1, 4);
//		tileGenerator("V", 4, 2);
//		tileGenerator("W", 4, 2);
//		tileGenerator("X", 8, 1);
//		tileGenerator("Y", 4, 2);
//		tileGenerator("Z", 10, 1);
//		//tileGenerator(" ", 3, 100);
//		for (int i = 0; i < 2; i++)
//			bag.add(new WildTile());
	}
	
	public Bag(int[] quantities) {
		int i = 0;
		for (; i < quantities.length && i < 26; i++) {
			tileGenerator(ALPHABET[i][0], Integer.parseInt(ALPHABET[i][1]), quantities[i]);
		}
		if (i != quantities.length) {
			for (int j = 0; j < quantities[i]; j++) {
				bag.add(new WildTile());
			}
		}
	}
	
	/** 
	 * Helps the constructor with repeatedly adding identical tiles.
	 */
	private void tileGenerator(String tileText, int tileValue, int quantity) {
		for (int i = 0; i  < quantity; i++) {
			bag.add(new LetterTile(tileText, tileValue));
		}
	}
	
	/**
	 * Returns a random tile from the bag, and deletes that tile.
	 * Returns null if the bag is empty.
	 * 
	 * @return a random Tile or null if bag is empty.
	 */
	
	public LetterTile draw() {
		int size = bag.size();

		if (size == 0)
			return null;

		LetterTile tile = bag.get((int) (Math.random() * size));
		bag.remove(tile);
		return tile;
	}
	

}
