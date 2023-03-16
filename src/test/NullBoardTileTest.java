package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pij.main.tiles.NullBoardTile;
import pij.main.Word;

public class NullBoardTileTest {

    @Test
    public void testGetAndToString() {
        Assertions.assertEquals(".", new NullBoardTile().toString());
        Assertions.assertEquals(0, new NullBoardTile().getValue());
    }

    @Test
    public void testAddToSequence() {
        Word word = new Word();
        word.addTile(new NullBoardTile());
        word.finalise();
        Assertions.assertTrue(word.getTiles().isEmpty());
        Assertions.assertEquals(0, word.getScore());
        Assertions.assertEquals("", word.toString());
    }

}
