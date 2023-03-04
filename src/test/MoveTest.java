package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pij.main.*;

public class MoveTest {
    @Test
    public void testSetAll() {
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
        Assertions.assertTrue(new Move(player, board).validateInput(",,"));

        // Out of bounds
        Assertions.assertFalse(new Move(player, board).validateInput("A, z20, r"));

        // Direction wrong
        Assertions.assertFalse(new Move(player, board).validateInput("A, f8, f"));

        // Wrong formats
        Assertions.assertFalse(new Move(player, board).validateInput("A, f8,r"));
        Assertions.assertFalse(new Move(player, board).validateInput("A, f8 r"));

    }
    
}
