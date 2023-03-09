package pij.main.Tiles;

import pij.main.TileSequence;

public class BonusWordTile extends BonusTile {
    public BonusWordTile(int value) {
        super("{" + value + "}", value);
    }

    @Override
    public void addToSequence(TileSequence tileSequence) {
        tileSequence.increaseMultiplier(getValue());
    }
}
