package pij.main.Tiles;

import pij.main.CharacterTile;
import pij.main.TileSequence;

public class WildTile implements CharacterTile {

	private static final int VALUE = 3;

	private char character = ' ';

	@Override
	public char getChar() {
		return ' ';
	}

	@Override
	public boolean matchChar(char c) {
		boolean matches = Character.isLowerCase(c);
		if (matches) {
			character = c;
		}
		return matches;
	}

	@Override
	public int getValue() {
		return VALUE;
	}

	@Override
	public void addToSequence(TileSequence tileSequence) {
		tileSequence.getTiles().add(new LetterTile(""+character, VALUE));
		tileSequence.increaseBaseScore(VALUE);
	}

	@Override
	public String toString() {
		return " 3";
	}
}


