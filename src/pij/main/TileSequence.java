package pij.main;

import java.util.LinkedList;

/**
 * A sequence of Tiles combined to form a scored word. A TileSequence allows for tiles
 * to be combined according to a Tile's addToSequence method. This interface
 * provides methods for accessing and modifying the tiles to be placed on the Board,
 * its score, and its multiplier.
 *
 * @author Roland Crompton
 */
public interface TileSequence {
    /**
     * Add a AbstractBoardTile to the TileSequence according to the Tile's addToSequence method.
     *
     * @param tile the tile to be added
     */
    void addTile(Tile tile);

    /**
     * Finalise the score. Multiply the base score by the base score.
     */
    void finaliseScore();

    /**
     * Increase the base score of the TileSequence by the integer value given.
     *
     * @param value the integer value to be added to the base score
     */
    void increaseBaseScore(int value);

    /**
     * Increase the multiplier to be multiply the base score by when finalised.
     *
     * @param value the integer to be added to the multiplier total
     */
    void increaseMultiplier(int value);

    /**
     * Return the finalised score.
     *
     * @return the finalised score, the base score multiplied by the multiplier
     */
    int getScore();

    /**
     * Returns a LinkedList of BoardTiles to actually be placed on a Board
     *
     * @return a LinkedList of BoardTiles to be placed on a Board
     */
    LinkedList<BoardTile> getTiles();
}
