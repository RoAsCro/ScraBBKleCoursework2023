package pij.main.tiles;

import pij.main.BoardTile;

/**
 * Abstract implementation of BoardTile.
 *
 * @author Roland Crompton
 */
public abstract class AbstractBoardTile implements BoardTile {

    /**
     * The value of the tile that describes how it modifies a player's score when included.
     * in a word. What exactly this means depends on the specific Tile implementation.
     */
    private final int value;

    /**
     * The text displayed when a tile is printed
     */
    private final String tileText;

    /**
     * Constructor. ensures the values of tileText and value are set.
     *
     * @param tileText the text to be displayed when a Tile is printed. Should not be null
     * @param value    the value of a tile for the purposes of modifying a player's score.
     *                 should not be null
     */
    public AbstractBoardTile(String tileText, int value) {
        this.value = value;
        this.tileText = tileText;
    }

    /**
     * Returns the tile's value.
     *
     * @return the tile's value
     */
    @Override
    public int getValue() {
        return value;
    }

    /**
     * Returns the tile text.
     *
     * @return the tile text
     */
    @Override
    public String toString() {
        return tileText;
    }

}
