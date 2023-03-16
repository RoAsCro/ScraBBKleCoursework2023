package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pij.main.Board;
import pij.main.Coordinate;
import pij.main.tiles.LetterTile;

public class BoardTest {

    private Board board;

    @BeforeEach
    void setup() {
        this.board = TestUtility.loadBoardFromTestBoards("testBoard.txt");
    }

    @Test
    public void testTileAt() {
        //testBoard is 15x15
        Assertions.assertNotNull(this.board.tileAt(0, 0));
        Assertions.assertNotNull(this.board.tileAt(14, 14));
        Assertions.assertNull(this.board.tileAt(15, 0));
        Assertions.assertNull(this.board.tileAt(0, 15));
        Assertions.assertNull(this.board.tileAt(-1, 0));
        Assertions.assertNull(this.board.tileAt(0, -1));

    }


    @Test
    public void testCentre() {
        //Odd magnitude board
        Assertions.assertEquals(7,
                this.board.getCentre().getX());

        Assertions.assertEquals(16,
                this.board.tileAt(this.board.getCentre()).getValue());

        //Even magnitude board
        this.board = TestUtility.loadBoardFromTestBoards("bigBoard.txt");
        Assertions.assertEquals(12, this.board.getCentre().getX());
        Assertions.assertEquals(16, this.board.tileAt(this.board.getCentre()).getValue());

    }

    @Test
    public void testGetStartState() {
        Assertions.assertTrue(this.board.getStartState());
        this.board.placeTile(new Coordinate(7, 7), new LetterTile("a", 1));
        Assertions.assertFalse(this.board.getStartState());
    }

    @Test
    public void testPlaceTile() {
        LetterTile tile = new LetterTile("a", 1);
        // In bounds
        Assertions.assertTrue(this.board.placeTile(new Coordinate(0, 0), tile));
        Assertions.assertEquals(1, this.board.tileAt(0, 0).getValue());
        Assertions.assertTrue(this.board.placeTile(new Coordinate(14, 14), tile));
        Assertions.assertEquals(1, this.board.tileAt(14, 14).getValue());
        // Out of bounds
        Assertions.assertFalse(this.board.placeTile(new Coordinate(-1, 0), tile));
        Assertions.assertFalse(this.board.placeTile(new Coordinate(0, -1), tile));
        Assertions.assertFalse(this.board.placeTile(new Coordinate(15, 0), tile));
        Assertions.assertFalse(this.board.placeTile(new Coordinate(0, 15), tile));
    }

}
