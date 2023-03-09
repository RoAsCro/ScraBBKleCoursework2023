package pij.main.Tiles;

import pij.main.TileSequence;

public class NullTile extends AbstractBoardTile {
    public NullTile() {
        super(".", 0);
    }

    @Override
    public void addToSequence(TileSequence tileSequence) {}
}
