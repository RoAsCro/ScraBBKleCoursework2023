package pij.main;

/**
 * 
 * A tile is placed on the board. It can be a blank, premium, or letter tile.
 * 
 * @author Roland Crompton
 *
 */

public abstract class Tile {
	/** The text displayed when a tile is displayed on the board or in a player's tile rack. */
	private final String TILE_TEXT;
	
	/** The value of the tile. for premium tiles, this is the multiplier,
	 * and for letter tiles, this is the point value.
	 */
	private final int VALUE;
	
	public Tile(String tileText, int value) {
		this.VALUE = value;
		this.TILE_TEXT = tileText;
		
	}
	
	/**
	 * Returns the text for purposes of displaying on the board and a player's tile rack.
	 * 
	 * @return the tile text concatenated with its value.
	 */
	public String getText() {
		String displayText = TILE_TEXT + VALUE;
		return displayText;
	}
	
	
}
