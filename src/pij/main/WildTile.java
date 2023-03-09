package pij.main;

public class WildTile implements CharacterTile {

	@Override
	public char getChar() {
		return ' ';
	}

	@Override
	public boolean matchChar(char c) {
		return Character.isLowerCase(c) || c == ' ';
	}

	@Override
	public int getValue() {
		return 3;
	}
}


