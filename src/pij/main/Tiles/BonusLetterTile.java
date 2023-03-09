package pij.main.Tiles;

import pij.main.TileSequence;
import pij.main.Tiles.BonusTile;

public class BonusLetterTile extends BonusTile {
    public BonusLetterTile(int value) {
        super("(" + value + ")", value);
    }

    @Override
    public void addToSequence(TileSequence tileSequence) {
        int letterValue = tileSequence.getTiles().getLast().getValue();
        tileSequence.increaseBaseScore(letterValue * getValue() - letterValue);
    }
}
