package test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import pij.main.*;


public class WildTest {

    public class TestPlayer extends Player {
        public TestPlayer(Board board) {
            super(board);
        }

        @Override
        public Move turn(Game game) {

            return null;
        }
    }
    @Test
    public void testDefaultWild(){
        WildTile wild = new WildTile();
        assertEquals(wild.getChar(), ' ');
        assertTrue(wild.compareChar('f'));
        assertTrue(wild.compareChar('g'));
        assertFalse(wild.compareChar('F'));
        assertFalse(wild.compareChar('G'));
    }

    @Test
    public void testWildMove(){
        Bag rigged = new Bag(new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2000});
    }
}
