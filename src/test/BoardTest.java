package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pij.main.*;

public class BoardTest {

    private Board board;

    @BeforeEach
    void setup() {
        board = TestUtility.loadBoardFromTestBoards("testBoard.txt");
    }

    @Test
    public void testTileAt() {
        //testBoard is 15x15
        assertEquals(Tile.class, board.tileAt(0,0).getClass());
        assertEquals(Tile.class, board.tileAt(14,14).getClass());
        assertNull(board.tileAt(15, 0));
        assertNull(board.tileAt(0, 15));
        assertNull(board.tileAt(-1, 0));
        assertNull(board.tileAt(0, -1));

    }


    @Test
    public void testCentre(){
        //Odd magnitude board
        assertEquals(7, board.getCentre());

        assertEquals(16, board.tileAt(board.getCentre(), board.getCentre()).getValue());

        //Even magnitude board
        board = TestUtility.loadBoardFromTestBoards("bigBoard.txt");
        assertEquals(12, board.getCentre());
        assertEquals(16, board.tileAt(board.getCentre(), board.getCentre()).getValue());

    }

    @Test
    public void testGetStartState() {
        assertTrue(board.getStartState());
        board.placeTile(new Coordinate(7, 7), new LetterTile("a", 1));
        assertFalse(board.getStartState());
    }

    @Test
    public void testPlaceTile() {
        LetterTile tile = new LetterTile("a", 1);
        // In bounds
        assertTrue(board.placeTile(new Coordinate(0, 0), tile));
        assertEquals(1, board.tileAt(0, 0).getValue());
        assertTrue(board.placeTile(new Coordinate(14, 14), tile));
        assertEquals(1, board.tileAt(14, 14).getValue());
        // Out of bounds
        assertFalse(board.placeTile(new Coordinate(-1, 0), tile));
        assertFalse(board.placeTile(new Coordinate(0, -1), tile));
        assertFalse(board.placeTile(new Coordinate(15, 0), tile));
        assertFalse(board.placeTile(new Coordinate(0, 15), tile));


    }


}
