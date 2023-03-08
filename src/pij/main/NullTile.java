package pij.main;

public class NullTile extends Tile{
    public NullTile() {
        super(".", 0);
    }

    @Override
    public void addToWord(Word tileSequence) {}
}
