package pij.main;

import java.util.LinkedList;

/**
 * A sequence of Tiles combined to form a scored word. A TileSequence allows for tiles
 * to be combined according to a Tile's addToSequence method. This interface
 * provides methods for accessing and modifying its LetterTiles, score,
 * and multiplier.
 *
 * @author Roland Crompton
 */
public interface TileSequence {
    /**
     * Add a Tile to the TileSequence according to the Tile's addToSequence method.
     * @param tile the tile to be added
     */
    void addTile(Tile tile);

    /**
     * Increase the base score of the TileSequence by the integer value given.
     * @param value the integer value to be added to the base score
     */
    void increaseBaseScore(int value);

    /**
     * Increase the multiplier to be multiply the base score by when finalised.
     * @param value the integer to be added to the multiplier total
     */
    void increaseMultiplier(int value);

    /**
     * Finalise the score. Multiply the base score by the base score.
     */
    void finalise();

    /**
     * Return the finalised score.
     * @return the finalised score, the base score multiplied by the multiplier
     */
    int getScore();
    LinkedList<LetterTile> getTiles();
}
