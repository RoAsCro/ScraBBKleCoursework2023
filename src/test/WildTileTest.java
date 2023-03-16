package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pij.main.tiles.WildTile;
import pij.main.Word;

public class WildTileTest {
    WildTile w = new WildTile();

    @Test
    public void testGettersAndString() {
        Assertions.assertEquals(' ', this.w.getChar());
        Assertions.assertEquals(3, this.w.getValue());
        Assertions.assertEquals(" 3", this.w.toString());
    }

    @Test
    public void testMatchChar() {
        Assertions.assertTrue(this.w.matchChar('l'));
        Assertions.assertTrue(this.w.matchChar('a'));
        Assertions.assertFalse(this.w.matchChar('A'));
        Assertions.assertFalse(this.w.matchChar(' '));
        Assertions.assertFalse(this.w.matchChar('.'));
        Assertions.assertFalse(this.w.matchChar(','));
    }

    @Test
    public void testAddToSequence() {
        Word word = new Word();

        this.w.matchChar('a');
        word.addTile(this.w);
        Assertions.assertEquals("a", word.toString());
        word.finalise();
        Assertions.assertEquals(3, word.getScore());

        this.w = new WildTile();
        word.addTile(this.w);
        Assertions.assertEquals("a ", word.toString());
        word.finalise();
        Assertions.assertEquals(6, word.getScore());

    }

}
