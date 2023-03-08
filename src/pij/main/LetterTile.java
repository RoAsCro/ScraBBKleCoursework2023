package pij.main;

import java.util.Objects;

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

	@Override
	public void addToSequence(TileSequence tileSequence) {
		tileSequence.getTiles().add(this);
		tileSequence.increaseBaseScore(getValue());
	}

	/**
	 * Returns the tile's letter as a character.
	 * 
	 * @return the tile's letter as a character.
	 */
	public char getChar() {
		return super.toString().charAt(0);
	}

	public boolean matchChar(char c) {
		return c == this.getChar();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof LetterTile l) {
			return l.getChar() == getChar() && l.getValue() == getValue();
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getChar(), getValue());
	}

}
