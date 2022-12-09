package pij.main;

public class WordConstruction implements WordOperation {

	@Override
	public void execute(Word word, Tile[] tiles, int i) {
		word.addLetter(tiles[i]);
	}

}
