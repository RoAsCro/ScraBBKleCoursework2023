package pij.main;

public class WordConstruction implements WordOperation {

	@Override
	public void execute(Word word, Tile tile) {
		word.addLetter(tile);
	}

}
