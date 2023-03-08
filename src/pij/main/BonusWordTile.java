package pij.main;

public class BonusWordTile extends BonusTile{
    public BonusWordTile(int value) {
        super("{" + value + "}", value);
    }

    @Override
    public void addToWord(Word tileSequence) {
        tileSequence.increaseMultiplier(getValue());
    }
}
