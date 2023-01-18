package pij.main;

/**
 * 
 * A tile is placed on the board. It can be a blank, premium, or letter tile.
 * 
 * @author Roland Crompton
 *
 */

public class Tile{
	/** The text displayed when a tile is displayed on the board or in a player's tile rack. */
	private String TILE_TEXT;
	
	/** The value of the tile. for premium tiles, this is the multiplier,
	 * and for letter tiles, this is the point value.
	 */
	private final int VALUE;


	public Tile(String tileText, int value) {
		this.VALUE = value;
		this.TILE_TEXT = tileText;
	}
	
	/**
	 * Returns the tile text.
	 * 
	 * @return the tile text.
	 */
	@Override
	public String toString() {
		return TILE_TEXT;
	}
	
	/**
	 * Returns the tile's value.
	 * 
	 * @return the tile's value. Always non-null.
	 */
	public int getValue() {
		return VALUE;
	}
	
	public void setText(String s) {
		TILE_TEXT = s;
	}
	
}
