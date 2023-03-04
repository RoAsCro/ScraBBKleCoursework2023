package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pij.main.Tile;

public class TestTile {

    @Test
    public void testToString() {
        Assertions.assertEquals(".", new Tile(".", 0).toString());
    }

    @Test
    public void testGetValue() {
        Assertions.assertEquals(0, new Tile(".", 0).getValue());
    }

}
