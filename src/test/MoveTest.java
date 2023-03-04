package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pij.main.*;

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
    public void testCheckPlacable() {
        Board board = TestUtility.loadBoardFromTestBoards("testBoard.txt");
        Player player = new ComputerPlayer(board);
        Bag riggedBag = new Bag(new int[]{1});
        player.draw(riggedBag);
        board.placeTile(new ScraBBKleCoordinate(board.getCentre(), board.getCentre()), new LetterTile("A", 1));

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
        board.placeTile(new ScraBBKleCoordinate(8, 6), new LetterTile("A", 1));
        move = new Move(player, board);
        Assertions.assertTrue(move.validateInput("A,h7,d"));
        Assertions.assertFalse(move.checkPlacable());

        // Check placing off board
        TestUtility.writeOnBoard(new ScraBBKleCoordinate(board.getCentre(), board.getCentre()), board, "AAAAAAAA", 'r');
        move = new Move(player, board);
        Assertions.assertTrue(move.validateInput("A,h8,r"));
        Assertions.assertFalse(move.checkPlacable());
    }

    

}
