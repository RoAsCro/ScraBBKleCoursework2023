package test;

import org.junit.jupiter.api.Test;
import pij.main.*;
import pij.main.Players.ComputerPlayer;
import pij.main.Tiles.LetterTile;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardParserTest {

    @Test
    public void testBoardParser() {
        Board board = TestUtility.loadBoardFromTestBoards("testBoard.txt");
        Player computer = new ComputerPlayer(board);
        MoveFinder parser = new MoveFinder(board, computer, true);
        TestUtility.loadDictionary();

        computer.draw(new Bag(new int[]{1}));
        board.placeTile(new Coordinate(7, 7), new LetterTile("a", 1));
        assertEquals(1, parser.findMoves().size());

        parser = new MoveFinder(board, computer, false);
        assertEquals(4, parser.findMoves().size());
    }

}
