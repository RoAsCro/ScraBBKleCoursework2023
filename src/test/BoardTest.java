package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import pij.main.*;
import pij.main.Validator;

public class BoardTest {

    @Test
    public void testTileAt() {
        //testBoard is 15x15
        Board board = TestUtility.loadBoardFromTestBoards("testBoard.txt");
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
        Board board = TestUtility.loadBoardFromTestBoards("testBoard.txt");
        assertEquals(7, board.getCentre());

        assertEquals(16, board.tileAt(board.getCentre(), board.getCentre()).getValue());

        //Even magnitude board
        board = TestUtility.loadBoardFromTestBoards("bigBoard.txt");
        assertEquals(12, board.getCentre());
        assertEquals(16, board.tileAt(board.getCentre(), board.getCentre()).getValue());

    }



}
