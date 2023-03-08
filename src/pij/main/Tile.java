package pij.main;

/**
 * 
 * A tile is placed on the board. It can be a blank, premium, or letter tile.
 * 
 * @author Roland Crompton
 *
 */

public abstract class Tile{
	/** The text displayed when a tile is displayed on the board or in a player's tile rack. */
	private final String tileText;
	
	/** The value of the tile. for premium tiles, this is the multiplier,
	 * and for letter tiles, this is the point value.
	 */
	private final int value;


	public Tile(String tileText, int value) {
		this.value = value;
		this.tileText = tileText;
	}
	
	/**
	 * Returns the tile text.
	 * 
	 * @return the tile text.
	 */
	@Override
	public String toString() {
		return tileText;
	}
	
	/**
	 * Returns the tile's value.
	 * 
	 * @return the tile's value. Always non-null.
	 */
	public int getValue() {
		return value;
	}

	
}
