package pij.main;

public class WildTile extends LetterTile {

	private String tempText = " ";
	
	public WildTile() {
		super(" ", 3);
	}
	
	public void setTempText(char c) {
		tempText = "" + c;
	}
	
	public void setText() {
		super.setText(tempText);
	}

}
