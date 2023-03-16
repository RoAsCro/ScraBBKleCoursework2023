package pij.main.tiles;

import pij.main.CharacterTile;
import pij.main.TileSequence;

/**
 * An implementation of CharacterTile representing any lowercase letter, depending on the last
 * lowercase letter it was compared to.
 * It is represented when printed as " 3". The value of a WildTile is always 3.
 */
public class WildTile implements CharacterTile {

	/**
	 * The value of a wildTile is always 3.
	 */
	private static final int VALUE = 3;

	/**
	 * The character this tile represents. Initially a space, ' ',
	 * and changes when compared to a lowercase letter.
	 */
	private char character = ' ';

	/**
	 * The method describing how this Tile combines with other Tiles in a TileSequence.
	 * When added to a TileSequence, a WildTile will generate a new LetterTile with the last
	 * lowercase letter it was checked against as the tileText, and 3 as the value.
	 * it then increases the base score of the TileSequence by 3.
	 *
	 * @param tileSequence the TileSequence this Tile is being added to
	 */
	@Override
	public void addToSequence(TileSequence tileSequence) {
		tileSequence.getTiles().add(new LetterTile("" + this.character, VALUE));
		tileSequence.increaseBaseScore(VALUE);
	}

	/**
	 * Always returns a space, ' '.
	 *
	 * @return a space as a character
	 */
	@Override
	public char getChar() {
		return ' ';
	}

	/**
	 * Always returns the value of 3.
	 *
	 * @return integer 3
	 */
	@Override
	public int getValue() {
		return VALUE;
	}

	/**
	 * Checks this Tile against a character. A WildTile will match a character if it is a
	 * lowercase letter.
	 * If it matches, the WildTile will then change the character it represents
	 * to the given character.
	 *
	 * @param c the character for this Tile to be checked against
	 * @return true if the character is a lowercase letter, false otherwise
	 */
	@Override
	public boolean matchChar(char c) {
		boolean matches = Character.isLowerCase(c);
		if (matches) {
			this.character = c;
		}
		return matches;
	}

	/**
	 * Returns the String to be shown when this Tile is printed. Always " 3".
	 * @return String " 3"
	 */
	@Override
	public String toString() {
		return " 3";
	}

}


