package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pij.main.LetterTile;

public class LetterTileTest {

    @Test
    public void testToString() {
        Assertions.assertEquals("A1", new LetterTile("A", 1).toString());
    }

    @Test
    public void testGetChar() {
        Assertions.assertEquals('A', new LetterTile("A", 1).getChar());
    }

    @Test
    public void testEqualsAndHash() {
        LetterTile l1 = new LetterTile("A", 1);
        LetterTile l2 = new LetterTile("A", 1);
        Assertions.assertEquals(l1, l2);
        Assertions.assertEquals(l1.hashCode(), l2.hashCode());

        l2 = new LetterTile("A", 2);
        Assertions.assertNotEquals(l1, l2);
        Assertions.assertNotEquals(l1.hashCode(), l2.hashCode());

        l2 = new LetterTile("B", 1);
        Assertions.assertNotEquals(l1, l2);
        Assertions.assertNotEquals(l1.hashCode(), l2.hashCode());

    }

}
