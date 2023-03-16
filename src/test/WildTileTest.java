package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pij.main.Tiles.WildTile;
import pij.main.Word;

public class WildTileTest {
    WildTile w = new WildTile();

    @Test
    public void testGetters() {
        Assertions.assertEquals(' ', w.getChar());
        Assertions.assertEquals(3, w.getValue());
        Assertions.assertEquals(" 3", w.toString());
    }

    @Test
    public void testMatchChar() {
        Assertions.assertTrue(w.matchChar('l'));
        Assertions.assertTrue(w.matchChar('a'));
        Assertions.assertFalse(w.matchChar('A'));
        Assertions.assertFalse(w.matchChar(' '));
        Assertions.assertFalse(w.matchChar('.'));
        Assertions.assertFalse(w.matchChar(','));
    }

    @Test
    public void testAddToSequence() {
        Word word = new Word();
        w.matchChar('a');
        word.addTile(w);
        Assertions.assertEquals("a", word.toString());
        w = new WildTile();
        word.addTile(w);
        Assertions.assertEquals("a ", word.toString());
    }

}
