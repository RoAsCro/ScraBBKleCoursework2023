package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pij.main.Tile;
import pij.main.tiles.BonusLetterTile;
import pij.main.tiles.LetterTile;
import pij.main.Word;

public class BonusLetterTileTest {

    @Test
    public void testGettersAndToString() {
        BonusLetterTile bonus = new BonusLetterTile(3);
        Assertions.assertEquals("(3)", bonus.toString());
        Assertions.assertEquals(3, bonus.getValue());
    }

    @Test
    public void testAddToSequence() {
        // Test bonus tile by itself
        BonusLetterTile bonus = new BonusLetterTile(3);
        Tile letter = new LetterTile("A", 1);
        Word word = new Word();
        word.addTile(bonus);
        word.finalise();
        Assertions.assertEquals(0, word.getScore());
        // Test bonus tile with other tiles
        word.addTile(letter);
        word.addTile(bonus);
        word.finalise();
        Assertions.assertEquals(3, word.getScore());
        Assertions.assertEquals("A", word.toString());
    }

}
