package test;

import org.junit.jupiter.api.Test;
import pij.main.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardParserTest {

    @Test
    public void testBoardParser() {
        Board board = TestUtility.loadBoardFromTestBoards("testBoard.txt");
        Player computer = new ComputerPlayer(board);
        BoardParser parser = new BoardParser(board, computer, true);
        TestUtility.loadDictionary();

        computer.draw(new Bag(new int[]{1}));
        board.placeTile(new Coordinate(7, 7), new LetterTile("a", 1));
        assertEquals(1, parser.findMoves().size());

        parser = new BoardParser(board, computer, false);
        assertEquals(4, parser.findMoves().size());
    }

}
