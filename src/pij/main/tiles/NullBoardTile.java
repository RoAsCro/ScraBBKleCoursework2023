package pij.main.tiles;

import pij.main.TileSequence;

/**
 * A null implementation of BoardTile. Used to represent the tiles on a Board where there is no Tile.
 *
 * @author Roland Crompton
 */
public class NullBoardTile extends AbstractBoardTile {

    /**
     * Constructs a new NullBoardTile, setting its tileText to "." and its value to 0.
     * When printed, a NullBoardTile will display as ".".
     */
    public NullBoardTile() {
        super(".", 0);
    }

    /**
     * The method describing how this Tile combines with other Tiles in a TileSequence.
     * A NullBoardTile does nothing if it is added to a TileSequence.
     *
     * @param tileSequence the TileSequence this Tile is being added to
     */
    @Override
    public void addToSequence(TileSequence tileSequence) {}
}
