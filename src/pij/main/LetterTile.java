package pij.main;

public class LetterTile extends Tile {

	public LetterTile(String tileText, int value) {		
		super(tileText, value);

	}
	
	/**
	 * Returns the text for purposes of displaying on the board and a player's tile rack.
	 * 
	 * @return the tile text concatenated with its value.
	 */
	@Override
	public String getText() {
		String prefix = "";
		if (getValue() <= 9)
			prefix = " ";
		return prefix + super.getText() + getValue() + "";
	}
	/**
	 * Returns the tile's letter as a character.
	 * 
	 * @return the tile's letter as a character.
	 */
	public char getChar() {
		return super.getText().charAt(0);
	}
	
}
