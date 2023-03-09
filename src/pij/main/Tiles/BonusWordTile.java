package pij.main.Tiles;

import pij.main.TileSequence;
import pij.main.Tiles.BonusTile;

public class BonusWordTile extends BonusTile {
    public BonusWordTile(int value) {
        super("{" + value + "}", value);
    }

    @Override
    public void addToSequence(TileSequence tileSequence) {
        tileSequence.increaseMultiplier(getValue());
    }
}
