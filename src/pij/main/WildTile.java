package pij.main;

public class WildTile extends LetterTile {

	public WildTile() {
		super(" ", 3);
	}

	public boolean matchChar(char c) {
		return Character.isLowerCase(c) || c == ' ';
	}

}
