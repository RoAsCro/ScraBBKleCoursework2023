package pij.main;

import java.util.Objects;

/**
 * A LetterTile implements both CharacterTile and BoardTile.
 * It can be present in a Player's tile rack, form part of a TileSequence
 *
 * @author Roland Crompton
 *
 */
public class LetterTile extends AbstractBoardTile implements CharacterTile {

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
	@Override
	public char getChar() {
		return super.toString().charAt(0);
	}

	@Override
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
