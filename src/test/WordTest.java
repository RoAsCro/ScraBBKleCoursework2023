package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pij.main.tiles.BonusLetterTile;
import pij.main.tiles.BonusWordTile;
import pij.main.tiles.LetterTile;
import pij.main.tiles.WildTile;
import pij.main.Word;

public class WordTest {
    @Test
    public void testAddingTilesAndScore() {
        Word word = new Word();
        word.addTile(new LetterTile("A", 1));
        word.addTile(new LetterTile("D", 1));
        word.addTile(new BonusLetterTile(3));
        word.finalise();
        Assertions.assertEquals("AD", word.toString());
        Assertions.assertEquals(4, word.getScore());

        word.addTile(new BonusWordTile(4));
        word.finalise();
        Assertions.assertEquals(16, word.getScore());

        word = new Word();
        word.addTile(new LetterTile("A", 5));
        word.addTile(new BonusLetterTile(0));
        word.finalise();
        Assertions.assertEquals(0, word.getScore());

        word = new Word();
        word.addTile(new LetterTile("A", 2));
        word.addTile(new BonusLetterTile(-2));
        word.finalise();
        Assertions.assertEquals(-4, word.getScore());

        word.addPoints(70);
        Assertions.assertEquals(66, word.getScore());
    }

    @Test
    public void testLookupWord() {
        TestUtility.loadDictionary();
        Word word = new Word();

        LetterTile letter = new LetterTile("A", 1);
        word.addTile(letter);
        Assertions.assertFalse(word.lookupWord());

        word.addTile(letter);
        Assertions.assertTrue(word.lookupWord());

        word.addTile(new WildTile());
        Assertions.assertEquals("AA ", word.toString());
        Assertions.assertTrue(word.lookupWord());
        Assertions.assertEquals("AAh", word.toString());
    }

}
