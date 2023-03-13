package pij.main;

/**
 *
 * A Tile holds a value and some kind of String. It can also be added to a TileSequence.
 *
 * @author Roland Crompton
 *
 */
public interface Tile {

    /**
     * The method describing how this Tile combines with other Tiles in a TileSequence.
     *
     * @param tileSequence the TileSequence this Tile is being added to
     */
    void addToSequence(TileSequence tileSequence);

    /**
     * Returns the value of the Tile.
     *
     * @return the value of the Tile
     */
    int getValue();

}
