package pij.main;

public class WildTile extends LetterTile {

	private char tempText = ' ';
	
	public WildTile() {
		super(" ", 3);
	}
	
	@Override
	public char getChar() {
		return '.';
	}
	
	public void setTempText(char c) {
		tempText = c;
	}


}
