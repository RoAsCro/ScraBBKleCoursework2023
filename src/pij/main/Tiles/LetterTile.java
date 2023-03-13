package pij.main.Tiles;

import pij.main.CharacterTile;
import pij.main.TileSequence;

import java.util.Objects;

/**
 * A LetterTile implements both CharacterTile and BoardTile.
 * It can be present in a Player's tile rack and be placed on a Board.
 * A LetterTile represents a single character specified at construction and adds itself and
 * its value to a TileSequence.
 *
 * @author Roland Crompton
 *
 */
public class LetterTile extends AbstractBoardTile implements CharacterTile {

	/**
	 * Constructs a new LetterTile, setting the character of the tile and its value.
	 *
	 * @param tileText the character this Tile represents as a CharacterTile
	 * @param value the value of this tile
	 */
	public LetterTile(String tileText, int value) {
		super(tileText + value, value);

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
