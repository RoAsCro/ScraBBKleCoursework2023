package pij.main;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * A player makes Moves based on their Board and the CharacterTiles in their tile rack
 * and can draw new Tiles from a Bag.
 * A player has a score that can be updated.
 *
 * @author Roland Crompton
 */

public abstract class Player {

	/** The maximum number of Tiles a Player can have in their rack. */
	public final static int RACK_SIZE = 7;

	/** The Board a Player makes moves on. */
	private final Board board;

	/** A List of tiles a Player has available to them. */
	private final List<CharacterTile> tileRack = new ArrayList<>();

	/** A player's score. */
	private int score = 0;

	/**
	 * Constructor. Takes the Board the Player will make moves on.
	 *
	 * @param board the Board a Player will make moves on
	 */
	public Player(Board board) {
		this.board = board;
	}

	/**
	 * Fills all blank spaces in the Player's tile rack with Tiles from the given
	 * Bag.
	 *
	 * @param bag the Bag to be drawn from
	 */
	public void draw(Bag bag) {
		int size = this.tileRack.size();
		for (int i = 0; i < RACK_SIZE - size; i++) {
			CharacterTile tile = bag.draw();
			if (tile != null)
				this.tileRack.add(tile);
		}
	}

	/**
	 * Returns the Player's Board.
	 *
	 * @return the Player's Board
	 */
	public Board getBoard(){
		return this.board;
	}

	/**
	 * Return the Player's name - this should be based on the type of Player this Player is.
	 *
	 * @return the Player's name
	 */
	public abstract String getName();

	/**
	 * Returns the Player's tile rack. This is a copy of the TileRack as it shouldn't be possible
	 * to remove or add Tiles to the rack without using the methods in this class.
	 * 
	 * @return a copy of Player's tile rack
	 */
	public List<CharacterTile> getRack() {
		return List.copyOf(this.tileRack);
	}

	/**
	 * Returns the Player's score.
	 * 
	 * @return the Player's score
	 */
	public int getScore() {
		return this.score;
	}

	/**
	 * Removes the given CharacterTiles from the tile rack. The given CharacterTiles
	 * only need to match the characters of the Tiles in the Player's rack using the Tiles'
	 * matchChar method.
	 *
	 * @param tiles a List of CharacterTiles to be removed from the Player's rack
	 */
	public void removeTiles(List<CharacterTile> tiles) {
		tiles.forEach(c ->
				this.tileRack.remove(this.tileRack.stream()
						.filter(ch -> ch.matchChar(c.getChar()))
						.findFirst().orElse(null)));
	}

	/**
	 * The actions a Player carries out on their turn. Implementation should determine how a
	 * Player decides what move to make, and then make that into a Move object to be returned.
	 *
	 * @return the Move the Player decided to make
	 */
	public abstract Move turn();

	/**
	 * Updates the Player's score with the input integer.
	 * 
	 * @param update the number that will be added to the Player's score
	 */
	public void updateScore(int update) {
		this.score += update;
	}
}
