package pij.main;

public class BonusLetterTile extends BonusTile{
    public BonusLetterTile(int value) {
        super("(" + value + ")", value);
    }

    @Override
    public void addToWord(Word tileSequence) {
        int letterValue = tileSequence.getTiles().getLast().getValue();
        tileSequence.increaseBaseScore(letterValue * getValue() - letterValue);
    }
}
