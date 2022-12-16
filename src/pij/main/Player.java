package pij.main;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class Player {

	private final static int RACK_SIZE = 7;

	/** An array of tiles a player has available to them */
	private ArrayList<LetterTile> tileRack = new ArrayList<>();

	/** A player's score. */
	private double score = 0;

	/**
	 * Returns the player's tile rack.
	 * 
	 * @return the player's tile rack.
	 */
	public ArrayList<LetterTile> getRack() {
		return tileRack;
	}

	/**
	 * Returns the player's score.
	 * 
	 * @return the player's score.
	 */
	public double getScore() {
		return this.score;
	}

	/**
	 * Updates the player's score with the input double.
	 * 
	 * @param update the number that will be added to the player's score.
	 */
	public void updateScore(double update) {
		score += update;
	}

	/**
	 * Fills all blank spaces in the player's tile rack with tiles from the given
	 * bag.
	 * 
	 * @param bag the bag to be drawn from.
	 */
	public void draw(Bag bag) {
		int size = tileRack.size();
		for (int i = 0; i < RACK_SIZE - size; i++) {
			LetterTile tile = bag.draw();
			if (tile != null)
				tileRack.add(tile);
		}
	}

	/**
	 * A series of methods to be carried out on a player's turn. Should be
	 * overridden by classes extending Player.
	 * 
	 * @param bag
	 * @return TODO
	 */
	public abstract Move turn(Game game);

	/**
	 * Removes the specified letter from the tile rack. Should only be called
	 * alongside a Board's placeWord() method.
	 * 
	 * @param 
	 */
	public void removeTiles(LinkedList<LetterTile> linkedList) {
		for (LetterTile tile : linkedList) {
			tileRack.remove(tile);
		}

	}

}
