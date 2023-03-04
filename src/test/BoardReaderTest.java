package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pij.main.Board;
import pij.main.BoardReader;
import pij.main.LetterTile;
import pij.main.ScraBBKleCoordinate;

public class BoardReaderTest {

    @Test
    public void testGettersAndSetter() {
        ScraBBKleCoordinate coord = new ScraBBKleCoordinate(0, 0);
        Board board = TestUtility.loadBoardFromTestBoards("testBoard.txt");
        BoardReader reader = new BoardReader(board, coord, 'r');
        // Getters
        // Coordinate Getters
        Assertions.assertEquals(0, reader.getX());
        Assertions.assertEquals(0, reader.getY());
        Assertions.assertEquals(0, reader.getCoord().getX());
        Assertions.assertEquals(0, reader.getCoord().getY());

        // Direction
        Assertions.assertEquals('r', reader.getDirection());

        // Tile
        Assertions.assertEquals(7, reader.getCurrent().getValue());

        // Setters
        // Coordinate Setters
        reader.set(14, 14);
        Assertions.assertEquals(14, reader.getCoord().getX());
        Assertions.assertEquals(14, reader.getCoord().getY());
        reader.set(coord);
        Assertions.assertEquals(0, reader.getCoord().getX());
        Assertions.assertEquals(0, reader.getCoord().getY());

        // Turn
        reader.turn();
        Assertions.assertEquals('d', reader.getDirection());

        // Other
        // Next
        Assertions.assertEquals(0, reader.next().getValue());
        // Previous
        Assertions.assertEquals(7, reader.previous().getValue());
        // ConditionalNext
        reader.next();
        reader.conditionalNext(t -> t.getValue() == 0, (u, v) -> {});
        Assertions.assertEquals(2, reader.getCurrent().getValue());
        // Conditional Previous
        reader.previous();
        reader.conditionalPrevious(t -> t.getValue() == 0, (u, v) -> {});
        Assertions.assertEquals(7, reader.getCurrent().getValue());

        // breadthFirstSearch
        board.placeTile(new ScraBBKleCoordinate(7, 7), new LetterTile("a", 1));
        board.placeTile(new ScraBBKleCoordinate(7, 8), new LetterTile("a", 1));
        Assertions.assertEquals(2, reader.breadthFirstSearch().size());
    }

}