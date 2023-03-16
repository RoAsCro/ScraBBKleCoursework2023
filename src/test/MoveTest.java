package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pij.main.Board;
import pij.main.Coordinate;
import pij.main.Move;
import pij.main.tiles.LetterTile;

import java.util.LinkedList;
import java.util.List;

public class MoveTest {

    @Test
    public void testToString() {
        Board board = TestUtility.loadBoardFromTestBoards("testBoard.txt");
        Move move = new Move(board);
        move.setAll(new Coordinate(0 , 0), 'r', new LinkedList<>());
        Assertions.assertEquals("The move is:	Word:  at position a1, direction: right",
                move.toString());
    }

    @Test
    public void testCheckPlacable() {
        Board board = TestUtility.loadBoardFromTestBoards("testBoard.txt");
        board.placeTile(board.getCentre(), new LetterTile("A", 1));

        // Valid placement
        Move move = new Move(board);
        move.setAll(new Coordinate('h', 8), 'r', List.of(new LetterTile("A", 1)));
        Assertions.assertTrue(move.checkPlacable());

        // Position doesn't intersect
        move = new Move(board);
        move.setAll(new Coordinate('c', 8), 'r', List.of(new LetterTile("A", 1)));
        Assertions.assertFalse(move.checkPlacable());

        // Position does not start with the earliest letter in word
        move = new Move(board);
        move.setAll(new Coordinate('h', 9), 'd', List.of(new LetterTile("A", 1)));
        Assertions.assertFalse(move.checkPlacable());

        // Position is behind a letter
        move = new Move(board);
        move.setAll(new Coordinate('h', 7), 'd', List.of(new LetterTile("A", 1)));
        Assertions.assertTrue(move.checkPlacable());

        // Check placing next to parallel word
        board.placeTile(new Coordinate(8, 6), new LetterTile("A", 1));
        move = new Move(board);
        move.setAll(new Coordinate('h', 7), 'd', List.of(new LetterTile("A", 1)));
        Assertions.assertFalse(move.checkPlacable());

        // Check placing off board
        TestUtility.writeOnBoard(board.getCentre(), board, "AAAAAAAA", 'r');
        move = new Move(board);
        move.setAll(new Coordinate('h', 8), 'r', List.of(new LetterTile("A", 1)));
        Assertions.assertFalse(move.checkPlacable());
    }

}
