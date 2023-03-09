package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pij.main.WildTile;

public class WildTileTest {
    @Test
    public void testFunctions() {
        WildTile w = new WildTile();
        Assertions.assertEquals(' ', w.getChar());
        Assertions.assertEquals(3, w.getValue());
        Assertions.assertEquals(" 3", w.toString());

    }

}
