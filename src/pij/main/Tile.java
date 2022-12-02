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
	private final int VALUE;
	
	public Tile(String tileText, int value) {
		this.VALUE = value;
		this.TILE_TEXT = tileText;
		
	}
	
	public String getText() {
		String displayText = tileText + value
	}
	
	
}
