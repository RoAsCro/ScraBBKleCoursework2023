package pij.main;

public class PlaceTile implements WordOperation {

	@Override
	public void execute(Word word, Tile tile) {
		tile = word.getTilesTwo().pop();
		
	}

}
