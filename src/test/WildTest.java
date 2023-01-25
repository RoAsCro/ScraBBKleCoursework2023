package test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import pij.main.*;

import java.util.regex.Pattern;


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
    public void testThis(){
        String x = ".";
        System.out.println(Pattern.matches(x, "b"));
    }

    @Test
    public void testDefaultWild(){
        WildTile wild = new WildTile();
        assertEquals(wild.getChar(), '.');

    }

    @Test
    public void testWildMove(){
        Bag rigged = new Bag(new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2000});
    }
}
