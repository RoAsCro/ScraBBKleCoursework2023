package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pij.main.*;
import pij.main.Players.ComputerPlayer;
import pij.main.Players.HumanPlayer;
import pij.main.Tiles.LetterTile;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        move.setAll(new Coordinate('h', 8), 'r', List.of(new LetterTile("A", 1)));
        Assertions.assertTrue(move.checkPlacable());

        // Position doesn't intersect
        move = new Move(player, board);
        move.setAll(new Coordinate('c', 8), 'r', List.of(new LetterTile("A", 1)));
        Assertions.assertFalse(move.checkPlacable());

        // Position does not start with the earliest letter in word
        move = new Move(player, board);
        move.setAll(new Coordinate('h', 9), 'd', List.of(new LetterTile("A", 1)));
        Assertions.assertFalse(move.checkPlacable());

        // Position is behind a letter
        move = new Move(player, board);
        move.setAll(new Coordinate('h', 7), 'd', List.of(new LetterTile("A", 1)));
        Assertions.assertTrue(move.checkPlacable());

        // Check placing next to parallel word
        board.placeTile(new Coordinate(8, 6), new LetterTile("A", 1));
        move = new Move(player, board);
        move.setAll(new Coordinate('h', 7), 'd', List.of(new LetterTile("A", 1)));
        Assertions.assertFalse(move.checkPlacable());

        // Check placing off board
        TestUtility.writeOnBoard(board.getCentre(), board, "AAAAAAAA", 'r');
        move = new Move(player, board);
        move.setAll(new Coordinate('h', 8), 'r', List.of(new LetterTile("A", 1)));
        Assertions.assertFalse(move.checkPlacable());
    }

}
