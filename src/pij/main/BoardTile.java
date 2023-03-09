package pij.main;

/**
 * BoardTiles are tiles placable on a Board.
 * They can also be read together as a sequence of characters with a score be being combined
 * into a TileSequence.
 *
 * @author Roland Crompton
 */
public interface BoardTile extends Tile {

    public abstract void addToSequence(TileSequence tileSequence);

}
