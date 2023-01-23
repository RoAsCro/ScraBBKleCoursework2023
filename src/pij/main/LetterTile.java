package pij.main;

public class LetterTile extends Tile {

	public LetterTile(String tileText, int value) {
		super(tileText, value);

	}

	/**
	 * Returns the text for purposes of displaying on the board and a player's tile
	 * rack.
	 * 
	 * @return the tile text concatenated with its value.
	 */
	@Override
	public String toString() {
		return super.toString() + getValue();
	}

	/**
	 * Returns the tile's letter as a character.
	 * 
	 * @return the tile's letter as a character.
	 */
	public char getChar() {
		return super.toString().charAt(0);
	}

	public boolean compareChar(char c) {
		return c == getChar();
	}

}
