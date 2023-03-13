package pij.main.Tiles;

import pij.main.TileSequence;

/**
 * A BoardTile that multiplies the value of a word placed over it.
 *
 * @author Roland Crompton
 */
public class BonusWordTile extends AbstractBoardTile {
    /**
     * Constructs a new BonusWordTile, setting the tileText to "{x}" where x is the value.
     *
     * @param value the value by which the score of the word placed over this Tile is multiplied.
     *              This should be between -9 and 99
     */
    public BonusWordTile(int value) {
        super("{" + value + "}", value);
    }

    /**
     * The method describing how this Tile combines with other Tiles in a TileSequence.
     * It increases the multiplier of the TileSequence by this Tile's value.
     *
     * @param tileSequence the TileSequence this Tile is being added to
     */
    @Override
    public void addToSequence(TileSequence tileSequence) {
        tileSequence.increaseMultiplier(getValue());
    }

}
