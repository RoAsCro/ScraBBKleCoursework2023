package pij.main;

import java.util.List;
import java.util.ArrayList;


/**
 *
 * A player makes moves based on the Board in front of them.
 * A player has a tile rack and draws tiles from a bag.
 * A player has a score.
 *
 * @author Roland Crompton
 */

public abstract class Player {

	public final static int RACK_SIZE = 7;

	/** An array of tiles a player has available to them */
	private List<CharacterTile> tileRack = new ArrayList<>();

	/** A player's score. */
	private double score = 0;

	private Board board;

	public Player(Board board) {
		this.board = board;
	}

	/**
	 * Returns the player's tile rack.
	 * 
	 * @return the player's tile rack.
	 */
	public List<CharacterTile> getRack() {
		return tileRack;
	}

	public abstract String getName();

	/**
	 * Returns the player's score.
	 * 
	 * @return the player's score.
	 */
	public double getScore() {
		return this.score;
	}

	public Board getBoard(){
		return this.board;
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
			CharacterTile tile = bag.draw();
			if (tile != null)
				tileRack.add(tile);
		}
	}

	public abstract Move turn(Bag bag);

	/**
	 * Removes the specified letter from the tile rack. Should only be called
	 * alongside a Board's placeWord() method.
	 * 
	 * @param 
	 */
	public void removeTiles(List<CharacterTile> tiles) {
		for (CharacterTile tile : tiles) {
			for (CharacterTile tile2 : tileRack) {
				if (tile2.matchChar(tile.getChar())) {
					tileRack.remove(tile2);
					break;
				}
			}
		}
	}

}
