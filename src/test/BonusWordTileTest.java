package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pij.main.Tile;
import pij.main.tiles.BonusWordTile;
import pij.main.tiles.LetterTile;
import pij.main.Word;

public class BonusWordTileTest {

    @Test
    public void testGettersAndToString() {
        BonusWordTile bonus = new BonusWordTile(3);
        Assertions.assertEquals("{3}", bonus.toString());
        Assertions.assertEquals(3, bonus.getValue());
    }

    @Test
    public void testAddToSequence() {
        BonusWordTile bonus = new BonusWordTile(3);
        Tile letter = new LetterTile("B", 2);
        Word word = new Word();
        word.addTile(bonus);
        word.finalise();
        Assertions.assertEquals(0, word.getScore());
        word.addTile(letter);
        word.addTile(letter);
        word.finalise();
        Assertions.assertEquals(12, word.getScore());
        Assertions.assertEquals("BB", word.toString());
        word.addTile(bonus);
        word.finalise();
        Assertions.assertEquals(36, word.getScore());
    }

}
