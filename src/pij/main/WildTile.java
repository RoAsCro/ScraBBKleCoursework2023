package pij.main;

public class WildTile extends LetterTile {

	private char tempText = ' ';
	
	public WildTile() {
		super(" ", 3);
	}
	
	@Override
	public char getChar() {
		return tempText;
	}
	
	public void setTempText(char c) {
		tempText = c;
	}
	
	public void setText() {
		super.setText("" + tempText);
	}

}
