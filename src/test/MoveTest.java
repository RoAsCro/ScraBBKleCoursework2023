package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pij.main.*;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MoveTest {
    @Test
    public void testValidateInput() {
        Board board = TestUtility.loadBoardFromTestBoards("testBoard.txt");
        Player player = new ComputerPlayer(board);
        Move move = new Move(player, board);
        // No tiles
        Assertions.assertFalse(move.validateInput("A,f8,r"));

        // Has the tiles
        Bag riggedBag = new Bag(new int[]{1});
        player.draw(riggedBag);
        Assertions.assertTrue(new Move(player, board).validateInput("A,f8,r"));

        // Pass
        move = new Move(player, board);
        Assertions.assertTrue(move.validateInput(",,"));
        Assertions.assertTrue(move.isPass());

        // Direction wrong
        Assertions.assertFalse(new Move(player, board).validateInput("A,f8,f"));

        // Wrong formats
        Assertions.assertFalse(new Move(player, board).validateInput("A, f8,r"));
        Assertions.assertFalse(new Move(player, board).validateInput("A,f8 r"));

    }

    @Test
    public void testToString() {
        Board board = TestUtility.loadBoardFromTestBoards("testBoard.txt");
        Player player = new ComputerPlayer(board);
        Move move = new Move(player, board);
        move.setAll(new Coordinate(0 , 0), 'r', new LinkedList<>());
        Assertions.assertEquals("The move is:	Word:  at position a1, direction: right",
                move.toString());
    }

    @Test
    public void testCheckPlacable() {
        Board board = TestUtility.loadBoardFromTestBoards("testBoard.txt");
        Player player = new ComputerPlayer(board);
        Bag riggedBag = new Bag(new int[]{1});
        player.draw(riggedBag);
        board.placeTile(board.getCentre(), new LetterTile("A", 1));

        // Valid placement
        Move move = new Move(player, board);
        Assertions.assertTrue(move.validateInput("A,h8,r"));
        Assertions.assertTrue(move.checkPlacable());

        // Position doesn't intersect
        move = new Move(player, board);
        Assertions.assertTrue(move.validateInput("A,c8,r"));
        Assertions.assertFalse(move.checkPlacable());

        // Position does not start with the earliest letter in word
        move = new Move(player, board);
        Assertions.assertTrue(move.validateInput("A,h9,d"));
        Assertions.assertFalse(move.checkPlacable());

        // Position is behind a letter
        move = new Move(player, board);
        Assertions.assertTrue(move.validateInput("A,h7,d"));
        Assertions.assertTrue(move.checkPlacable());

        // Check placing next to parallel word
        board.placeTile(new Coordinate(8, 6), new LetterTile("A", 1));
        move = new Move(player, board);
        Assertions.assertTrue(move.validateInput("A,h7,d"));
        Assertions.assertFalse(move.checkPlacable());

        // Check placing off board
        TestUtility.writeOnBoard(board.getCentre(), board, "AAAAAAAA", 'r');
        move = new Move(player, board);
        Assertions.assertTrue(move.validateInput("A,h8,r"));
        Assertions.assertFalse(move.checkPlacable());
    }

    @Test
    public void testTryMove() {
        // File file = new File("./resources/testBoard.txt");
        Board board = TestUtility.loadBoardFromTestBoards("testBoard.txt");
        Bag riggedBag = new Bag(new int[] { 1, 1, 1, 1, 1, 1, 1 });
        HumanPlayer human = new HumanPlayer(board);
        TestUtility.loadDictionary();

        Tile lower = board.tileAt(0, 0);
        Tile upper = board.tileAt(14, 14);
        Tile outOfBounds = board.tileAt(-1, -1);
        Tile outOfBoundsTwo = board.tileAt(15, 15);

        // Tests the lower and upper bounds of the grid, plus tileAt function.
        assertNotNull(lower);
        assertNotNull(upper);
        assertNull(outOfBounds);
        assertNull(outOfBoundsTwo);

        // Tests the correct tile is being referenced by getText
        assertEquals("{7}", lower.toString());
        assertEquals("(50)", board.tileAt(5, 1).toString());

        // Tests initial word placement
        human.draw(riggedBag);
        Move move = new Move(human, board);
        assertFalse(move.tryMove("FACE,a1,r"));

        move = new Move(human, board);
        assertFalse(move.tryMove("FACE,d1,r"));

        move = new Move(human, board);
        assertFalse(move.tryMove("FACE,c4,d"));

        move = new Move(human, board);
        assertTrue(move.tryMove("FACE,e8,r"));

        board = TestUtility.loadBoardFromTestBoards("testBoard.txt");

        move = new Move(human, board);
        assertTrue(move.tryMove("FACE,h8,r"));


        // Tests intersection test
        move = new Move(human, board);
        assertFalse(move.tryMove("FACE,a1,r"));

        move = new Move(human, board);
        assertFalse(move.tryMove("FACE,a1,d"));

        move = new Move(human, board);
        assertTrue(move.tryMove("CE,i8,d"));

        move = new Move(human, board);
        assertTrue(move.tryMove("FAC,k5,d"));

        move = new Move(human, board);
        assertTrue(move.tryMove("FCE,j6,r"));

        move = new Move(human, board);
        assertTrue(move.tryMove("F,i7,d"));

        move = new Move(human, board);
        assertTrue(move.tryMove("D,k5,d"));

        move = new Move(human, board);
        assertTrue(move.tryMove("BADG,m2,d"));


//		Tests not forming two words at once
        move = new Move(human, board);
        assertFalse(move.tryMove("FAE,l4,d"));

//		Tests double intersection
        board.print();
        move = new Move(human, board);
        assertTrue(move.tryMove("DE,k3,d"));

        move = new Move(human, board);
        assertTrue(move.tryMove("FE,j4,r"));

//		Tests out of bounds right

        move = new Move(human, board);
        assertTrue(move.tryMove("AB,m5,r"));

        move = new Move(human, board);
        assertFalse(move.tryMove("ED,k3,r"));

        move = new Move(human, board);
        assertFalse(move.tryMove("CE,o6,r"));


//		Tests dictionary is taking into account all letters

        move = new Move(human, board);
        assertFalse(move.tryMove("BADGE,o5,d"));



    // Tests out of bounds down
		move = new Move(human, board);
		assertTrue(move.tryMove("ADGE,o5,d"));

        move = new Move(human, board);
        assertTrue(move.tryMove("AD,k9,r"));

        move = new Move(human, board);
        assertTrue(move.tryMove("CED,l9,d"));

        move = new Move(human, board);
        assertTrue(move.tryMove("AD,l12,r"));

        move = new Move(human, board);
        assertTrue(move.tryMove("CED,m12,d"));

        move = new Move(human, board);
        assertTrue(move.tryMove("AD,m15,r"));

        move = new Move(human, board);
        assertFalse(move.tryMove("CED,n15,d"));
        board.print();

    }

}
