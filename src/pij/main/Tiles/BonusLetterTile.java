package pij.main.Tiles;

import pij.main.TileSequence;

/**
 * A BoardTile that multiplies the value of a Tile placed on it.
 *
 * @author Roland Crompton
 */
public class BonusLetterTile extends AbstractBoardTile {
    /**
     * Constructs a new BonusLetterTile, setting the tileText to "(x)" where x is the value.
     *
     * @param value the value by which the value of the Tile placed on this Tile is multiplied.
     *              This should be between -9 and 99
     */
    public BonusLetterTile(int value) {
        super("(" + value + ")", value);
    }

    /**
     * The method describing how this Tile combines with other Tiles in a TileSequence.
     * It will take the last BoardTile added to a TileSequence's list of Tiles and multiplies
     * the value of that tile by the value of this tile, updating the base score accordingly.
     *
     * @param tileSequence the TileSequence this Tile is being added to
     */
    @Override
    public void addToSequence(TileSequence tileSequence) {
        if (!tileSequence.getTiles().isEmpty()) {
            int letterValue = tileSequence.getTiles().getLast().getValue();
            tileSequence.increaseBaseScore(letterValue * getValue() - letterValue);
        }
    }

}
