package pij.main.Tiles;

import pij.main.CharacterTile;
import pij.main.TileSequence;

import java.util.Objects;

/**
 * A LetterTile implements both CharacterTile and BoardTile.
 * It can be present in a Player's tile rack and be placed on a Board.
 * A LetterTile represents a single letter character specified at construction
 * and adds itself and its value to a TileSequence.
 *
 * @author Roland Crompton
 *
 */
public class LetterTile extends AbstractBoardTile implements CharacterTile {

	/**
	 * Constructs a new LetterTile, setting the character of the tile and its value.
	 * When printed, a LetterTile will be displayed as "cx" where c is the tile's character
	 * and c is its value.
	 *
	 * @param tileText the character this Tile represents as a CharacterTile. Should only be one
	 *                 character long, and should be a letter
	 * @param value the value of this tile
	 */
	public LetterTile(String tileText, int value) {
		super(tileText + value, value);

	}

	/**
	 * The method describing how this Tile combines with other Tiles in a TileSequence.
	 * A LetterTile adds itself to the TileSequence's BoardTiles then increases its base score
	 * by its value.
	 *
	 * @param tileSequence the TileSequence this Tile is being added to
	 */
	@Override
	public void addToSequence(TileSequence tileSequence) {
		tileSequence.getTiles().add(this);
		tileSequence.increaseBaseScore(getValue());
	}

	/**
	 * Checks if a different LetterTile is equal to this LetterTile.
	 * Two LetterTiles are equal if they represent the same character and have the same value.
	 *
	 * @param o the LetterTile for this Tile to be checked against
	 * @return true if o is a LetterTile that represents the same character and has the same
	 * value as this LetterTile
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof LetterTile l) {
			return l.getChar() == getChar() && l.getValue() == getValue();
		}
		return false;
	}

	/**
	 * Returns the character this Tile represents, defined at construction.
	 * 
	 * @return the character this Tile represents.
	 */
	@Override
	public char getChar() {
		return toString().charAt(0);
	}

	/**
	 * Creates a hash code for this Tile. The hash code is decided based on the character
	 * this Tile represents and its value.
	 *
	 * @return the hash code for this Tile
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getChar(), getValue());
	}

	/**
	 * Checks whether the given character exactly matches the character this Tile represents
	 *
	 * @param c the character for this Tile to be checked against
	 * @return true if the character matches, false otherwise
	 */
	@Override
	public boolean matchChar(char c) {
		return c == getChar();
	}

}
