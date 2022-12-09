package pij.main;

public class SetWild implements WordOperation {

	@Override
	public void execute(Word word, Tile[] tiles, int i) {
		WildTile wild = (WildTile) tiles[i];
		wild.setText();
	}

}
