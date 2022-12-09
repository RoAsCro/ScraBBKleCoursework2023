package pij.main;

public class PlaceTile implements WordOperation {

	@Override
	public void execute(Word word, Tile[] tiles, int y) {
		tiles[y] = word.getTilesTwo().pop();
	}

}
