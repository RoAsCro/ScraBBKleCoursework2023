package pij.main.Tiles;

import pij.main.TileSequence;

public class NullBoardTile extends AbstractBoardTile {
    public NullBoardTile() {
        super(".", 0);
    }

    @Override
    public void addToSequence(TileSequence tileSequence) {}
}
