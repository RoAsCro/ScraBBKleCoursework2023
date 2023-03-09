package pij.main;

public class NullTile extends AbstractBoardTile {
    public NullTile() {
        super(".", 0);
    }

    @Override
    public void addToSequence(TileSequence tileSequence) {}
}
