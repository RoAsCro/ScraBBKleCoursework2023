package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pij.main.*;
import pij.main.Tiles.LetterTile;

import java.util.List;

public class GameTest {
    Game game;
    Board board;

    public static Bag riggedBag = new Bag();

    @BeforeEach
    public void setUp() {
        board = TestUtility.loadBoardFromTestBoards("testBoard.txt");
        TestUtility.loadDictionary();
        List<Player> players = List.of(new MockComputerPlayer(board, "One"), new MockComputerPlayer(board, "Two"));
        game = new Game(players, board);
    }

    @Test
    public void testEndingDraw() {
        riggedBag = new Bag(new int[]{});
        board.placeTile(board.getCentre(), new LetterTile("A", 1));
        game.run();
        Assertions.assertEquals("It's a draw!", game.checkVictory());
    }

    @Test
    public void testEndingWinner() {
        riggedBag = new Bag(new int[]{1});
        board.placeTile(board.getCentre(), new LetterTile("A", 1));
        game.run();
        Assertions.assertEquals("The one player wins!", game.checkVictory());
    }

    @Test
    public void testEndingWithDeduction() {
        riggedBag = new Bag(new int[]{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
        board.placeTile(board.getCentre(), new LetterTile("A", 1));
        game.run();
        Assertions.assertEquals("The two player wins!", game.checkVictory());
    }

    @Test
    public void testScoringSevenTiles() {
        TestUtility.loadTestDictionary();
        board = TestUtility.loadBoardFromTestBoards("blankBoard.txt");
        List<Player> players = List.of(new MockComputerPlayer(board, "One"), new MockComputerPlayer(board, "Two"));
        game = new Game(players, board);

        riggedBag = new Bag(new int[]{ 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
        board.placeTile(board.getCentre(), new LetterTile("A", 1));
        board.placeTile(new Coordinate(7, 8), new LetterTile("A", 1));

        game.run();
        Assertions.assertEquals(73, players.get(0).getScore());
        Assertions.assertEquals(6, players.get(1).getScore());

    }

}
