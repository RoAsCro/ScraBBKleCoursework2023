package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pij.main.Board;
import pij.main.BoardReader;
import pij.main.Coordinate;
import pij.main.tiles.LetterTile;

public class BoardReaderTest {

    @Test
    public void testFunctions() {
        Coordinate coord = new Coordinate(0, 0);
        Board board = TestUtility.loadBoardFromTestBoards("testBoard.txt");
        BoardReader reader = new BoardReader(board, coord, 'r');
        // Getters
        // Coordinate Getters
        Assertions.assertEquals(0, reader.getCoordinate().getX());
        Assertions.assertEquals(0, reader.getCoordinate().getY());

        // Direction
        Assertions.assertEquals('r', reader.getDirection());

        // AbstractBoardTile
        Assertions.assertEquals(7, board.tileAt(reader.getCoordinate()).getValue());

        // Setters
        // Coordinate Setters
        reader.setCoordinate(new Coordinate(14, 14));
        Assertions.assertEquals(14, reader.getCoordinate().getX());
        Assertions.assertEquals(14, reader.getCoordinate().getY());
        reader.setCoordinate(coord);
        Assertions.assertEquals(0, reader.getCoordinate().getX());
        Assertions.assertEquals(0, reader.getCoordinate().getY());

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
        reader.conditionalNext(t -> t.getValue() == 0, (c) -> {
        });
        Assertions.assertEquals(2, board.tileAt(reader.getCoordinate()).getValue());
        // Conditional Previous
        reader.previous();
        reader.conditionalPrevious(t -> t.getValue() == 0, (c) -> {
        });
        Assertions.assertEquals(7, board.tileAt(reader.getCoordinate()).getValue());

        // breadthFirstSearch
        board.placeTile(new Coordinate(7, 7), new LetterTile("a", 1));
        board.placeTile(new Coordinate(7, 8), new LetterTile("a", 1));
        Assertions.assertEquals(2, reader.findAllLetters().size());
    }

}
