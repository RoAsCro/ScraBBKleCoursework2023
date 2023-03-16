package test;

import org.junit.jupiter.api.Test;
import pij.main.*;
import pij.main.players.ComputerPlayer;
import pij.main.tiles.LetterTile;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoveFinderTest {

    @Test
    public void testMoveFinder() {
        Board board = TestUtility.loadBoardFromTestBoards("testBoard.txt");
        Player computer = new ComputerPlayer(board);
        TestUtility.loadDictionary();

        computer.draw(new Bag(new int[]{1}));
        board.placeTile(new Coordinate(7, 7), new LetterTile("a", 1));
        assertEquals(1, MoveFinder.findMoves(board, computer.getRack(), true).size());

        assertEquals(4, MoveFinder.findMoves(board, computer.getRack(), false).size());
    }

}
