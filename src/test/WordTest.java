package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pij.main.*;

public class WordTest {
    @Test
    public void testFunctions() {
        Word word = new Word();
        word.addLetter(new LetterTile("A", 1));
        word.addLetter(new LetterTile("D", 1));
        word.addLetter(new BonusLetterTile(3));
        word.finalise();
        Assertions.assertEquals("AD", word.toString());
        Assertions.assertEquals(4, word.getScore());

        word.addLetter(new BonusWordTile(4));
        word.finalise();
        Assertions.assertEquals(16, word.getScore());

        word = new Word();
        word.addLetter(new LetterTile("A", 5));
        word.addLetter(new BonusLetterTile(0));
        word.finalise();
        Assertions.assertEquals(0, word.getScore());

        word = new Word();
        word.addLetter(new LetterTile("A", 2));
        word.addLetter(new BonusLetterTile(-2));
        word.finalise();
        Assertions.assertEquals(-4, word.getScore());

        word.addPoints(70);
        Assertions.assertEquals(66, word.getScore());

    }

}
