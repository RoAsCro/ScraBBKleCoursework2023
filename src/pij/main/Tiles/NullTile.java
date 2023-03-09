package pij.main.Tiles;

import pij.main.TileSequence;
import pij.main.Tiles.AbstractBoardTile;

public class NullTile extends AbstractBoardTile {
    public NullTile() {
        super(".", 0);
    }

    @Override
    public void addToSequence(TileSequence tileSequence) {}
}
